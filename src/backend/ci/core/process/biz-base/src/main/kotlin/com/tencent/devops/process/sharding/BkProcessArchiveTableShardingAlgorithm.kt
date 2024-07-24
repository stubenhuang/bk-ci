/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.process.sharding

import com.tencent.devops.common.api.enums.SystemModuleEnum
import com.tencent.devops.common.api.pojo.ShardingRuleTypeEnum
import com.tencent.devops.common.api.util.ShardingUtil
import com.tencent.devops.common.client.Client
import com.tencent.devops.common.service.utils.BkShardingRoutingCacheUtil
import com.tencent.devops.common.service.utils.CommonUtils
import com.tencent.devops.common.service.utils.SpringContextUtil
import com.tencent.devops.project.api.service.ServiceShardingRoutingRuleResource
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm
import java.util.Properties

class BkProcessArchiveTableShardingAlgorithm : StandardShardingAlgorithm<String> {

    override fun doSharding(
        availableTargetNames: MutableCollection<String>,
        shardingValue: PreciseShardingValue<String>
    ): String {
        val routingName = shardingValue.value
        val fistTableNameSuffix = "_0"
        // 获取第一个分表的表名作为默认表名
        val defaultTableName = availableTargetNames.filter { it.endsWith(fistTableNameSuffix) }[0]
        // 获取逻辑表名
        val logicTableName = defaultTableName.removeSuffix(fistTableNameSuffix)
        if (routingName.isNullOrBlank()) {
            // 如果分片键为空则路由到默认数据库表
            return defaultTableName
        }
        // 获取路由规则在缓存中的key值
        val key = ShardingUtil.getShardingRoutingRuleKey(
            clusterName = CommonUtils.getDbClusterName(),
            moduleCode = SystemModuleEnum.PROCESS.name,
            ruleType = ShardingRuleTypeEnum.ARCHIVE_TABLE.name,
            routingName = routingName,
            tableName = logicTableName
        )
        // 从本地缓存获取路由规则
        var routingRule = BkShardingRoutingCacheUtil.getIfPresent(key)
        if (routingRule.isNullOrBlank()) {
            // 本地缓存没有查到路由规则信息则调接口去db实时查
            val client = SpringContextUtil.getBean(Client::class.java)
            val ruleObj = client.get(ServiceShardingRoutingRuleResource::class)
                .getShardingRoutingRuleByName(
                    routingName = routingName,
                    moduleCode = SystemModuleEnum.PROCESS,
                    ruleType = ShardingRuleTypeEnum.ARCHIVE_TABLE,
                    tableName = logicTableName
                ).data
            if (ruleObj != null) {
                routingRule = ruleObj.routingRule
                // 将路由规则信息放入本地缓存
                BkShardingRoutingCacheUtil.put(key, routingRule)
            }
        }
        if (routingRule.isNullOrBlank() || !availableTargetNames.contains(routingRule)) {
            // 没有配置路由规则则路由到默认数据库表
            return defaultTableName
        }
        return routingRule
    }

    override fun doSharding(
        availableTargetNames: MutableCollection<String>,
        shardingValue: RangeShardingValue<String>
    ): MutableCollection<String> {
        return availableTargetNames
    }

    override fun getType(): String? {
        return null
    }

    override fun init(props: Properties?) = Unit


}

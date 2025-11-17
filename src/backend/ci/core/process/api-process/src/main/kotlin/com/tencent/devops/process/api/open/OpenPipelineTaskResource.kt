/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 Tencent.  All rights reserved.
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

package com.tencent.devops.process.api.open

import com.tencent.devops.common.api.auth.AUTH_HEADER_DEVOPS_BK_TOKEN
import com.tencent.devops.common.api.pojo.BuildHistoryPage
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.pipeline.enums.BuildStatus
import com.tencent.devops.common.pipeline.enums.StartType
import com.tencent.devops.process.pojo.BuildHistory
import com.tencent.devops.process.pojo.Pipeline
import com.tencent.devops.process.pojo.classify.PipelineViewPipelinePage
import com.tencent.devops.process.pojo.open.BuildStatusInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.HeaderParam
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

@Tag(name = "OPEN_SERVICE_PIPELINE", description = "接口服务-流水线-任务资源")
@Path("/open/service/pipeline/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface OpenPipelineTaskResource {

    @Operation(summary = "获取流水线指定任务的构建状态, 如果传了TaskId则查询相应任务的状态，否则就查询整体构建的状态")
    @GET
    @Path("/get_build_status")
    fun getBuildStatus(
        @Parameter(description = "项目ID", required = true)
        @QueryParam("projectId")
        projectId: String,
        @Parameter(description = "流水线ID", required = true)
        @QueryParam("pipelineId")
        pipelineId: String,
        @Parameter(description = "构建ID", required = true)
        @QueryParam("buildId")
        buildId: String,
        @Parameter(description = "任务ID，可选，如果传了则查询相应任务的状态，否则就查询整体构建的状态", required = false)
        @QueryParam("taskId")
        taskId: String?
    ): Result<BuildStatusInfo?> // { "startUser" : "启动人", "debug" : "true/false 是否调试版本", "status" : RUNNING }

    @Operation(summary = "用户获取视图流水线编排列表")
    @GET
    @Path("/view/pipeline/list")
    fun getViewPipelineList(
        @HeaderParam(AUTH_HEADER_DEVOPS_BK_TOKEN)
        @Parameter(description = "认证token", required = true)
        token: String,
        @Parameter(description = "用户ID", required = true)
        @QueryParam("userId")
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @QueryParam("projectId")
        projectId: String,
        @Parameter(description = "视图ID", required = true)
        @QueryParam("viewId")
        viewId: String,
        @Parameter(description = "页码", required = false)
        @QueryParam("page")
        page: Int? = 1,
        @Parameter(description = "每页数量", required = false)
        @QueryParam("pageSize")
        pageSize: Int? = 20
    ): Result<PipelineViewPipelinePage<Pipeline>>

    @Operation(summary = "获取流水线构建历史-new")
    @GET
    @Path("/build/history/new")
    fun getHistoryBuildNew(
        @Parameter(description = "认证token", required = true)
        @HeaderParam(AUTH_HEADER_DEVOPS_BK_TOKEN)
        token: String,
        @Parameter(description = "用户ID", required = true)
        @QueryParam("userId")
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @QueryParam("projectId")
        projectId: String,
        @Parameter(description = "流水线ID", required = true)
        @QueryParam("pipelineId")
        pipelineId: String,
        @Parameter(description = "第几页", required = false)
        @QueryParam("page")
        page: Int?,
        @Parameter(description = "每页多少条", required = false)
        @QueryParam("pageSize")
        pageSize: Int?,
        @Parameter(description = "源材料代码库别名", required = false)
        @QueryParam("materialAlias")
        materialAlias: List<String>?,
        @Parameter(description = "代码库URL", required = false)
        @QueryParam("materialUrl")
        materialUrl: String?,
        @Parameter(description = "源材料分支", required = false)
        @QueryParam("materialBranch")
        materialBranch: List<String>?,
        @Parameter(description = "commitId", required = false)
        @QueryParam("materialCommitId")
        materialCommitId: String?,
        @Parameter(description = "commitMessage", required = false)
        @QueryParam("materialCommitMessage")
        materialCommitMessage: String?,
        @Parameter(description = "状态", required = false)
        @QueryParam("status")
        status: List<BuildStatus>?,
        @Parameter(description = "触发方式", required = false)
        @QueryParam("trigger")
        trigger: List<StartType>?,
        @Parameter(description = "排队于-开始时间(时间戳形式)", required = false)
        @QueryParam("queueTimeStartTime")
        queueTimeStartTime: Long?,
        @Parameter(description = "排队于-结束时间(时间戳形式)", required = false)
        @QueryParam("queueTimeEndTime")
        queueTimeEndTime: Long?,
        @Parameter(description = "开始于-开始时间(时间戳形式)", required = false)
        @QueryParam("startTimeStartTime")
        startTimeStartTime: Long?,
        @Parameter(description = "开始于-结束时间(时间戳形式)", required = false)
        @QueryParam("startTimeEndTime")
        startTimeEndTime: Long?,
        @Parameter(description = "结束于-开始时间(时间戳形式)", required = false)
        @QueryParam("endTimeStartTime")
        endTimeStartTime: Long?,
        @Parameter(description = "结束于-结束时间(时间戳形式)", required = false)
        @QueryParam("endTimeEndTime")
        endTimeEndTime: Long?,
        @Parameter(description = "耗时最小值", required = false)
        @QueryParam("totalTimeMin")
        totalTimeMin: Long?,
        @Parameter(description = "耗时最大值", required = false)
        @QueryParam("totalTimeMax")
        totalTimeMax: Long?,
        @Parameter(description = "备注", required = false)
        @QueryParam("remark")
        remark: String?,
        @Parameter(description = "构件号起始", required = false)
        @QueryParam("buildNoStart")
        buildNoStart: Int?,
        @Parameter(description = "构件号结束", required = false)
        @QueryParam("buildNoEnd")
        buildNoEnd: Int?,
        @Parameter(description = "构建信息", required = false)
        @QueryParam("buildMsg")
        buildMsg: String?,
        @Parameter(description = "是否查询归档数据", required = false)
        @QueryParam("archiveFlag")
        archiveFlag: Boolean? = false,
        @Parameter(description = "指定调试数据", required = false)
        @QueryParam("debug")
        debug: Boolean? = null,
        @Parameter(description = "触发代码库", required = false)
        @QueryParam("triggerAlias")
        triggerAlias: List<String>?,
        @Parameter(description = "触发分支", required = false)
        @QueryParam("triggerBranch")
        triggerBranch: List<String>?,
        @Parameter(description = "触发方式", required = false)
        @QueryParam("triggerUser")
        triggerUser: List<String>?
    ): Result<BuildHistoryPage<BuildHistory>>
}

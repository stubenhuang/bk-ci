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
package com.tencent.devops.notify.dao

import com.tencent.devops.model.notify.tables.TCommonNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TEmailsNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TMoaNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TRtxNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TWechatNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TWeworkGroupNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.TWeworkNotifyMessageTemplate
import com.tencent.devops.model.notify.tables.records.TCommonNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TEmailsNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TMoaNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TRtxNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TWechatNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TWeworkGroupNotifyMessageTemplateRecord
import com.tencent.devops.model.notify.tables.records.TWeworkNotifyMessageTemplateRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
@Suppress("ALL")
class MessageTemplateDao {

    fun crateCommonNotifyMessageTemplate(dslContext: DSLContext, template: TCommonNotifyMessageTemplateRecord) {
        with(TCommonNotifyMessageTemplate.T_COMMON_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                TEMPLATE_CODE,
                TEMPLATE_NAME,
                NOTIFY_TYPE_SCOPE,
                PRIORITY,
                SOURCE,
            ).values(
                template.id,
                template.templateCode,
                template.templateName,
                template.notifyTypeScope,
                template.priority.toByte(),
                template.source.toByte()
            ).onDuplicateKeyUpdate()
                .set(TEMPLATE_NAME, template.templateName)
                .execute()
        }
    }

    fun crateMoaNotifyMessageTemplate(dslContext: DSLContext, template: TMoaNotifyMessageTemplateRecord) {
        with(TMoaNotifyMessageTemplate.T_MOA_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                CALLBACK_URL,
                PROCESS_NAME,
                BODY,
                CREATE_TIME,
                UPDATE_TIME
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.callbackUrl,
                template.processName,
                template.body,
                template.createTime,
                template.updateTime
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

    fun crateWechatNotifyMessageTemplate(dslContext: DSLContext, template: TWechatNotifyMessageTemplateRecord) {
        with(TWechatNotifyMessageTemplate.T_WECHAT_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                BODY,
                CREATE_TIME,
                UPDATE_TIME,
                SENDER
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.body,
                template.createTime,
                template.updateTime,
                template.sender
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

    fun crateWeworkGroupNotifyMessageTemplate(
        dslContext: DSLContext,
        template: TWeworkGroupNotifyMessageTemplateRecord
    ) {
        with(TWeworkGroupNotifyMessageTemplate.T_WEWORK_GROUP_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                BODY,
                CREATE_TIME,
                UPDATE_TIME
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.body,
                template.createTime,
                template.updateTime,
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

    fun crateRtxNotifyMessageTemplate(dslContext: DSLContext, template: TRtxNotifyMessageTemplateRecord) {
        with(TRtxNotifyMessageTemplate.T_RTX_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                BODY,
                CREATE_TIME,
                UPDATE_TIME,
                SENDER
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.body,
                template.createTime,
                template.updateTime,
                template.sender
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

    fun crateWeworkNotifyMessageTemplate(dslContext: DSLContext, template: TWeworkNotifyMessageTemplateRecord) {
        with(TWeworkNotifyMessageTemplate.T_WEWORK_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                BODY,
                CREATE_TIME,
                UPDATE_TIME,
                SENDER
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.body,
                template.createTime,
                template.updateTime,
                template.sender
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

    fun crateEmailsNotifyMessageTemplate(dslContext: DSLContext, template: TEmailsNotifyMessageTemplateRecord) {
        with(TEmailsNotifyMessageTemplate.T_EMAILS_NOTIFY_MESSAGE_TEMPLATE) {
            dslContext.insertInto(
                this,
                ID,
                COMMON_TEMPLATE_ID,
                CREATOR,
                MODIFIOR,
                TITLE,
                BODY,
                CREATE_TIME,
                UPDATE_TIME,
                SENDER,
                BODY_FORMAT,
                EMAIL_TYPE
            ).values(
                template.id,
                template.commonTemplateId,
                template.creator,
                template.modifior,
                template.title,
                template.body,
                template.createTime,
                template.updateTime,
                template.sender,
                template.bodyFormat,
                template.emailType
            ).onDuplicateKeyUpdate()
                .set(TITLE, template.title)
                .set(BODY, template.body)
                .set(MODIFIOR, template.modifior)
                .set(UPDATE_TIME, template.updateTime)
                .execute()
        }
    }

}

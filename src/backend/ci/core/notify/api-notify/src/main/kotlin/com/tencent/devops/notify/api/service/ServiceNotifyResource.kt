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
package com.tencent.devops.notify.api.service

import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.notify.enums.WeworkMediaType
import com.tencent.devops.common.notify.enums.WeworkReceiverType
import com.tencent.devops.common.notify.enums.WeworkTextType
import com.tencent.devops.notify.pojo.EmailNotifyMessage
import com.tencent.devops.notify.pojo.RtxNotifyMessage
import com.tencent.devops.notify.pojo.SmsNotifyMessage
import com.tencent.devops.notify.pojo.WechatNotifyMessage
import com.tencent.devops.notify.pojo.WeworkRobotNotifyMessage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.glassfish.jersey.media.multipart.FormDataParam
import java.io.InputStream
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

@Tag(name = "SERVICE_NOTIFIES", description = "通知")
@Path("/service/notifies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface ServiceNotifyResource {
    @Operation(summary = "发送RTX信息通知")
    @POST
    @Path("/rtx")
    fun sendRtxNotify(
        @Parameter(description = "RTX信息内容", required = true)
        message: RtxNotifyMessage
    ): Result<Boolean>

    @Operation(summary = "发送电子邮件通知")
    @POST
    @Path("/email")
    fun sendEmailNotify(
        @Parameter(
            description = "电子邮件信息内容",
            required = true
        ) message: EmailNotifyMessage
    ): Result<Boolean>

    @Operation(summary = "发送微信通知")
    @POST
    @Path("/wechat")
    fun sendWechatNotify(
        @Parameter(
            description = "微信信息内容",
            required = true
        ) message: WechatNotifyMessage
    ): Result<Boolean>

    @Operation(summary = "发送短信通知")
    @POST
    @Path("/sms")
    fun sendSmsNotify(
        @Parameter(
            description = "短信信息内容",
            required = true
        ) message: SmsNotifyMessage
    ): Result<Boolean>

    @Operation(summary = "发送企业微信多媒体信息")
    @POST
    @Path("/wework/media")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    fun sendWeworkMediaNotify(
        @Parameter(description = "企业微信群Id", required = true)
        @QueryParam("receivers")
        receivers: String,
        @Parameter(description = "接受人类型", required = true)
        @QueryParam("receiverType")
        receiverType: WeworkReceiverType,
        @Parameter(description = "文件类型", required = true)
        @QueryParam("mediaType")
        mediaType: WeworkMediaType,
        @Parameter(description = "文件名称", required = true)
        @QueryParam("mediaName")
        mediaName: String,
        @Parameter(description = "文件", required = true)
        @FormDataParam("file")
        inputStream: InputStream
    ): Result<Boolean>

    @Operation(summary = "发送企业微信文本信息")
    @POST
    @Path("/wework/text")
    fun sendWeworkTextNotify(
        @Parameter(description = "企业微信群Id", required = true)
        @QueryParam("receivers")
        receivers: String,
        @Parameter(description = "接受人类型", required = true)
        @QueryParam("receiverType")
        receiverType: WeworkReceiverType,
        @Parameter(description = "文本类型", required = true)
        @QueryParam("textType")
        textType: WeworkTextType,
        @Parameter(description = "文件内容", required = true)
        message: String
    ): Result<Boolean>

    @Operation(summary = "发送企微机器人信息")
    @POST
    @Path("/wework/robot")
    fun sendWeworkRobotNotify(
        @Parameter(description = "企微机器人信息内容", required = true)
        weworkRobotNotifyMessage: WeworkRobotNotifyMessage
    ): Result<Boolean>
}

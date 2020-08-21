package com.tencent.devops.common.web.mq.processor

import com.tencent.devops.common.web.context.RID
import com.tencent.devops.common.web.context.RequestContext
import com.tencent.devops.common.web.mq.MQ_RID
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.Message

class HeaderPublishProcessor : MessagePostProcessor {
    override fun postProcessMessage(message: Message?): Message? {
        message?.messageProperties?.setHeader(MQ_RID, RequestContext[RID])
        return message
    }
}
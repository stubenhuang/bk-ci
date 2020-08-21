package com.tencent.devops.common.web.mq.processor

import com.tencent.devops.common.web.context.RID
import com.tencent.devops.common.web.context.RequestContext
import com.tencent.devops.common.web.mq.MQ_RID
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.Message

class HeaderReceiverProcessor : MessagePostProcessor {
    override fun postProcessMessage(message: Message?): Message? {
        message?.messageProperties?.run {
            RequestContext[RID] = headers[MQ_RID]?.toString() ?: "unknown"
        }
        return message
    }
}
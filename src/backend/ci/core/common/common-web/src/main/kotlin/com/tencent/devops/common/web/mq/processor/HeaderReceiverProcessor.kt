package com.tencent.devops.common.web.mq.processor

import com.tencent.devops.common.service.ThreadKey
import com.tencent.devops.common.web.mq.MQ_RID
import org.apache.logging.log4j.ThreadContext
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.Message

class HeaderReceiverProcessor : MessagePostProcessor {
    override fun postProcessMessage(message: Message?): Message? {
        ThreadContext.remove(ThreadKey.RID)

        message?.messageProperties?.run {
            ThreadContext.put(ThreadKey.RID, headers[MQ_RID]?.toString() ?: "unknown")
        }
        return message
    }
}
package com.tencent.devops.common.web.mq.processor

import com.tencent.devops.common.service.ThreadKey
import com.tencent.devops.common.web.mq.MQ_RID
import org.apache.logging.log4j.ThreadContext
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.Message

class HeaderPublishProcessor : MessagePostProcessor {
    override fun postProcessMessage(message: Message?): Message? {
        message?.messageProperties?.setHeader(MQ_RID, ThreadContext.get(ThreadKey.RID))
        return message
    }
}
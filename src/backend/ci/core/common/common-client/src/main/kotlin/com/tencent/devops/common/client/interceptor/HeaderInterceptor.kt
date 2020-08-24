package com.tencent.devops.common.client.interceptor

import com.tencent.devops.common.api.auth.HEADER_RID
import com.tencent.devops.common.service.ThreadKey
import feign.RequestInterceptor
import feign.RequestTemplate
import org.apache.logging.log4j.ThreadContext

class HeaderInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate?) {
        template?.header(HEADER_RID, ThreadContext.get(ThreadKey.RID))
    }
}
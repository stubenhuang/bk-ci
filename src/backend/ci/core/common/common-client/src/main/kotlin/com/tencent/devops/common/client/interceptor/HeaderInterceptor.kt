package com.tencent.devops.common.client.interceptor

import com.tencent.devops.common.api.auth.HEADER_RID
import com.tencent.devops.common.web.context.RID
import com.tencent.devops.common.web.context.RequestContext
import feign.RequestInterceptor
import feign.RequestTemplate

class HeaderInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate?) {
        template?.header(HEADER_RID, RequestContext[RID])
    }
}
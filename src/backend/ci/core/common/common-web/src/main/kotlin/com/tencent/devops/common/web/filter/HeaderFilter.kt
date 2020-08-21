package com.tencent.devops.common.web.filter

import com.tencent.devops.common.api.auth.HEADER_RID
import com.tencent.devops.common.web.RequestFilter
import com.tencent.devops.common.web.context.RID
import com.tencent.devops.common.web.context.RequestContext
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.PreMatching
import javax.ws.rs.ext.Provider

@Provider
@PreMatching
@RequestFilter
class HeaderFilter : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext?) {
        if (null == requestContext) {
            return
        }

        requestContext.headers[HEADER_RID].let { RequestContext[RID] = if (null == it) "unknown" else it[0] }
    }
}
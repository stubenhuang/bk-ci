package com.tencent.devops.common.web.filter

import com.tencent.devops.common.api.auth.HEADER_RID
import com.tencent.devops.common.service.ThreadKey
import com.tencent.devops.common.web.RequestFilter
import org.apache.logging.log4j.ThreadContext
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

        ThreadContext.remove(ThreadKey.RID)

        requestContext.headers[HEADER_RID].let {
            ThreadContext.put(
                ThreadKey.RID,
                if (null == it) "unknown" else it[0]
            )
        }
    }
}
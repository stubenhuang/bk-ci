package com.tencent.devops.common.web.context

import java.util.concurrent.ConcurrentHashMap

class RequestContext : ConcurrentHashMap<String?, String?>() {
    companion object {
        private val threadLocal: ThreadLocal<out RequestContext> = ThreadLocal.withInitial { RequestContext() }
        private val currentContext: RequestContext
            get() = threadLocal.get()

        operator fun set(key: String, value: String?) {
            if (value != null) currentContext[key] = value else currentContext.remove(key)
        }

        fun unset() {
            threadLocal.remove()
        }

        operator fun get(key: String): String? {
            return currentContext[key]
        }
    }
}
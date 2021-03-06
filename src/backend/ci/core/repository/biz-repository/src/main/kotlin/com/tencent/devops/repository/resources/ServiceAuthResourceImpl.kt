package com.tencent.devops.repository.resources

import com.tencent.devops.common.api.pojo.Page
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.api.util.PageUtil
import com.tencent.devops.common.web.RestResource
import com.tencent.devops.repository.api.ServiceAuthRepositoryResource
import com.tencent.devops.repository.pojo.RepositoryInfo
import com.tencent.devops.repository.service.RepositoryService
import org.springframework.beans.factory.annotation.Autowired

@RestResource
class ServiceAuthResourceImpl @Autowired constructor(
    private val repositoryService: RepositoryService
) : ServiceAuthRepositoryResource {

    override fun listByProjects(projectIds: Set<String>, page: Int?, pageSize: Int?): Result<Page<RepositoryInfo>> {
        val pageNotNull = page ?: 0
        val pageSizeNotNull = pageSize ?: 20
        val limit = PageUtil.convertPageSizeToSQLLimit(page, pageSize)
        val result = repositoryService.listByProject(projectIds, null, limit.offset, limit.limit)
        return Result(Page(pageNotNull, pageSizeNotNull, result.count, result.records))
    }
}
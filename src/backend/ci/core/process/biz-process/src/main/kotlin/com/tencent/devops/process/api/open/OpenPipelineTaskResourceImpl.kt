package com.tencent.devops.process.api.open

import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.pipeline.enums.ChannelCode
import com.tencent.devops.common.web.RestResource
import com.tencent.devops.common.web.annotation.BkApiPermission
import com.tencent.devops.common.web.constant.BkApiHandleType
import com.tencent.devops.process.engine.service.PipelineRuntimeService
import com.tencent.devops.process.engine.service.PipelineTaskService
import com.tencent.devops.process.pojo.Pipeline
import com.tencent.devops.process.pojo.PipelineSortType
import com.tencent.devops.process.pojo.classify.PipelineViewPipelinePage
import com.tencent.devops.process.pojo.open.BuildStatusInfo
import com.tencent.devops.process.service.PipelineListFacadeService
import org.springframework.beans.factory.annotation.Autowired

@RestResource
class OpenPipelineTaskResourceImpl @Autowired constructor(
    private val pipelineTaskService: PipelineTaskService,
    private val pipelineRuntimeService: PipelineRuntimeService,
    private val pipelineListFacadeService: PipelineListFacadeService
) : OpenPipelineTaskResource {

    override fun getBuildStatus(
        projectId: String,
        pipelineId: String,
        buildId: String,
        taskId: String?
    ): Result<BuildStatusInfo?> {

        // 校验参数
        val build = pipelineRuntimeService.getBuildInfo(
            projectId = projectId,
            pipelineId = pipelineId,
            buildId = buildId
        )

        return if (build == null) {
            Result(status = -1, message = "Build[$buildId] is not found!")
        } else if (!taskId.isNullOrBlank()) { // 查指定task的状态
            val tStatus = pipelineTaskService.getTaskStatus(projectId = projectId, buildId = buildId, taskId = taskId)
            if (tStatus == null) {
                Result(status = -1, message = "Task[$taskId] is not found!")
            } else {
                Result(BuildStatusInfo(startUser = build.startUser, debug = build.debug, status = tStatus))
            }
        } else {
            Result(BuildStatusInfo(startUser = build.startUser, debug = build.debug, status = build.status))
        }
    }

    @BkApiPermission([BkApiHandleType.API_OPEN_TOKEN_CHECK])
    override fun getViewPipelineList(
        token: String,
        userId: String,
        projectId: String,
        viewId: String,
        page: Int?,
        pageSize: Int?
    ): Result<PipelineViewPipelinePage<Pipeline>> {
        val result = pipelineListFacadeService.listViewPipelines(
            userId = userId,
            projectId = projectId,
            page = page,
            pageSize = pageSize,
            sortType = PipelineSortType.CREATE_TIME,
            channelCode = ChannelCode.BS,
            viewId = viewId,
            checkPermission = false
        )
        return Result(result)
    }
}

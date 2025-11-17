package com.tencent.devops.process.api.open

import com.tencent.devops.common.api.pojo.BuildHistoryPage
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.pipeline.enums.BuildStatus
import com.tencent.devops.common.pipeline.enums.ChannelCode
import com.tencent.devops.common.pipeline.enums.StartType
import com.tencent.devops.common.web.RestResource
import com.tencent.devops.common.web.annotation.BkApiPermission
import com.tencent.devops.common.web.constant.BkApiHandleType
import com.tencent.devops.process.engine.service.PipelineRuntimeService
import com.tencent.devops.process.engine.service.PipelineTaskService
import com.tencent.devops.process.pojo.BuildHistory
import com.tencent.devops.process.pojo.Pipeline
import com.tencent.devops.process.pojo.PipelineSortType
import com.tencent.devops.process.pojo.classify.PipelineViewPipelinePage
import com.tencent.devops.process.pojo.open.BuildStatusInfo
import com.tencent.devops.process.service.PipelineListFacadeService
import com.tencent.devops.process.service.builds.PipelineBuildFacadeService
import org.springframework.beans.factory.annotation.Autowired

@RestResource
class OpenPipelineTaskResourceImpl @Autowired constructor(
    private val pipelineTaskService: PipelineTaskService,
    private val pipelineRuntimeService: PipelineRuntimeService,
    private val pipelineListFacadeService: PipelineListFacadeService,
    private val pipelineBuildFacadeService: PipelineBuildFacadeService
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

    @Suppress("LongParameterList", "UNUSED_PARAMETER")
    @BkApiPermission([BkApiHandleType.API_OPEN_TOKEN_CHECK])
    override fun getHistoryBuildNew(
        token: String,
        userId: String,
        projectId: String,
        pipelineId: String,
        page: Int?,
        pageSize: Int?,
        materialAlias: List<String>?,
        materialUrl: String?,
        materialBranch: List<String>?,
        materialCommitId: String?,
        materialCommitMessage: String?,
        status: List<BuildStatus>?,
        trigger: List<StartType>?,
        queueTimeStartTime: Long?,
        queueTimeEndTime: Long?,
        startTimeStartTime: Long?,
        startTimeEndTime: Long?,
        endTimeStartTime: Long?,
        endTimeEndTime: Long?,
        totalTimeMin: Long?,
        totalTimeMax: Long?,
        remark: String?,
        buildNoStart: Int?,
        buildNoEnd: Int?,
        buildMsg: String?,
        archiveFlag: Boolean?,
        debug: Boolean?,
        triggerAlias: List<String>?,
        triggerBranch: List<String>?,
        triggerUser: List<String>?
    ): Result<BuildHistoryPage<BuildHistory>> {
        val result = pipelineBuildFacadeService.getHistoryBuild(
            userId = userId,
            projectId = projectId,
            pipelineId = pipelineId,
            page = page,
            pageSize = pageSize,
            materialAlias = materialAlias,
            materialUrl = materialUrl,
            materialBranch = materialBranch,
            materialCommitId = materialCommitId,
            materialCommitMessage = materialCommitMessage,
            status = status,
            trigger = trigger,
            queueTimeStartTime = queueTimeStartTime,
            queueTimeEndTime = queueTimeEndTime,
            startTimeStartTime = startTimeStartTime,
            startTimeEndTime = startTimeEndTime,
            endTimeStartTime = endTimeStartTime,
            endTimeEndTime = endTimeEndTime,
            totalTimeMin = totalTimeMin,
            totalTimeMax = totalTimeMax,
            remark = remark,
            buildNoStart = buildNoStart,
            buildNoEnd = buildNoEnd,
            buildMsg = buildMsg,
            archiveFlag = archiveFlag,
            debug = debug,
            triggerAlias = triggerAlias,
            triggerBranch = triggerBranch,
            triggerUser = triggerUser,
            checkPermission = false
        )
        return Result(result)
    }
}

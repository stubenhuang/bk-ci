package com.tencent.devops.process.api.op

import com.tencent.devops.common.api.auth.AUTH_HEADER_USER_ID
import com.tencent.devops.common.api.auth.AUTH_HEADER_USER_ID_DEFAULT_VALUE
import com.tencent.devops.common.api.pojo.Result
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.HeaderParam
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Tag(name = "USER_PIPELINE_VIEW", description = "用户-流水线视图")
@Path("/op/pipelineViews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface OpPipelineViewResource {
    @Operation(summary = "初始化所有动态视图")
    @GET
    @Path("/initAllView")
    fun initAllView(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String
    ): Result<Boolean>
}

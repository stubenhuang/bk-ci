/*
 * Tencent is pleased to support the open source community by making BlueKing available.
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the MIT License (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://opensource.org/licenses/MIT
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.bk.codecc.defect.api;

import com.tencent.bk.codecc.defect.vo.ToolClocRspVO;
import com.tencent.bk.codecc.defect.vo.ToolDefectRspVO;
import com.tencent.bk.codecc.defect.vo.admin.DeptTaskDefectExtReqVO;
import com.tencent.bk.codecc.defect.vo.admin.DeptTaskDefectReqVO;
import com.tencent.bk.codecc.defect.vo.common.DefectQueryReqVO;
import com.tencent.bk.codecc.defect.vo.openapi.CheckerPkgDefectRespVO;
import com.tencent.bk.codecc.defect.vo.openapi.CheckerPkgDefectVO;
import com.tencent.bk.codecc.defect.vo.openapi.TaskOverviewDetailRspVO;
import com.tencent.devops.common.api.pojo.CodeCCResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.tencent.devops.common.api.auth.CodeCCHeaderKt.CODECC_AUTH_HEADER_DEVOPS_TASK_ID;

/**
 * 告警相关接口
 * 
 * @date 2019/11/15
 * @version V1.0
 */
@Api(tags = {"SERVICE_PKGDEFECT"}, description = "告警相关接口")
@Path("/service/pkgDefect")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ServicePkgDefectRestResource
{

    @ApiOperation("根据规则包获取规则清单")
    @Path("/list/toolName/{toolName}/pkgId/{pkgId}")
    @GET
    CodeCCResult<CheckerPkgDefectVO> getPkgDefectList(
                    @ApiParam(value = "工具名", required = true)
                    @PathParam(value = "toolName")
                    String toolName,
                    @ApiParam(value = "规则包id", required = true)
                    @PathParam(value = "pkgId")
                    String pkgId,
                    @ApiParam(value = "事业群id")
                    @QueryParam(value = "bgId")
                    Integer bgId,
                    @ApiParam(value = "代码扫描任务id")
                    @QueryParam(value = "taskId")
                    Long taskId,
                    @ApiParam(value = "页数")
                    @QueryParam(value = "pageNum")
                    Integer pageNum,
                    @ApiParam(value = "每页多少条")
                    @QueryParam(value = "pageSize")
                    Integer pageSize,
                    @ApiParam(value = "排序字段")
                    @QueryParam(value = "sortField")
                    String sortField,
                    @ApiParam(value = "排序类型")
                    @QueryParam(value = "sortType")
                    Sort.Direction sortType);


    @ApiOperation("统计工具规则包各规则的告警情况")
    @Path("/statistics/toolName/{toolName}/pkgId/{pkgId}")
    @GET
    CodeCCResult<CheckerPkgDefectRespVO> queryCheckerPkgDefect(
            @ApiParam(value = "工具名称", required = true)
            @PathParam(value = "toolName")
                    String toolName,
            @ApiParam(value = "规则包ID", required = true)
            @PathParam(value = "pkgId")
                    String pkgId,
            @ApiParam(value = "事业群ID", required = true)
            @QueryParam(value = "bgId")
                    Integer bgId,
            @ApiParam(value = "部门ID")
            @QueryParam(value = "deptId")
                    Integer deptId,
            @ApiParam(value = "页数")
            @QueryParam(value = "pageNum")
                    Integer pageNum,
            @ApiParam(value = "每页多少条")
            @QueryParam(value = "pageSize")
                    Integer pageSize,
            @ApiParam(value = "排序类型")
            @QueryParam(value = "sortType")
                    Sort.Direction sortType
    );


    @ApiOperation("查询工具告警清单")
    @Path("/list")
    @POST
    CodeCCResult<ToolDefectRspVO> queryToolDefectList(
            @ApiParam(value = "任务ID", required = true)
            @HeaderParam(CODECC_AUTH_HEADER_DEVOPS_TASK_ID)
                    Long taskId,
            @ApiParam(value = "查询参数详情", required = true)
            @Valid DefectQueryReqVO defectQueryReqVO,
            @ApiParam(value = "页数")
            @QueryParam(value = "pageNum")
                    Integer pageNum,
            @ApiParam(value = "页面大小")
            @QueryParam(value = "pageSize")
                    Integer pageSize,
            @ApiParam(value = "排序字段")
            @QueryParam(value = "sortField")
                    String sortField,
            @ApiParam(value = "排序方式")
            @QueryParam(value = "sortType")
                    Sort.Direction sortType);


    @ApiOperation("查询代码行数信息")
    @Path("/codeLine/taskId/{taskId}")
    @POST
    CodeCCResult<ToolClocRspVO> queryCodeLine(
            @ApiParam(value = "任务id", required = true)
            @PathParam(value = "taskId")
            Long taskId);

    @ApiOperation("统计工具规则包各规则的告警情况")
    @Path("/statistics/overall")
    @POST
    CodeCCResult<CheckerPkgDefectRespVO> queryOverallDefect(
            @ApiParam(value = "复合条件请求体")
            @Valid
                    DeptTaskDefectExtReqVO reqVO,
            @ApiParam(value = "页数")
            @QueryParam(value = "pageNum")
                    Integer pageNum,
            @ApiParam(value = "每页多少条")
            @QueryParam(value = "pageSize")
                    Integer pageSize,
            @ApiParam(value = "排序类型")
            @QueryParam(value = "sortType")
                    Sort.Direction sortType
    );

    @ApiOperation("批量统计任务告警概览情况")
    @Path("/statistics/overview")
    @POST
    CodeCCResult<TaskOverviewDetailRspVO> queryTaskOverview(
            @ApiParam(value = "查询参数详情", required = true) @Valid DeptTaskDefectReqVO deptTaskDefectReqVO,
            @ApiParam(value = "页数") @QueryParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "每页数量") @QueryParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "排序类型") @QueryParam(value = "sortType") Sort.Direction sortType);


    @ApiOperation("批量获取个性化任务告警概览情况")
    @Path("/statistics/custom")
    @GET
    CodeCCResult<TaskOverviewDetailRspVO> queryCustomTaskOverview(
            @ApiParam(value = "个性化任务创建来源") @QueryParam(value = "customProjSource") String customProjSource,
            @ApiParam(value = "页数") @QueryParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "每页数量") @QueryParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "排序类型") @QueryParam(value = "sortType") Sort.Direction sortType);


}

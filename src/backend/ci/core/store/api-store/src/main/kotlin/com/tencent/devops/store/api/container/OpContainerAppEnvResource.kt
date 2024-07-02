/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.store.api.container

import com.tencent.devops.store.pojo.app.ContainerAppEnv
import com.tencent.devops.store.pojo.app.ContainerAppEnvCreate
import com.tencent.devops.common.api.pojo.Result
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Tag(name = "OP_CONTAINER_APP_ENV", description = "OP-容器-编译环境变量")
@Path("/op/pipeline/container/app/env")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface OpContainerAppEnvResource {

    @Operation(summary = "添加编译环境变量")
    @POST
    @Path("/")
    fun addContainerAppEnv(
        @Parameter(description = "容器编译环境变量请求实体", required = true)
         containerAppEnvRequest: ContainerAppEnvCreate
    ): Result<Boolean>

    @Operation(summary = "根据ID删除编译环境变量")
    @DELETE
    @Path("/{id}")
    fun deleteContainerAppEnvById(
        @Parameter(description = "编译环境变量ID", required = true)
        @PathParam("id")
        id: Int
    ): Result<Boolean>

    @Operation(summary = "更新编译环境变量")
    @PUT
    @Path("/{id}")
    fun updateContainerAppEnv(
        @Parameter(description = "编译环境变量ID", required = true)
        @PathParam("id")
        id: Int,
        @Parameter(description = "容器编译环境变量请求实体", required = true)
        containerAppEnvRequest: ContainerAppEnvCreate
    ): Result<Boolean>

    @Operation(summary = "根据appId获取所有编译环境变量信息")
    @GET
    @Path("/list/{appId}")
    fun listContainerAppEnvsByAppId(
        @Parameter(description = "编译环境ID", required = true)
        @PathParam("appId")
        appId: Int
    ): Result<List<ContainerAppEnv>>

    @Operation(summary = "根据ID获取编译环境变量信息")
    @GET
    @Path("/{id}")
    fun getContainerAppEnvById(
        @Parameter(description = "编译环境变量ID", required = true)
        @PathParam("id")
        id: Int
    ): Result<ContainerAppEnv?>
}

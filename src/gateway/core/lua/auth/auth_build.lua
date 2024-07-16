--[[
Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.

Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.

BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.

A copy of the MIT License is included in this file.


Terms of the MIT License:
---------------------------------------------------
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
]]

-- 本地开发不进行鉴权
if os.getenv("BK_CI_ENV") == "local" then
    return
end

local build_type = ngx.var.http_x_devops_build_type

ngx.header["X-DEVOPS-ERROR-RETURN"] =
'{"status": 500,"data": "buildEnd","result":true,"message": "构建已结束。","errorCode":2101182}'
if build_type == "AGENT" then
    buildUtil:auth_agent()
    return
elseif build_type == "DOCKER" then
    buildUtil:auth_docker()
    return
elseif build_type == "PLUGIN_AGENT" then
    buildUtil:auth_plugin_agent()
    return
elseif build_type == "MACOS" then
    buildUtil:auth_macos(false)
    return
else
    buildUtil:auth_other()
    return
end

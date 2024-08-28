FROM ubuntu:20.04 as builder

LABEL maintainer="Tencent BlueKing Devops"

ARG NODE_JS_VERSION

RUN apt update
RUN apt install -y curl
RUN curl -fsSL https://deb.nodesource.com/setup_${NODE_JS_VERSION}.x | bash -
RUN apt install -y nodejs
RUN npm install -g yarn

# 前端代码
WORKDIR /frontend
COPY src/frontend .
RUN yarn install && yarn public

# 网关代码
WORKDIR /gateway
COPY src/gateway .

# 压缩
WORKDIR /
RUN tar -czvf gateway.tar.gz /frontend/frontend /gateway

FROM scratch as output
COPY --from=builder /gateway.tar.gz gateway.tar.gz

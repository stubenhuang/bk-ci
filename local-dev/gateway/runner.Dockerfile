FROM bkci/openresty:0.0.1

LABEL maintainer="Tencent BlueKing Devops"

ENV INSTALL_PATH="/data/workspace/"
ENV OPENRESTY_PATH="/usr/local/openresty/nginx"

# 依赖软件
RUN yum install -y dos2unix

# 复制脚本和模板
COPY ./scripts /data/workspace/scripts
COPY ./support-files/templates /data/workspace/templates
COPY ./local-dev/release/gateway.tar.gz /data/workspace/build/

# 解压
WORKDIR /data/workspace/build
RUN tar -xf gateway.tar.gz && \
    cp -rf frontend/frontend /data/workspace/ && \
    cp -rf gateway /data/workspace/ && \
    ls -l /data/workspace

# 准备好启动环境
ENV BK_CI_ENV="local"
WORKDIR ${OPENRESTY_PATH}
RUN rm -rf conf &&\
    ln -s /data/workspace/gateway/core conf &&\
    dos2unix /data/workspace/scripts/* &&\
    cp /data/workspace/scripts/bkenv.properties ./ &&\
    echo "BK_REPO_FQDN=bkrepo.local.com" >> bkenv.properties &&\
    echo "BK_REPO_GATEWAY_IP=127.0.0.1" >> bkenv.properties &&\
    chmod +x /data/workspace/scripts/render_tpl &&\
    find "/data/workspace/frontend/" -name "frontend#*" -exec cp -v -f {} /data/workspace/templates \; &&\
    /data/workspace/scripts/render_tpl -m . /data/workspace/templates/gateway* &&\
    /data/workspace/scripts/render_tpl -m . /data/workspace/templates/frontend* 

# 运行Docker
RUN echo "#!/bin/bash" > start.sh &&\
    echo "source bkenv.properties" >> start.sh &&\
    echo "mkdir -p \${BK_CI_LOGS_DIR}/nginx/ \${BK_CI_HOME} ./run/" >> start.sh &&\
    echo "chown nobody:nobody \${BK_CI_LOGS_DIR}/nginx/" >> start.sh &&\
    echo "ln -s /data/workspace/frontend \${BK_CI_HOME}/frontend" >> start.sh &&\
    echo "sbin/nginx -g \"daemon off;\"" >> start.sh &&\
    chmod +x start.sh
CMD [ "./start.sh"]

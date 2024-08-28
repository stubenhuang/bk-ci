REGISTRY ?=
IMAGE_TAG ?=$(shell git rev-parse --short HEAD 2>/dev/null)
NODE_JS_VERSION ?= 18
FRONTEND_PATH := ../src/frontend
GATEWAY_PATH := ../src/gateway

.DEFAULT:
build: build-gateway
	@echo "build all success , release:"
	@ls -lh release
build-gateway:
	@echo "build gateway start..."
	cd ${GATEWAY_PATH} && git clean -fd
	cd ${FRONTEND_PATH} && git clean -fd
	cp gateway/.dockerignore ..
	DOCKER_BUILDKIT=1 docker build -f gateway/builder.Dockerfile \
		--build-arg NODE_JS_VERSION=${NODE_JS_VERSION} \
		-t ${REGISTRY}bkci-gateway-builder:${IMAGE_TAG} \
		--output release \
		..
	rm -f ../.dockerignore
	@echo ""
	@echo "output zip file: release/gateway.zip"
run-gateway: stop-gateway
	@echo "run gateway start..."
	cd ${GATEWAY_PATH} && git clean -fd
	cd ${FRONTEND_PATH} && git clean -fd
	cp gateway/.dockerignore ..
	DOCKER_BUILDKIT=1 docker build -f gateway/runner.Dockerfile \
		-t ${REGISTRY}bkci-gateway-runner:${IMAGE_TAG} \
		..
	rm -f ../.dockerignore
	docker run -p 80:80 -d --name bkci-gateway-local ${REGISTRY}bkci-gateway-runner:${IMAGE_TAG}
stop-gateway:
	docker ps -q -f name=bkci-gateway-local | xargs --no-run-if-empty docker stop
	docker ps -a -q -f name=bkci-gateway-local | xargs --no-run-if-empty docker rm

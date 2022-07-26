ARG REPOSITORY_URL
ARG REPOSITORY_ID
ARG BUILD_VERSION
FROM ${REPOSITORY_URL}/${REPOSITORY_ID}/base-akamai-siem-processor:${BUILD_VERSION}

# File Author / Maintainer.
LABEL maintainer="Felipe Vilarinho <fvilarin@akamai.com>"

# Adding startup and configuration files.
COPY etc/* ${ETC_DIR}/
COPY src/*.js ${BIN_DIR}/
COPY src/node_modules ${LIBS_DIR}
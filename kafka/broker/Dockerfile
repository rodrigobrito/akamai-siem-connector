# Receive build attributes.
ARG REPOSITORY_URL
ARG REPOSITORY_ID
ARG BUILD_VERSION
FROM ${REPOSITORY_URL}/${REPOSITORY_ID}/base-kafka:${BUILD_VERSION}

# File Author / Maintainer.
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Environment variables.
ENV ZOOKEEPER_SERVICE_HOST zookeeper
ENV ZOOKEEPER_SERVICE_PORT 2181

# Add default scripts and configuration files.
ADD bin ${BIN_DIR}
ADD etc ${ETC_DIR}

# Define permissions and entrypoint.
USER root
RUN rm /opt/kafka/config/server.properties && \
    ln -s ${ETC_DIR}/server.properties /opt/kafka/config/server.properties && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh && \
    chmod +x ${BIN_DIR}/*.sh

# Default user.
USER kafka

# Default work directory.
WORKDIR ${HOME_DIR}

# Exposed ports
EXPOSE 9092/tcp

ENTRYPOINT [ "/entrypoint.sh" ]
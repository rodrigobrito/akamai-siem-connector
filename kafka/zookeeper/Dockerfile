ARG REPOSITORY_URL
ARG REPOSITORY_ID
ARG BUILD_VERSION
FROM ${REPOSITORY_URL}/${REPOSITORY_ID}/base-kafka:${BUILD_VERSION}

# File Author / Maintainer
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Add default scripts and configuration files.
ADD bin ${BIN_DIR}
ADD etc ${ETC_DIR}

# Define permissions and entrypoint.
USER root
RUN rm /opt/kafka/config/zookeeper.properties && \
    ln -s ${ETC_DIR}/zookeeper.properties /opt/kafka/config/zookeeper.properties && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh && \
    chmod +x ${BIN_DIR}/*.sh

# Default user.
USER kafka

# Default work directory.
WORKDIR ${HOME_DIR}

# Exposed ports
EXPOSE 2181/tcp
EXPOSE 8080/tcp

ENTRYPOINT [ "/entrypoint.sh" ]
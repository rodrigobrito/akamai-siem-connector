FROM alpine:3.16.1

# File Author / Maintainer
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Variables
ENV HOME_DIR=/home/kafka
ENV BIN_DIR=${HOME_DIR}/bin
ENV ETC_DIR=${HOME_DIR}/etc
ENV DATA_DIR=${HOME_DIR}/data
ENV LOGS_DIR=${HOME_DIR}/logs
ARG SCALA_VERSION=2.13
ARG KAFKA_VERSION=3.2.0

# Install dependencies
RUN apk update && \
    apk add --no-cache bash ca-certificates curl nss openjdk11-jre

# Create default directory structure.
RUN mkdir -p ${HOME_DIR} ${BIN_DIR} ${ETC_DIR} ${DATA_DIR}

# Download Kafka package.
RUN curl https://dlcdn.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz -o kafka.tgz && \
    tar -xzf kafka.tgz -C /opt && \
    ln -s /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka && \
    ln -s /opt/kafka/logs ${LOGS_DIR} && \
    rm -rf kafka.tgz

ADD bin ${BIN_DIR}

# Define the permissions and entrypoint file.
RUN addgroup -S kafka && \
    adduser -S kafka -G kafka && \
    chown -R kafka:kafka ${HOME_DIR} && \
    chown -R kafka:kafka /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} && \
    chmod +x ${BIN_DIR}/*.sh

# Default user.
USER kafka

# Default work directory.
WORKDIR ${HOME_DIR}
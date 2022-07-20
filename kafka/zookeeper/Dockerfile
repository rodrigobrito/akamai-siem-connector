<<<<<<< HEAD
ARG REPOSITORY_URL
ARG REPOSITORY_ID
ARG BUILD_VERSION
FROM ${REPOSITORY_URL}/${REPOSITORY_ID}/base-kafka:${BUILD_VERSION}
=======
FROM alpine:latest
>>>>>>> origin/main

# File Author / Maintainer
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

<<<<<<< HEAD
# Add default scripts and configuration files.
ADD bin ${BIN_DIR}
ADD etc ${ETC_DIR}

# Define permissions and entrypoint.
USER root
RUN rm /opt/kafka/config/zookeeper.properties && \
    ln -s ${ETC_DIR}/zookeeper.properties /opt/kafka/config/zookeeper.properties && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh && \
    chmod +x ${BIN_DIR}/*.sh
=======
RUN apk add --update \
    curl \
    openjdk11-jre \
	bash \
 && rm /var/cache/apk/*
>>>>>>> origin/main

# Default user.
USER kafka

<<<<<<< HEAD
# Default work directory.
WORKDIR ${HOME_DIR}
=======
RUN curl "https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz" -o "kafka_2.13-3.2.0.tgz"

RUN tar -xzf kafka_2.13-3.2.0.tgz -C /tmp/

RUN rm -rf kafka_2.13-3.2.0.tgz

RUN cp -rf /tmp/kafka_2.13-3.2.0/* /kafka/

RUN rm -rf /tmp/kafka_2.13-3.2.0

COPY scripts/start.sh bin/start.sh

COPY scripts/setup.sh bin/setup.sh

RUN ["chmod", "+x", "bin/setup.sh"]

RUN ["chmod", "+x", "/bin/start.sh"]
>>>>>>> origin/main

# Exposed ports
EXPOSE 2181/tcp
EXPOSE 8080/tcp

<<<<<<< HEAD
ENTRYPOINT [ "/entrypoint.sh" ]
=======
CMD ["bin/start.sh"]
>>>>>>> origin/main

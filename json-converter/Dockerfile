FROM alpine:3.16.1

# File Author / Maintainer.
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Environment variables.
ENV HOME_DIR=/home/json-converter
ENV BIN_DIR=${HOME_DIR}/bin
ENV ETC_DIR=${HOME_DIR}/etc
ENV LIBS_DIR=${HOME_DIR}/lib
ENV LOGS_DIR=${HOME_DIR}/logs

# Install dependencies.
RUN apk update && \
    apk add --no-cache bash ca-certificates curl nss unzip openjdk11-jre

# Create default directory structure.
RUN mkdir -p ${HOME_DIR} ${BIN_DIR} ${ETC_DIR} ${LIBS_DIR} ${LOGS_DIR}

# Setup the binary and configurations.
ADD src/main/resources/bin ${BIN_DIR}
ADD src/main/resources/etc ${ETC_DIR}
ADD build/distributions/json-converter.zip /home

RUN cd /home && \
    unzip /home/json-converter.zip && \
    rm json-converter.zip && \
    rm ${BIN_DIR}/json-converter.bat && \
    mv ${BIN_DIR}/json-converter ${BIN_DIR}/run.sh

# Define the permissions and entrypoint file.
RUN addgroup -S json-converter && \
    adduser -S json-converter -G json-converter && \
    chown -R json-converter:json-converter ${HOME_DIR} && \
    chmod +x ${BIN_DIR}/*.sh && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh

# Default user.
USER json-converter

# Default work directory.
WORKDIR ${HOME_DIR}

ENTRYPOINT ["/entrypoint.sh"]
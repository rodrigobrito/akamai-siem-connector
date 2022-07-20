FROM eclipse-mosquitto:2.0.14

# File Author / Maintainer.
LABEL maintainer="Felipe Vilarinho <fvilarin@akamai.com>"

# Install dependencies.
RUN apk update && \
    apk add --no-cache bash shadow ca-certificates curl jq su-exec && \
    chmod u+s /sbin/su-exec

# Environment variables.
ENV HOME_DIR=/home/scheduler
ENV BIN_DIR=${HOME_DIR}/bin
ENV ETC_DIR=${HOME_DIR}/etc
ENV DATA_DIR=${HOME_DIR}/data
ENV LOGS_DIR=${HOME_DIR}/logs

# Create default work structure.
RUN mkdir -p ${HOME_DIR} ${BIN_DIR} ${ETC_DIR}

# Adding startup and configuration files.
ADD bin ${BIN_DIR}
ADD etc ${ETC_DIR}

# Configure the scheduling and startup.
RUN rm /etc/crontabs/root && \
    echo "* * * * * /home/scheduler/bin/run.sh" > /etc/crontabs/root && \
    rm /mosquitto/config/mosquitto.conf && \
    ln -s ${ETC_DIR}/mosquitto.conf /mosquitto/config/mosquitto.conf && \
    ln -s /mosquitto/log ${LOGS_DIR} && \
    ln -s /mosquitto/data ${DATA_DIR} && \
    rm /docker-entrypoint.sh && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh

# Define default permissions.
RUN usermod -d ${HOME_DIR} mosquitto && \
    chown -R mosquitto:mosquitto /mosquitto && \
    chown -R mosquitto:mosquitto ${HOME_DIR} && \
    chmod +x ${BIN_DIR}/*.sh

# Define default user.
USER mosquitto

# Defune default work directory.
WORKDIR ${HOME_DIR}

# Expose ports.
EXPOSE 1883

ENTRYPOINT [ "/entrypoint.sh" ]
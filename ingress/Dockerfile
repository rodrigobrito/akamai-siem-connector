FROM alpine:3.16.1

# File Author / Maintainer,
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Install the dependencies.
RUN apk update && \
    apk add --no-cache bash ca-certificates curl nginx

# Environment variables.
ENV HOME_DIR=/home/ingress
ENV BIN_DIR=${HOME_DIR}/bin
ENV ETC_DIR=${HOME_DIR}/etc
ENV LOGS_DIR=${HOME_DIR}/logs
ENV QUEUE_DOMAIN=queue.akamai.siem
ENV DASHBOARDS_DOMAIN=dashboards.akamai.siem

# Create default work structure.
RUN mkdir -p ${HOME_DIR} ${BIN_DIR} ${ETC_DIR} ${LOGS_DIR} && \
    rm /etc/nginx/http.d/default.conf

# Adding startup and configuration files.
ADD bin ${BIN_DIR}
ADD etc/ssl ${ETC_DIR}/ssl
ADD etc/nginx/http.d/default.conf ${ETC_DIR}/http.d/

# Define default permissions.
RUN ln -s ${ETC_DIR}/ssl/certs/nginx-selfsigned.crt /etc/ssl/certs/nginx-selfsigned.crt && \
    ln -s ${ETC_DIR}/ssl/private/nginx-selfsigned.key /etc/ssl/private/nginx-selfsigned.key && \
    ln -s ${ETC_DIR}/http.d/default.conf /etc/nginx/http.d/default.conf && \
    chmod +x ${BIN_DIR}/*.sh && \
    ln -s ${BIN_DIR}/startup.sh /entrypoint.sh

# Define default work directory.
WORKDIR ${HOME_DIR}

ENTRYPOINT [ "/entrypoint.sh" ]
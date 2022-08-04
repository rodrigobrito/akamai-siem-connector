FROM nginx:stable-alpine

# File Author / Maintainer
LABEL maintainer="Leandro Cassiano Alves <lcassian@akamai.com>"

# Remove default config.
RUN rm /etc/nginx/conf.d/default.conf

# Copy configuration
COPY etc/default.conf /etc/nginx/conf.d/default.conf
COPY ssl/nginx-selfsigned.crt /etc/ssl/certs/nginx-selfsigned.crt
COPY ssl/nginx-selfsigned.key /etc/ssl/private/nginx-selfsigned.key

# Exposed ports
EXPOSE 443/tcp

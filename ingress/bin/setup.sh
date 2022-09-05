#!/bin/bash

# Define the prompt.
echo "export CLICOLOR=1" > ~/.bashrc
echo "export PS1='[\u@\h:\W]$ '" >> ~/.bashrc

if [ ! -f "/etc/nginx/http.d/default.conf" ]; then
  cp $ETC_DIR/nginx/http.d/default.conf /tmp/default.conf
  sed -i -e 's|${QUEUE_DOMAIN}|'"$QUEUE_DOMAIN"'|g' /tmp/default.conf
  sed -i -e 's|${DASHBOARDS_DOMAIN}|'"$DASHBOARDS_DOMAIN"'|g' /tmp/default.conf
  cp -f /tmp/default.conf /etc/nginx/http.d/default.conf
fi
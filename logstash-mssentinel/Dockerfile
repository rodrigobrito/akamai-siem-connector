FROM opensearchproject/logstash-oss-with-opensearch-output-plugin

# Add your logstash plugins setup here
RUN \
  bin/logstash-plugin install logstash-output-azure_loganalytics && \
  bin/logstash-plugin update logstash-input-azure_event_hubs && \
  bin/logstash-plugin install microsoft-logstash-output-azure-loganalytics

#COPY ./logstash.yml /usr/share/logstash/config/logstash.yml

FROM opensearchproject/logstash-oss-with-opensearch-output-plugin:7.16.3

# File Author / Maintainer.
LABEL maintainer="Leandro Cassiano <lcassian@akamai.com>"

# Add your logstash plugins setup here
RUN bin/logstash-plugin install logstash-output-azure_loganalytics && \
    bin/logstash-plugin update logstash-input-azure_event_hubs && \
    bin/logstash-plugin install microsoft-logstash-output-azure-loganalytics
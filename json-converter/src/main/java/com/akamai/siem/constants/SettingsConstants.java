package com.akamai.siem.constants;

public class SettingsConstants {
    public static final String DEFAULT_ETC_DIR = "etc/";
    public static final String DEFAULT_SETTINGS_FILENAME = "settings.json";
    public static final String DEFAULT_SETTINGS_FILEPATH = DEFAULT_ETC_DIR.concat(DEFAULT_SETTINGS_FILENAME);
    public static final String DEFAULT_KAFKA_INBOUND_TOPIC = "akamai-siem-raw";
    public static final String DEFAULT_KAFKA_OUTBOUND_TOPIC = "akamai-siem-processed";
    public static final String DEFAULT_KAFKA_BROKERS = "kafka-broker:9092";
    public static final Integer DEFAULT_CONVERTER_WORKERS = 10;
    public static final String SETTINGS_FILEPATH = "${ETC_DIR}/".concat(DEFAULT_SETTINGS_FILENAME);
    public static final String KAFKA_BROKERS_ATTRIBUTE_ID = "kafka.brokers";
    public static final String KAFKA_INBOUND_TOPIC_ATTRIBUTE_ID = "kafka.inboundTopic";
    public static final String KAFKA_OUTBOUND_TOPIC_ATTRIBUTE_ID = "kafka.outboundTopic";
    public static final String CONVERTER_WORKERS_ATTRIBUTE_ID = "converter.workers";
    public static final String CONVERTER_TEMPLATE_VALUE_ATTRIBUTE_ID = "converter.template.value";
    public static final String CONVERTER_TEMPLATE_BASE64_FIELDS_ATTRIBUTE_ID = "converter.template.base64Fields";
    public static final String CONVERTER_TEMPLATE_URL_ENCODED_FIELDS_ATTRIBUTE_ID = "converter.template.urlEncodedFields";
    public static final String CONVERTER_TEMPLATE_FIELDS_TO_BE_ADDED = "converter.template.fieldsToBeAdded";
}
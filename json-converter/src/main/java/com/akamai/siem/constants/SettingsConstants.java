package com.akamai.siem.constants;

public class SettingsConstants {
    public static final String DEFAULT_SETTINGS_FILENAME = "settings.json";
    public static final String DEFAULT_HOME_DIR = "./";
    public static final String DEFAULT_ETC_DIR = DEFAULT_HOME_DIR.concat("etc/");
    public static final String DEFAULT_SETTINGS_FILEPATH = DEFAULT_ETC_DIR.concat(DEFAULT_SETTINGS_FILENAME);
    public static final String SETTINGS_FILEPATH = "${ETC_DIR}".concat(DEFAULT_SETTINGS_FILENAME);
    public static final String KAFKA_ATTRIBUTE_ID = "kafka";
    public static final String BROKERS_ATTRIBUTE_ID = "brokers";
    public static final String INBOUND_TOPIC_ATTRIBUTE_ID = "inboundTopic";
    public static final String OUTBOUND_TOPIC_ATTRIBUTE_ID = "outboundTopic";
    public static final String CONVERTER_WORKERS_ATTRIBUTE_ID = "converterWorkers";
    public static final String CONVERTER_TEMPLATE_ATTRIBUTE_ID = "converterTemplate";
    public static final String BASE64_FIELDS_ATTRIBUTE_ID = "base64Fields";
    public static final String URL_ENCODED_FIELDS_ATTRIBUTE_ID = "urlEncodedFields";
}
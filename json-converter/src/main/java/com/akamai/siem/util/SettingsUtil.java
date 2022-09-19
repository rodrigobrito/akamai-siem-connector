package com.akamai.siem.util;

import com.akamai.siem.constants.SettingsConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SettingsUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static JsonNode settings = null;

    public static JsonNode get() throws IOException {
        if(settings == null)
            load();

        return settings;
    }

    public static void load(InputStream in) throws IOException{
        if (in == null)
            throw new IOException("Settings file not found!");

        settings = mapper.readValue(in, JsonNode.class);
    }

    public static void load(String settingsFilepath) throws IOException{
        File settingsFile = new File(settingsFilepath);

        if (!settingsFile.exists() || !settingsFile.canRead()) {
            InputStream in = SettingsUtil.class.getClassLoader().getResourceAsStream(settingsFilepath);

            if(in == null)
                in = SettingsUtil.class.getClassLoader().getResourceAsStream(SettingsConstants.DEFAULT_SETTINGS_FILEPATH);

            load(in);
        }
        else
            settings = mapper.readValue(new File(settingsFilepath), JsonNode.class);
    }

    public static void load() throws IOException{
        Map<String, String> environmentMap = System.getenv();
        Pattern pattern = Pattern.compile("\\$\\{(.*?)?}");
        String settingsFilepath = SettingsConstants.SETTINGS_FILEPATH;
        Matcher matcher = pattern.matcher(settingsFilepath);

        while(matcher.find()){
            String environmentVariableExpression = matcher.group(0);
            String environmentVariableName = matcher.group(1);
            String environmentVariableValue = environmentMap.get(environmentVariableName);

            if(environmentVariableValue == null)
                environmentVariableValue = StringUtils.EMPTY;

            settingsFilepath = StringUtils.replace(settingsFilepath, environmentVariableExpression, environmentVariableValue);
        }

        load(settingsFilepath);
    }

    public static String getKafkaBrokers() throws IOException{
        String brokers = JsonNodeUtil.getAttribute(get(), SettingsConstants.KAFKA_BROKERS_ATTRIBUTE_ID);

        if(brokers == null || brokers.isEmpty())
            brokers = SettingsConstants.DEFAULT_KAFKA_BROKERS;

        return brokers;
    }

    public static String getKafkaInboundTopic() throws IOException{
        String inboundTopic = JsonNodeUtil.getAttribute(get(), SettingsConstants.KAFKA_INBOUND_TOPIC_ATTRIBUTE_ID);

        if(inboundTopic == null || inboundTopic.isEmpty())
            inboundTopic = SettingsConstants.DEFAULT_KAFKA_INBOUND_TOPIC;

        return inboundTopic;
    }

    public static String getKafkaOutboundTopic() throws IOException{
        String outboundTopic = JsonNodeUtil.getAttribute(get(), SettingsConstants.KAFKA_OUTBOUND_TOPIC_ATTRIBUTE_ID);

        if(outboundTopic == null || outboundTopic.isEmpty())
            outboundTopic = SettingsConstants.DEFAULT_KAFKA_OUTBOUND_TOPIC;

        return outboundTopic;
    }

    public static Integer getConverterWorkers() throws IOException{
        Integer workers = JsonNodeUtil.getAttribute(get(), SettingsConstants.CONVERTER_WORKERS_ATTRIBUTE_ID);

        if(workers == null)
            workers = SettingsConstants.DEFAULT_CONVERTER_WORKERS;

        return workers;
    }

    public static String getConverterTemplateValue() throws IOException{
        return JsonNodeUtil.getAttribute(get(), SettingsConstants.CONVERTER_TEMPLATE_VALUE_ATTRIBUTE_ID);
    }

    public static List<String> getBase64Fields() throws IOException{
        return JsonNodeUtil.getAttribute(get(), SettingsConstants.CONVERTER_TEMPLATE_BASE64_FIELDS_ATTRIBUTE_ID);
    }

    public static List<String> getUrlEncodedFields() throws IOException{
        return JsonNodeUtil.getAttribute(get(), SettingsConstants.CONVERTER_TEMPLATE_URL_ENCODED_FIELDS_ATTRIBUTE_ID);
    }

    public static List<Map<String, String>> getFieldsToBeAdded() throws IOException{
        return JsonNodeUtil.getAttribute(get(), SettingsConstants.CONVERTER_TEMPLATE_FIELDS_TO_BE_ADDED);
    }
}
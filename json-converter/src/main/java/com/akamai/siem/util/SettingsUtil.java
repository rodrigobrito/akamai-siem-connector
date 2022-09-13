package com.akamai.siem.util;

import com.akamai.siem.constants.Constants;
import com.akamai.siem.constants.SettingsConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SettingsUtil {
    private static final Logger logger = LogManager.getLogger(Constants.DEFAULT_APP_NAME);
    private static final ObjectMapper mapper = new ObjectMapper();

    private static JsonNode settings = null;

    public static JsonNode load() throws IOException {
        if (settings == null) {
            logger.info("Loading settings...");

            Map<String, String> environmentMap = System.getenv();
            String pattern = "\\$\\{([A-Za-z\\d]+)}";
            Pattern expression = Pattern.compile(pattern);
            Matcher matcher = expression.matcher(SettingsConstants.SETTINGS_FILEPATH);
            String settingsFilepath = null;

            if (matcher.matches()) {
                settingsFilepath = SettingsConstants.SETTINGS_FILEPATH;

                for (int i = 0; i <= matcher.groupCount(); i++) {
                    String environmentVariableName = matcher.group(i).toUpperCase();
                    String environmentVariableValue = environmentMap.get(environmentVariableName);

                    if (environmentVariableValue == null)
                        environmentVariableValue = SettingsConstants.DEFAULT_SETTINGS_FILEPATH;

                    Pattern subExpression = Pattern.compile("\\$\\{".concat(matcher.group(i)).concat("\\}"));

                    settingsFilepath = subExpression.matcher(settingsFilepath).replaceAll(environmentVariableValue);
                }
            }

            File settingsFile = (settingsFilepath != null ? new File(settingsFilepath) : null);

            if (settingsFilepath == null || !settingsFile.exists() || !settingsFile.canRead()) {
                InputStream in = SettingsUtil.class.getClassLoader().getResourceAsStream(SettingsConstants.DEFAULT_SETTINGS_FILENAME);

                if (in == null)
                    throw new IOException("Settings file not found!");

                settings = mapper.readValue(SettingsUtil.class.getClassLoader().getResourceAsStream(SettingsConstants.DEFAULT_SETTINGS_FILENAME), JsonNode.class);
            }
            else
                settings = mapper.readValue(new File(settingsFilepath), JsonNode.class);

            logger.info("Settings loaded!");
        }

        return settings;
    }

    public static String getKafkaBrokers() throws IOException{
        JsonNode settings = load();

        try {
            return settings.get(SettingsConstants.KAFKA_ATTRIBUTE_ID).get(SettingsConstants.BROKERS_ATTRIBUTE_ID).asText();
        }
        catch(NullPointerException e){
            throw new IOException("Could not find the ".concat(SettingsConstants.BROKERS_ATTRIBUTE_ID).concat(" attribute!"));
        }
    }

    public static String getKafkaInboundTopic() throws IOException{
        JsonNode settings = load();

        try {
            return settings.get(SettingsConstants.KAFKA_ATTRIBUTE_ID).get(SettingsConstants.INBOUND_TOPIC_ATTRIBUTE_ID).asText();
        }
        catch(NullPointerException e){
            throw new IOException("Could not find the ".concat(SettingsConstants.INBOUND_TOPIC_ATTRIBUTE_ID).concat(" attribute!"));
        }
    }

    public static String getKafkaOutboundTopic() throws IOException{
        JsonNode settings = load();

        try {
            return settings.get(SettingsConstants.KAFKA_ATTRIBUTE_ID).get(SettingsConstants.OUTBOUND_TOPIC_ATTRIBUTE_ID).asText();
        }
        catch(NullPointerException e){
            throw new IOException("Could not find the ".concat(SettingsConstants.OUTBOUND_TOPIC_ATTRIBUTE_ID).concat(" attribute!"));
        }
    }

    public static Integer getConverterWorkers() throws IOException{
        JsonNode settings = load();

        return settings.get(SettingsConstants.CONVERTER_WORKERS_ATTRIBUTE_ID).asInt();
    }

    public static String getConverterTemplate() throws IOException{
        JsonNode settings = load();

        return settings.get(SettingsConstants.CONVERTER_TEMPLATE_ATTRIBUTE_ID).asText();
    }

    public static List<String> getBase64Fields() throws IOException{
        JsonNode settings = load();

        return mapper.convertValue(settings.get(SettingsConstants.BASE64_FIELDS_ATTRIBUTE_ID), new TypeReference<>() {});
    }

    public static List<String> getUrlEncodedFields() throws IOException{
        JsonNode settings = load();

        return mapper.convertValue(settings.get(SettingsConstants.URL_ENCODED_FIELDS_ATTRIBUTE_ID), new TypeReference<>() {});
    }
}
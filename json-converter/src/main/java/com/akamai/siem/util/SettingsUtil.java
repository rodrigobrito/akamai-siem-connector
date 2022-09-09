package com.akamai.siem.util;

import com.akamai.siem.constants.Constants;
import com.akamai.siem.constants.SettingsConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
            String pattern = "\\$\\{([A-Za-z\\d]+)\\}";
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

        return settings.get("kafka").get("brokers").asText();
    }

    public static String getKafkaInboundTopic() throws IOException{
        JsonNode settings = load();

        return settings.get("kafka").get("inboundTopic").asText();
    }

    public static String getKafkaOutboundTopic() throws IOException{
        JsonNode settings = load();

        return settings.get("kafka").get("inboundTopic").asText();
    }

    public static Integer getConverterWorkers() throws IOException{
        JsonNode settings = load();

        return settings.get("converterWorkers").asInt();
    }

    public static String getConverterTemplate() throws IOException{
        JsonNode settings = load();

        return settings.get("converterTemplate").asText();
    }
}
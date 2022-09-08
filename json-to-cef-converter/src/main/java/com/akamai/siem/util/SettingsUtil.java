package com.akamai.siem.util;

import com.akamai.siem.constants.SettingsConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SettingsUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Map<String, Object> settings = null;

    public static Map<String, Object> load() throws IOException {
        if (settings == null) {
            Map<String, String> environmentMap = System.getenv();
            String pattern = "\\$\\{([A-Za-z0-9]+)\\}";
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

            if (settingsFilepath == null || !settingsFile.exists() || !settingsFile.canRead())
                settings = mapper.readValue(SettingsUtil.class.getClassLoader().getResourceAsStream(SettingsConstants.DEFAULT_SETTINGS_FILENAME), new TypeReference<Map<String, Object>>() {
                });
            else
                settings = mapper.readValue(new File(settingsFilepath), new TypeReference<Map<String, Object>>() {
                });
        }

        return settings;
    }
}
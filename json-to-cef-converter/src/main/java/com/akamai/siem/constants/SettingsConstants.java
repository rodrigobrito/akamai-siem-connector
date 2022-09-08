package com.akamai.siem.constants;

public class SettingsConstants {
    public static final String DEFAULT_SETTINGS_FILENAME = "settings.json";
    public static final String DEFAULT_HOME_DIR = "./";
    public static final String DEFAULT_ETC_DIR = DEFAULT_HOME_DIR.concat("etc/");
    public static final String DEFAULT_SETTINGS_FILEPATH = DEFAULT_ETC_DIR.concat(DEFAULT_SETTINGS_FILENAME);
    public static final String SETTINGS_FILEPATH = "${ETC_DIR}".concat(DEFAULT_SETTINGS_FILENAME);
}
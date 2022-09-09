package com.akamai.siem.constants;

import java.util.Arrays;
import java.util.List;

public abstract class ConverterConstants {
    public static final List<String> BASE64_FIELDS = Arrays.asList("attackData.ruleVersions",
                                                                   "attackData.rules",
                                                                   "attackData.ruleActions",
                                                                   "ruleActions",
                                                                   "attackData.ruleMessages",
                                                                   "attackData.ruleData",
                                                                   "attackData.ruleSelectors",
                                                                   "attackData.ruleTags",
                                                                   "custom");
    public static final List<String> URLENCODED_FIELDS = Arrays.asList("httpMessage.requestHeaders",
                                                                       "httpMessage.responseHeaders");

    public static final String ALERT_ID = "alert";
    public static final String MONITOR_ID = "monitor";
    public static final String DETECT_ID = "detect";
    public static final String MITIGATE_ID = "mitigate";
    public static final String ABORT_ID = "abort";
    public static final String ACTIVITY_DETECTED = "Activity detected";
    public static final String ACTIVITY_MITIGATED = "Activity mitigated";
}
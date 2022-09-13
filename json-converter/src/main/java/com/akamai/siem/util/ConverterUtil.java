package com.akamai.siem.util;

import com.akamai.siem.constants.ConverterConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ConverterUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String decodeUrl(String encodedUrl) throws UnsupportedEncodingException {
        String decodedUrl = java.net.URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());

        return decodedUrl.replaceAll(" ", "+");
    }

    private static String decodeBase64(String encodedValue) throws IllegalArgumentException{
        byte[] decodedValue = Base64.getMimeDecoder().decode(encodedValue);

        return new String(decodedValue, StandardCharsets.UTF_8);
    }

    private static String name(JsonNode jsonNode) throws IOException {
        if(jsonNode != null) {
            String eventClassId = eventClassId(jsonNode);

            if (eventClassId.equalsIgnoreCase(ConverterConstants.DETECT_ID))
                return ConverterConstants.ACTIVITY_DETECTED;

            return ConverterConstants.ACTIVITY_MITIGATED;
        }

        return StringUtils.EMPTY;
    }

    private static String severity(JsonNode jsonNode) throws IOException {
        if(jsonNode != null) {
            String eventClassId = eventClassId(jsonNode);

            if (eventClassId.equalsIgnoreCase(ConverterConstants.DETECT_ID))
                return "5";

            return "10";
        }

        return StringUtils.EMPTY;
    }

    private static String eventClassId(JsonNode jsonNode) throws IOException{
        if(jsonNode != null) {
            String action = appliedAction(jsonNode);

            if (ConverterConstants.ALERT_ID.equalsIgnoreCase(action) || ConverterConstants.MONITOR_ID.equalsIgnoreCase(action))
                return ConverterConstants.DETECT_ID;

            return ConverterConstants.MITIGATE_ID;
        }

        return StringUtils.EMPTY;
    }

    private static String appliedAction(JsonNode jsonNode) throws IOException{
        if(jsonNode != null) {
            JsonNode slowPostActionNode = jsonNode.get(ConverterConstants.SLOW_POST_ACTION_ID);

            if (slowPostActionNode != null) {
                String slowPostAction = slowPostActionNode.asText();

                if (slowPostAction != null && slowPostAction.length() > 0) {
                    if (slowPostAction.equalsIgnoreCase("A"))
                        return ConverterConstants.ABORT_ID;

                    return ConverterConstants.ALERT_ID;
                }
            }

            JsonNode ruleActionsNode = jsonNode.get(ConverterConstants.RULE_ACTIONS_ID);

            if (ruleActionsNode != null) {
                String action = ruleActionsNode.asText();

                if (SettingsUtil.getUrlEncodedFields().contains(ConverterConstants.RULE_ACTIONS_ID))
                    action = decodeUrl(action);

                if (SettingsUtil.getBase64Fields().contains(ConverterConstants.RULE_ACTIONS_ID))
                    action = decodeBase64(action);

                return action;
            }
        }

        return StringUtils.EMPTY;
    }

    private static String ipv6Src(String clientIp) {
        if(clientIp != null && !clientIp.isEmpty()) {
            Pattern ipv6Pattern = Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");
            Matcher matcher = ipv6Pattern.matcher(clientIp);

            if (matcher.find())
                return clientIp;
        }

        return StringUtils.EMPTY;
    }

    public static JsonNode getJsonAttribute(JsonNode jsonNode, String attributeName) {
        if(jsonNode != null && attributeName != null && !attributeName.isEmpty()) {
            String[] attributes = attributeName.split("\\.");
            JsonNode parentNode = jsonNode;

            for (String item : attributes) {
                parentNode = parentNode.get(item);

                if (parentNode == null)
                    break;
            }

            return parentNode;
        }

        return null;
    }

    private static String requestURL(JsonNode jsonNode) {
        String requestURL = StringUtils.EMPTY;

        if(jsonNode != null) {
            JsonNode tlsNode = jsonNode.get(ConverterConstants.TLS_ID);

            if (tlsNode != null && !tlsNode.asText().isEmpty())
                requestURL = ConverterConstants.HTTPS_SCHEME_ID;
            else
                requestURL = ConverterConstants.HTTP_SCHEME_ID;

            JsonNode hostNode = jsonNode.get(ConverterConstants.HOST_ID);

            if (hostNode != null && !hostNode.asText().isEmpty())
                requestURL += hostNode.asText();
            else
                requestURL = StringUtils.EMPTY;

            if (!requestURL.isEmpty()) {
                JsonNode pathNode = jsonNode.get(ConverterConstants.PATH_ID);

                if (pathNode != null && !pathNode.asText().isEmpty())
                    requestURL += pathNode.asText();
                else
                    requestURL = StringUtils.EMPTY;
            }
        }

        return requestURL;
    }

    public static String fromJson(String jsonFormattedValue) throws IOException{
        JsonNode jsonNode = mapper.readValue(jsonFormattedValue, JsonNode.class);

        return fromJson(jsonNode);
    }

    public static String fromJson(JsonNode jsonNode) throws IOException {
        String converterTemplate = SettingsUtil.getConverterTemplate();
        Pattern pattern = Pattern.compile("#\\{(.*?)?}");
        Matcher matcher = pattern.matcher(converterTemplate);

        while (matcher.find()) {
            String expression = matcher.group(0);
            String attributeName = matcher.group(1);

            do {
                JsonNode attributeNode = getJsonAttribute(jsonNode, attributeName);

                if(attributeNode == null || attributeNode.isValueNode()) {
                    String attributeValue = (attributeNode == null ? StringUtils.EMPTY : attributeNode.asText());

                    if(attributeValue != null && !attributeValue.isEmpty()){
                        if(SettingsUtil.getUrlEncodedFields().contains(attributeName))
                            attributeValue = decodeUrl(attributeValue);

                        if(SettingsUtil.getBase64Fields().contains(attributeName))
                            attributeValue = decodeBase64(attributeValue).trim();
                    }

                    converterTemplate = StringUtils.replace(converterTemplate, expression, attributeValue);
                }
                else
                    break;
            }
            while (converterTemplate.contains(expression));
        }

        pattern = Pattern.compile("@\\{(.*?)\\((.*?)\\)}");
        matcher = pattern.matcher(converterTemplate);

        while (matcher.find()) {
            String expression = matcher.group(0);
            String methodName = matcher.group(1);
            String[] methodParameters = matcher.group(2).split(",");
            Object[] methodParametersValues = new Object[methodParameters.length];
            int cont = 0;

            for(String methodParameterName : methodParameters){
                methodParameterName = StringUtils.replace(methodParameterName, "#{", "");
                methodParameterName = StringUtils.replace(methodParameterName, "}", "");

                JsonNode methodParameterNode = getJsonAttribute(jsonNode, methodParameterName);

                if(methodParameterNode == null || methodParameterNode.isValueNode()) {
                    if (methodParameterNode == null)
                        methodParametersValues[cont] = null;
                    else if (methodParameterNode.isBoolean())
                        methodParametersValues[cont] = methodParameterNode.asBoolean();
                    else if (methodParameterNode.isFloat() || methodParameterNode.isDouble() || methodParameterNode.isBigDecimal())
                        methodParametersValues[cont] = methodParameterNode.asDouble();
                    else if (methodParameterNode.isLong() || methodParameterNode.isBigInteger())
                        methodParametersValues[cont] = methodParameterNode.asLong();
                    else if (methodParameterNode.isInt())
                        methodParametersValues[cont] = methodParameterNode.asInt();
                    else
                        methodParametersValues[cont] = methodParameterNode.asText();
                }
                else
                    methodParametersValues[cont] = methodParameterNode;

                cont++;
            }

            String attributeValue = null;

            try{
                switch (methodName) {
                    case ConverterConstants.EVENT_CLASS_ID:
                        attributeValue = eventClassId((JsonNode) methodParametersValues[0]);

                        break;
                    case ConverterConstants.APPLIED_ACTION_ID:
                        attributeValue = appliedAction((JsonNode) methodParametersValues[0]);

                        break;
                    case ConverterConstants.NAME_ID:
                        attributeValue = name((JsonNode) methodParametersValues[0]);

                        break;
                    case ConverterConstants.SEVERITY_ID:
                        attributeValue = severity((JsonNode) methodParametersValues[0]);

                        break;
                    case ConverterConstants.IPV6_SRC_ID:
                        attributeValue = ipv6Src((String) methodParametersValues[0]);

                        break;
                    case ConverterConstants.REQUEST_URL_ID:
                        attributeValue = requestURL((JsonNode) methodParametersValues[0]);

                        break;
                }
            }
            catch(Throwable ignored){
            }

            if(attributeValue == null)
                attributeValue = StringUtils.EMPTY;

            converterTemplate = StringUtils.replace(converterTemplate, expression, attributeValue);
        }

        return converterTemplate.replaceAll("\r", "");
    }
}
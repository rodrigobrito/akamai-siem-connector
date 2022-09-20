package com.akamai.siem.util;

import com.akamai.siem.constants.ConverterConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ConverterUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String decodeUrl(String encodedUrl){
        if(encodedUrl != null)
            return java.net.URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);

        return null;
    }

    public static String decodeBase64(String encodedValue){
        if(encodedValue != null) {
            byte[] decodedValue = Base64.getMimeDecoder().decode(encodedValue);

            return new String(decodedValue, StandardCharsets.UTF_8);
        }

        return null;
    }

    private static String name(JsonNode jsonNode){
        if(jsonNode != null) {
            String eventClassId = eventClassId(jsonNode);

            if (eventClassId.equalsIgnoreCase(ConverterConstants.DETECT_ID))
                return ConverterConstants.ACTIVITY_DETECTED;

            return ConverterConstants.ACTIVITY_MITIGATED;
        }

        return StringUtils.EMPTY;
    }

    private static String severity(JsonNode jsonNode){
        if(jsonNode != null) {
            String eventClassId = eventClassId(jsonNode);

            if (eventClassId.equalsIgnoreCase(ConverterConstants.DETECT_ID))
                return "5";

            return "10";
        }

        return StringUtils.EMPTY;
    }

    private static String eventClassId(JsonNode jsonNode){
        if(jsonNode != null) {
            String action = appliedAction(jsonNode);

            if (ConverterConstants.ALERT_ID.equalsIgnoreCase(action) || ConverterConstants.MONITOR_ID.equalsIgnoreCase(action))
                return ConverterConstants.DETECT_ID;

            return ConverterConstants.MITIGATE_ID;
        }

        return StringUtils.EMPTY;
    }

    private static String appliedAction(JsonNode jsonNode){
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

            if (ruleActionsNode != null)
                return ruleActionsNode.asText();
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

    public static void decodeUrlEncodedFields(JsonNode jsonNode) throws IOException{
        List<String> urlEncodedFields = SettingsUtil.getUrlEncodedFields();

        if(urlEncodedFields != null && !urlEncodedFields.isEmpty()){
            for(String urlEncodedField : urlEncodedFields){
                String encodedValue = JsonNodeUtil.getAttribute(jsonNode, urlEncodedField);
                String decodedValue = decodeUrl(encodedValue);

                JsonNodeUtil.setAttribute(jsonNode, urlEncodedField, decodedValue);
            }
        }
    }

    public static void decodeBase64Fields(JsonNode jsonNode) throws IOException{
        List<String> base64Fields = SettingsUtil.getBase64Fields();

        if(base64Fields != null && !base64Fields.isEmpty()){
            for(String base64Field : base64Fields){
                String encodedValue = JsonNodeUtil.getAttribute(jsonNode, base64Field);
                String decodedValue = decodeBase64(encodedValue);

                JsonNodeUtil.setAttribute(jsonNode, base64Field, decodedValue);
            }
        }
    }

    public static void addFields(JsonNode jsonNode) throws IOException{
        List<Map<String, String>> fieldsToBeAdded = SettingsUtil.getFieldsToBeAdded();

        if(fieldsToBeAdded != null && !fieldsToBeAdded.isEmpty()){
            for(Map<String, String> fieldToBeAdded : fieldsToBeAdded){
                String name = fieldToBeAdded.get("name");
                String value = fieldToBeAdded.get("value");

                JsonNodeUtil.setAttribute(jsonNode, name, processExpressions(jsonNode, value));
            }
        }
    }

    public static String fromJson(String jsonFormattedValue) throws IOException{
        JsonNode jsonNode = mapper.readValue(jsonFormattedValue, JsonNode.class);

        return fromJson(jsonNode);
    }

    public static String fromJson(JsonNode jsonNode) throws IOException {
        decodeUrlEncodedFields(jsonNode);
        decodeBase64Fields(jsonNode);
        addFields(jsonNode);

        String converterTemplateValue = SettingsUtil.getConverterTemplateValue();

        if (converterTemplateValue != null && !converterTemplateValue.isEmpty())
            converterTemplateValue = processExpressions(jsonNode, converterTemplateValue);
        else
            converterTemplateValue = mapper.writeValueAsString(jsonNode);

        converterTemplateValue = converterTemplateValue.replaceAll("\r", "");

        return converterTemplateValue;
    }

    private static String processExpressions(JsonNode jsonNode, String valueWithExpressions) throws IOException{
        if(valueWithExpressions != null && !valueWithExpressions.isEmpty()) {
            Pattern pattern = Pattern.compile("@\\{(.*?)\\((.*?)\\)}");
            Matcher matcher = pattern.matcher(valueWithExpressions);

            while (matcher.find()) {
                String expression = matcher.group(0);
                String methodName = matcher.group(1);
                String[] methodParameters = matcher.group(2).split(",");
                Object[] methodParametersValues = new Object[methodParameters.length];
                int cont = 0;

                for (String methodParameterName : methodParameters) {
                    methodParameterName = StringUtils.replace(methodParameterName, "#{", "");
                    methodParameterName = StringUtils.replace(methodParameterName, "}", "");
                    methodParametersValues[cont] = JsonNodeUtil.getAttribute(jsonNode, methodParameterName);

                    cont++;
                }

                String attributeValue;

                try {
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
                        default:
                            attributeValue = null;
                    }
                }
                catch (Throwable ignored) {
                    attributeValue = null;
                }

                if (attributeValue == null)
                    attributeValue = "null";

                valueWithExpressions = StringUtils.replace(valueWithExpressions, expression, attributeValue);
            }

            pattern = Pattern.compile("#\\{(.*?)?}");
            matcher = pattern.matcher(valueWithExpressions);

            while (matcher.find()) {
                String expression = matcher.group(0);
                String attributeName = matcher.group(1);
                Object attributeValue = JsonNodeUtil.getAttribute(jsonNode, attributeName);

                if(attributeValue == null)
                    attributeValue = StringUtils.EMPTY;

                if(attributeValue instanceof String) {
                    do {
                        valueWithExpressions = StringUtils.replace(valueWithExpressions, expression, (String)attributeValue);
                    }
                    while (valueWithExpressions.contains(expression));
                }
            }
        }

        return valueWithExpressions;
    }
}
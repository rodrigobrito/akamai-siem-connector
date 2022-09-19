package com.akamai.siem.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public abstract class JsonNodeUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <O> O getAttribute(JsonNode node, String attributeName) throws IOException {
        String[] parts = attributeName.split("\\.");
        JsonNode parentNode = node;

        if(parts.length > 0) {
            for (String part : parts) {
                parentNode = parentNode.get(part);

                if (parentNode == null)
                    break;
            }
        }

        if(parentNode != null) {
            if (parentNode.isTextual())
                return (O)parentNode.asText();
            else if (parentNode.isFloat() || parentNode.isDouble() || parentNode.isBigDecimal())
                return (O)Double.valueOf(parentNode.asDouble());
            else if (parentNode.isLong() || parentNode.isBigInteger())
                return (O)Long.valueOf(parentNode.asLong());
            else if (parentNode.isInt())
                return (O)Integer.valueOf(parentNode.asInt());
            else if (parentNode.isBoolean() || parentNode.isBinary())
                return (O)Boolean.valueOf(parentNode.asBoolean());
            else if (parentNode.isArray())
                return (O)mapper.treeToValue(parentNode, List.class);

            return (O)parentNode;
        }

        return null;
    }

    public static <O> void setAttribute(JsonNode jsonNode, String attributeName, O value){
        String[] parts = attributeName.split("\\.");
        JsonNode parentNode = jsonNode;

        if(parts.length > 1) {
            for (int i = 0 ; i < (parts.length - 1) ; i++) {
                String part = parts[i];
                JsonNode node = parentNode.get(part);

                if (node == null)
                    parentNode = ((ObjectNode)parentNode).putObject(part);
                else
                    parentNode = node;
            }

            attributeName = parts[parts.length - 1];
        }

        if(parentNode != null) {
            if (value instanceof String)
                ((ObjectNode) parentNode).put(attributeName, (String) value);
            else if (value instanceof Float)
                ((ObjectNode) parentNode).put(attributeName, (Float) value);
            else if (value instanceof Double)
                ((ObjectNode) parentNode).put(attributeName, (Double) value);
            else if (value instanceof BigDecimal)
                ((ObjectNode) parentNode).put(attributeName, (BigDecimal) value);
            else if (value instanceof Long)
                ((ObjectNode) parentNode).put(attributeName, (Long) value);
            else if (value instanceof BigInteger)
                ((ObjectNode) parentNode).put(attributeName, (BigInteger) value);
            else if (value instanceof Integer)
                ((ObjectNode) parentNode).put(attributeName, (Integer) value);
            else if (value instanceof Boolean)
                ((ObjectNode) parentNode).put(attributeName, (Boolean) value);
            else if (value instanceof List) {
                ArrayNode valuesNode = mapper.valueToTree(value);

                ((ObjectNode) parentNode).putArray(attributeName).addAll(valuesNode);
            }
        }
    }
}

package com.akamai.siem;

import com.akamai.siem.util.ConverterUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConverterUtilTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static JsonNode jsonMessageNode;
    private static String cefMessage;

    @BeforeAll
    static void loadMessages(){
        try {
            jsonMessageNode = mapper.readValue(ConverterUtilTest.class.getClassLoader().getResourceAsStream("jsonMessage.json"), JsonNode.class);
            cefMessage = Files.readString(Paths.get(Objects.requireNonNull(ConverterUtilTest.class.getClassLoader().getResource("cefMessage.txt")).toURI()));
        }
         catch(IOException | URISyntaxException e){
            Assertions.fail();
        }
    }

    @Test
    void fromJsonToCef_test(){
        try {
            Assertions.assertEquals(cefMessage, ConverterUtil.fromJson(jsonMessageNode));
        }
        catch(IOException e){
            Assertions.fail();
        }
    }
}
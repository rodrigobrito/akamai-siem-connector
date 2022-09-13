package com.akamai.siem;

import com.akamai.siem.util.ConverterUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ConverterUtilTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static JsonNode messageNode;

    @BeforeAll
    static void loadMessage(){
        try {
            messageNode = mapper.readValue(ConverterUtilTest.class.getClassLoader().getResourceAsStream("message.json"), JsonNode.class);
        }
        catch(IOException e){
            Assertions.fail();
        }
    }

    @Test
    void fromJson_test(){
        try {
            System.out.println(ConverterUtil.fromJson(messageNode));
        }
        catch(IOException e){
            Assertions.fail();
        }
    }
}

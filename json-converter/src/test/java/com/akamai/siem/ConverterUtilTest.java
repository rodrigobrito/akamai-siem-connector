package com.akamai.siem;

import com.akamai.siem.constants.TestConstants;
import com.akamai.siem.util.ConverterUtil;
import com.akamai.siem.util.SettingsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConverterUtilTest {
    private static String originMessageNode;
    private static String expectedCefMessage;
    private static String expectedDecodedMessage;

    @BeforeAll
    static void loadMessages(){
        try {
            originMessageNode = Files.readString(Paths.get(Objects.requireNonNull(ConverterUtilTest.class.getClassLoader().getResource(TestConstants.DEFAULT_ORIGINAL_MESSAGE_FILENAME)).toURI()));
            expectedCefMessage = Files.readString(Paths.get(Objects.requireNonNull(ConverterUtilTest.class.getClassLoader().getResource(TestConstants.DEFAULT_EXPECTED_CEF_MESSAGE_FILENAME)).toURI()));
            expectedDecodedMessage = Files.readString(Paths.get(Objects.requireNonNull(ConverterUtilTest.class.getClassLoader().getResource(TestConstants.DEFAULT_EXPECTED_DECODED_MESSAGE_FILENAME)).toURI()));
        }
         catch(IOException | URISyntaxException e){
            Assertions.fail(e); 
        }
    }

    @Test
    void fromJsonToCef_test() {
        try {
            SettingsUtil.load(TestConstants.DEFAULT_CEF_SETTINGS_FILEPATH);

            Assertions.assertEquals(expectedCefMessage, ConverterUtil.fromJson(originMessageNode));
        }
        catch(Throwable e){
            Assertions.fail(e);
        }
    }

    @Test
    void decodeJsonAndEnrich_test() {
        try {
            Assertions.assertEquals(expectedDecodedMessage, ConverterUtil.fromJson(originMessageNode));
        }
        catch(IOException e){
            Assertions.fail(e);
        }
    }
}
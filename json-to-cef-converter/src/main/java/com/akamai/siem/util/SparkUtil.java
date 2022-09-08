package com.akamai.siem.util;

import com.akamai.siem.constants.Constants;
import com.akamai.siem.constants.SettingsConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class SparkUtil {
    public static JavaStreamingContext getContext() throws IOException {
        Map<String, Object> settings = SettingsUtil.load();
        SparkConf conf = new SparkConf().setAppName(Constants.DEFAULT_APP_NAME).setMaster("local[*]").set("spark.driver.bindAddress", "127.0.0.1");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, Object> sparkSettings = (Map<String, Object>) settings.get("spark");

        return new JavaStreamingContext(sc, new Duration((Integer)sparkSettings.get(SettingsConstants.BATCH_DURATION_ATTRIBUTE_ID)));
    }

    public static JavaInputDStream<ConsumerRecord<String, String>> getKafkaInboundStream(JavaStreamingContext context) throws IOException {
        Map<String, Object> settings = SettingsUtil.load();
        Map<String, Object> kafkaSettings = (Map<String, Object>)settings.get("kafka");
        Map<String, Object> kafkaParams = new HashMap<>();

        kafkaParams.put("bootstrap.servers", (String)kafkaSettings.get("brokers"));
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "0");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singleton((String)kafkaSettings.get("inboundTopic"));

        return KafkaUtils.createDirectStream(context, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));
    }
}

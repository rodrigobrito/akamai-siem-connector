package com.akamai.siem.util.helpers;

import com.akamai.siem.constants.Constants;
import com.akamai.siem.util.ConverterUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConverterWorker implements Runnable{
    private static final Logger logger = LogManager.getLogger(Constants.DEFAULT_APP_NAME);

    private final KafkaProducer<String, String> producer;
    private final ConsumerRecord<String, String> record;
    private final String topic;

    public ConverterWorker(KafkaProducer<String, String> producer, ConsumerRecord<String, String> record, String topic){
        this.producer = producer;
        this.record = record;
        this.topic = topic;
    }

    @Override
    public void run() {
        try {
            final String key = this.record.key();

            String value = this.record.value();

            if(value != null && !value.isEmpty()) {
                logger.info("Processing message " + key + "...");

                value = ConverterUtil.fromJson(this.record.value());

                logger.info("Message " + key + " was processed!");
                logger.info("Pushing new value of message " + key + " to " + this.topic + "...");

                this.producer.send(new ProducerRecord<>(this.topic, key, value));
                this.producer.flush();

                logger.info("New value of message " + key + " pushed to " + this.topic + "!");
            }
        }
        catch(KafkaException | IOException e){
            logger.error(e);
        }
    }
}
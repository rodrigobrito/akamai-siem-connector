/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.akamai.siem;

import com.akamai.siem.constants.Constants;
import com.akamai.siem.util.SettingsUtil;
import com.akamai.siem.util.helpers.ConverterWorker;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    private static final Logger logger = LogManager.getLogger(Constants.DEFAULT_APP_NAME);

    private static Properties prepareKafkaConnectionParameters() throws IOException{
        String brokers = SettingsUtil.getKafkaBrokers();

        logger.info("Preparing the connection to " + brokers + "...");

        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SettingsUtil.getKafkaBrokers());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, Constants.DEFAULT_APP_NAME);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return properties;
    }

    public void run(){
        KafkaProducer<String, String> producer = null;
        KafkaConsumer<String, String> consumer = null;

        try {
            final Properties kafkaConnectionParameters = prepareKafkaConnectionParameters();
            final String inboundTopic = SettingsUtil.getKafkaInboundTopic();
            final String outboundTopic = SettingsUtil.getKafkaInboundTopic();

            producer = new KafkaProducer<String, String>(kafkaConnectionParameters);
            consumer = new KafkaConsumer<>(kafkaConnectionParameters);

            logger.info("Subscribing to the inbound topic " + inboundTopic + "...");

            consumer.subscribe(Arrays.asList(inboundTopic));

            logger.info("Fetching messages from the topic " + inboundTopic + "...");

            ExecutorService converterWorkersManager = Executors.newFixedThreadPool(SettingsUtil.getConverterWorkers());

            while (true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                    if(!records.isEmpty()) {
                        logger.info(records.count() + " messages fetched from the topic " + inboundTopic);

                        for (ConsumerRecord<String, String> record : records)
                            converterWorkersManager.submit(new ConverterWorker(producer, record, outboundTopic));
                    }
                }
                catch(KafkaException e){
                    logger.error(e);

                    break;
                }
            }

            logger.info("Waiting the conversion workers stop...");

            try {
                converterWorkersManager.shutdown();
                converterWorkersManager.awaitTermination(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            catch(InterruptedException e){
            }
        }
        catch(IOException e){
            logger.error(e);
        }
        finally {
            if(producer != null)
                producer.close();

            if(consumer != null)
                consumer.close();
        }
    }

    public static void main(String[] args){
        new App().run();
    }
}
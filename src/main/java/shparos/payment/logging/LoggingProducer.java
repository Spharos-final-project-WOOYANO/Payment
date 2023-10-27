package shparos.payment.logging;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;


    public LoggingProducer(@Value("${kafka.clusters.bootstrap.servers}") String bootstrapServers,
                           @Value("${logging.topic}")String topic) {

        // Producer Initialization ';'
        Properties props = new Properties();
        log.info("bootstrapServers: {}", bootstrapServers);
        // kafka:29092
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // "key:value"
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //acks
        props.put(ProducerConfig.ACKS_CONFIG, "0");

        this.producer = new KafkaProducer<>(props); //producer 초기화
        this.topic = topic;
    }

    // Kafka Cluster [key, value] Produce
    public void sendMessage(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
                // System.err.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
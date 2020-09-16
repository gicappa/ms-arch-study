package eu.cise;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Properties;

import static io.restassured.RestAssured.with;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
@QuarkusTestResource(KafkaResource.class)
public class ValidatedMessageTest {

    static KafkaConsumer<String, String> consumer;

    @BeforeAll
    public static void beforeAll() {
        consumer = createConsumer();
    }

    public static KafkaConsumer<String, String> createConsumer() {
        var consumer = new KafkaConsumer<String, String>(kafkaConfig());
        consumer.subscribe(singletonList("validated-messages"));
        return consumer;
    }

    private static Properties kafkaConfig() {
        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS_CONFIG, System.getProperty("kafka.bootstrap.servers"));
        props.put(GROUP_ID_CONFIG, "cise");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Test
    public void it_sends_a_message_to_kafka_channel() {
        with().body("my message").post("/messages");

        var records = consumer.poll(Duration.ofSeconds(10)).iterator().next();

        assertNull(records.key());

        assertEquals("msg: my message\nstep: verified", records.value());
    }

}

package com.example.kafkamessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer2 {
    public static void main(String[] args) throws Exception {
        // Cấu hình Consumer
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test2-group"); // Group ID khác với Consumer cũ
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Khởi tạo Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Đăng ký topic test2-topic
        consumer.subscribe(Collections.singletonList("test2-topic"));

        // Tạo đối tượng JSON mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Nhận và xử lý dữ liệu JSON
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                String jsonMessage = record.value();
                Message message = objectMapper.readValue(jsonMessage, Message.class);
                System.out.printf("Nhận từ test2-topic: offset = %d, value = %s%n", record.offset(), message);
            }
        }
    }
}

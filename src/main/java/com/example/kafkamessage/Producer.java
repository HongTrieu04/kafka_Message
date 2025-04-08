package com.example.kafkamessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) throws Exception {
        // Cấu hình Producer
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Khởi tạo Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Tạo đối tượng JSON mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Gửi dữ liệu tới test-topic
        for (int i = 0; i < 3; i++) {
            Message message = new Message("ID_" + i, "Nội dung tin nhắn " + i, System.currentTimeMillis());
            String jsonMessage = objectMapper.writeValueAsString(message);

            ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", jsonMessage);
            producer.send(record);
            System.out.println("Đã gửi tới test-topic: " + jsonMessage);
        }

        // Gửi dữ liệu tới test2-topic
        for (int i = 0; i < 3; i++) {
            Message message = new Message("NEW_ID_" + i, "Tin nhắn mới " + i, System.currentTimeMillis());
            String jsonMessage = objectMapper.writeValueAsString(message);

            ProducerRecord<String, String> record = new ProducerRecord<>("test2-topic", jsonMessage);
            producer.send(record);
            System.out.println("Đã gửi tới test2-topic: " + jsonMessage);
        }

        // Đóng Producer
        producer.close();
    }
}

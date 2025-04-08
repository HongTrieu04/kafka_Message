package com.example.kafkamessage;

import lombok.Data;

@Data
public class Message {
    private String id;
    private String content;
    private long timestamp;

    // Constructor để khởi tạo tất cả các field
    public Message(String id, String content, long timestamp) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Constructor không tham số (nếu cần cho deserialization)
    public Message() {
    }
}
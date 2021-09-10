package com.example.loggingservice;


import java.util.UUID;

public class Message {
    private UUID uuid;
    private String text;

    public Message(UUID uuid, String text) {
        this.uuid = uuid;
        this.text = text;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid=" + uuid +
                ", text='" + text + '\'' +
                '}';
    }
}

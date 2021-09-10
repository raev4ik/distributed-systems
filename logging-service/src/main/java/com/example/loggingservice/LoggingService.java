package com.example.loggingservice;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class LoggingService {

    private HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient();
    private Map<UUID, String> loggingMap = hazelcastInstanceClient.getMap("loggingMap");

    private void log(String log) {
        System.out.println(log);
    }

    public void addMessage(Message message) {
        log("Put message into halecast map" + message);
        loggingMap.put(message.getUuid(), message.getText());
    }

    public String getMessages() {
        String messages = loggingMap.values().toString();
        log("Get messages from hazelcast map" + messages);
        return messages;
    }

}

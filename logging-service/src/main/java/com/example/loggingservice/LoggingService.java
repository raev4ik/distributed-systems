package com.example.loggingservice;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class LoggingService {

    @Value("${map.name}")
    private String mapName;

    private HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient();

    private void log(String log) {
        System.out.println(log);
    }

    public void addMessage(Message message) {
        log("Put message into halecast map" + message);
        hazelcastInstanceClient.getMap(mapName).put(message.getUuid(), message.getText());
    }

    public String getMessages() {
        String messages = hazelcastInstanceClient.getMap(mapName).values().toString();
        log("Get messages from hazelcast map" + messages);
        return messages;
    }

}

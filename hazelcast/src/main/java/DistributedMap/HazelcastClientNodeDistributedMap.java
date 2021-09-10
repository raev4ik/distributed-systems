package DistributedMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.UUID;

public class HazelcastClientNodeDistributedMap {
    public static void main(String[] args) {
        HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient();
        Map<Integer, UUID> map = hazelcastInstanceClient.getMap("dsmap");
        for (int i = 0; i < 1000; i++) {
            map.put(i, UUID.randomUUID());
        }
        hazelcastInstanceClient.shutdown();
    }
}

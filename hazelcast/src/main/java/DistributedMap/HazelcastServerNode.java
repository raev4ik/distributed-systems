package DistributedMap;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.Serializable;
import java.util.Map;

public class HazelcastServerNode {
    public static void main(String[] args) {

        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
        Map<String, HazelcastClientNodeDMNoLock.Value> mapNoLock = hzInstance.getMap("mapNoLock");
        mapNoLock.put("1", new HazelcastClientNodeDMNoLock.Value());
        Map<String, HazelcastClientNodeDMOptiLock.Value> mapOptiLock = hzInstance.getMap("mapOptiLock");
        mapOptiLock.put("1", new HazelcastClientNodeDMOptiLock.Value());
        Map<String, HazelcastClientNodeDMPessLock.Value> mapPessLock = hzInstance.getMap("mapPessLock");
        mapPessLock.put("1", new HazelcastClientNodeDMPessLock.Value());
    }

}

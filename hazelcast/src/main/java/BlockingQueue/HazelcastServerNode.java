package BlockingQueue;

import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastServerNode {
    public static void main(String[] args) {
        Config config = new Config().addQueueConfig(new QueueConfig().setName("queue").setMaxSize(50));
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
    }
}

package task3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Producer {

    private final static String QUEUE_NAME_1 = "task3-default"; // delete old
    private final static String QUEUE_NAME_2 = "task3-reject-publish"; // reject new

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel1 = connection.createChannel();
            Channel channel2 = connection.createChannel()) {
            channel1.queueDelete(QUEUE_NAME_1);
            channel2.queueDelete(QUEUE_NAME_2);
            Map<String, Object> args1 = new HashMap<String, Object>();
            args1.put("x-max-length", 10);
            Map<String, Object> args2 = new HashMap<String, Object>();
            args2.put("x-max-length", 10);
            args2.put("x-overflow", "reject-publish"); // Block publish if queue is overflown
            channel1.queueDeclare(QUEUE_NAME_1, false, false, false, args1);
            channel2.queueDeclare(QUEUE_NAME_2, false, false, false, args2);
            for (int i = 1;;i++) {
                channel1.basicPublish("", QUEUE_NAME_1, null, ("message" + i).getBytes(StandardCharsets.UTF_8));
                channel2.basicPublish("", QUEUE_NAME_2, null, ("message" + i).getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent message: message-" + i);
                Thread.sleep(2000);
            }
        }
    }
}

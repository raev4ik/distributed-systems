package task5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Producer {

    private final static String QUEUE_NAME = "task5";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-message-ttl", 9999); // 10 seconds TTL
            channel.queueDeclare(QUEUE_NAME, false, false, false, args);
            for (int i = 1;;i++) {
                channel.basicPublish("", QUEUE_NAME, null, ("message" + i).getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent message: message-" + i);
                Thread.sleep(5000);
            }
        }
    }
}

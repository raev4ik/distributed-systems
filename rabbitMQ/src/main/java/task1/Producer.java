package task1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Producer {
    private final static String QUEUE_NAME = "task1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 1; i < 4; i++) {
                channel.basicPublish("", QUEUE_NAME, null, ("message-" + i).getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent message: message-" + i);
                Thread.sleep(5000);
            }
        }
    }
}

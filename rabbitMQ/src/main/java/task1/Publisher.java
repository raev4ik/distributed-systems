package task1;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

public class Publisher {
    private final static String EXCHANGE_NAME = "exchange1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            for (int i = 1; i < 4; i++) {
                channel.basicPublish(EXCHANGE_NAME, "", null, ("message-" + i).getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent message: message-" + i);
                Thread.sleep(5000);
            }
        }
    }
}

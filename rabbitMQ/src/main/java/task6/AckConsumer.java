package task6;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class AckConsumer {
    private final static String QUEUE_NAME = "task6";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("AckConsumer message received: " + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            System.out.println("AckConsumer message processed: " + message);
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
    }
}

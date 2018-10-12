package com.summer.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.summer.Config;
import com.summer.connect.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveByRouterKey implements Runnable {

    private String exchangeName;
    private String vhost;
    private String queueName;
    private String routerKey;

    public ReceiveByRouterKey(String exchangeName, String vhost, String queueName, String routerKey) {
        this.exchangeName = exchangeName;
        this.vhost = vhost;
        this.queueName = queueName;
        this.routerKey = routerKey;
    }

    public void run() {
        try {
            startReceive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReceive() throws IOException, InterruptedException, TimeoutException {
        //获取到连接以及通道
        Connection connection = ConnectionUtil.getConnection(vhost);
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        //绑定队列到交换器
        channel.queueBind(queueName, exchangeName, routerKey);
        //统一时刻服务器只会发一条消息给消费者;
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，手动返回完成
        channel.basicConsume(queueName, false, consumer);
        //获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody(), Config.charset);
            System.out.println("【Receive】【" + queueName + "】【" + message + "】");
            Thread.sleep(100);
            //手动返回
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }


}

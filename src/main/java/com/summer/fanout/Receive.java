package com.summer.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.summer.connect.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive implements Runnable {

    private String exchangeName;
    private String vhost;
    private String queueName;

    public Receive(String exchangeName, String vhost, String queueName) {
        this.exchangeName = exchangeName;
        this.vhost = vhost;
        this.queueName = queueName;
    }

    public void run() {
        try {
            startReceive();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void startReceive() throws IOException, InterruptedException, TimeoutException {
        //获取到连接以及通道
        Connection connection = ConnectionUtil.getConnection(vhost);
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        //绑定队列到交换器
        channel.queueBind(queueName, exchangeName, "");
        //统一时刻服务器只会发一条消息给消费者;
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，手动返回完成
        channel.basicConsume(queueName, false, consumer);
        //获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("【Receive】【" + queueName+"】【" + message + "】");
            Thread.sleep(100);
            //手动返回
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }


}

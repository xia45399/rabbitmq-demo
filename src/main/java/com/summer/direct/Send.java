package com.summer.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.summer.Config;
import com.summer.connect.ConnectionUtil;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Send implements Runnable {
    private String exchangeName;
    private String vhost;

    public Send(String exchangeName, String vhost) {
        this.exchangeName = exchangeName;
        this.vhost = vhost;
    }

    public void run() {
        try {
            startSend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSend() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection(vhost);
        //获得信道
        Channel channel = connection.createChannel();
        //声明交换器
        channel.exchangeDeclare(exchangeName, "direct", true);

        int i = 1;
        Random random = new Random();
        while (true) {
            String key = random.nextInt(2) == 0 ? "add" : "delete";
            String msg = exchangeName + " direct的" + key + "消息" + i++;
            channel.basicPublish(exchangeName, key, null, msg.getBytes(Config.charset));
            Thread.sleep(1000);
        }
//        channel.close();
//        connection.close();
    }


}

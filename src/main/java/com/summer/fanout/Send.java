package com.summer.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.summer.connect.ConnectionUtil;

import java.io.IOException;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startSend() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection(vhost);
        //获得信道
        Channel channel = connection.createChannel();
        //声明交换器
        channel.exchangeDeclare(exchangeName, "fanout", true);

        int i = 1;
        while (true) {
            String msg = "fanout消息" + i++;
            channel.basicPublish(exchangeName, "", null, msg.getBytes());
            Thread.sleep(1000);
        }
//        channel.close();
//        connection.close();
    }


}

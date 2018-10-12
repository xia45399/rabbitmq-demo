package com.summer.fanout;

/**
 * fanout 广播模式demo
 * 创建一个exchange 并将队列绑定到exchange,发送到exchange的消息会转发到所有与该exchange绑定的队列上
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String exchangeName = "fanout-exchange";
        String vhost = "fanout_vhost";
        Thread sendThread = new Thread(new Send(exchangeName, vhost));
        Thread receiveThread1 = new Thread(new Receive(exchangeName, vhost, "fanout_queue1"));
        Thread receiveThread2 = new Thread(new Receive(exchangeName, vhost, "fanout_queue2"));
        sendThread.start();
        receiveThread1.start();
        receiveThread2.start();
    }
}

package com.summer.connect;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    private static String username = "admin";
    private static String password = "123456";
    private static String ip = "127.0.0.1";

    public static Connection getConnection(String vhost) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setHost(ip);
        factory.setVirtualHost(vhost);
        //建立到代理服务器的连接
        return factory.newConnection();
    }
}

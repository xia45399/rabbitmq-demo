package com.summer.connect;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.summer.Config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection(String vhost) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(Config.username);
        factory.setPassword(Config.password);
        factory.setHost(Config.host);
        factory.setVirtualHost(vhost);
        //建立到代理服务器的连接
        return factory.newConnection();
    }
}

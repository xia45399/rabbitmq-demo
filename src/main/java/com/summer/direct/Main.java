package com.summer.direct;

public class Main {
    public static void main(String[] args) {
     new Thread(new Send("direct-exchange","direct_vhost")).start();
     new Thread(new ReceiveByRouterKey("direct-exchange","direct_vhost","addQueue","add")).start();
     new Thread(new ReceiveByRouterKey("direct-exchange","direct_vhost","delQueue","delete")).start();
    }
}

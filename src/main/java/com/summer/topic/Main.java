package com.summer.topic;

/**
 * routerKey包含  msg1.add msg1.delete msg2.add msg2.delete
 * msg1.add 会被allMsg1 和allAdd收到
 * msg1.delete 会被allMsg1 和allDelete收到
 * msg2.add 会被allMsg2 和allAdd收到
 * msg2.delete 会被allMsg2 和allDelete收到
 */
public class Main {
    public static void main(String[] args) {

        new Thread(new Send("topic-exchange", "topic_vhost")).start();
        new Thread(new ReceiveByRouterKey("topic-exchange", "topic_vhost", "allMsg1", "msg1.#")).start();
        new Thread(new ReceiveByRouterKey("topic-exchange", "topic_vhost", "allMsg2", "msg2.#")).start();
        new Thread(new ReceiveByRouterKey("topic-exchange", "topic_vhost", "allAdd", "#.add")).start();
        new Thread(new ReceiveByRouterKey("topic-exchange", "topic_vhost", "allDelete", "#.delete")).start();
    }
}

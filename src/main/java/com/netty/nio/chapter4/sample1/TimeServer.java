package com.netty.nio.chapter4.sample1;

public class TimeServer {

    public static void main(String[] args) {
        Integer port=10001;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();

    }

}

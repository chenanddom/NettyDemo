package com.netty.nio.chapter4.sample1;

public class TimeClient {


    public static void main(String[] args) {
        //请求的服务端的接口
        Integer port = 10001;
        new Thread(new TimeClientHandler("127.0.0.1",port),"TimeClient-0001").start();
    }
}

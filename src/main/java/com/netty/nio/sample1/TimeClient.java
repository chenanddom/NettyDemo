package com.netty.nio.sample1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient {


    public static void main(String[] args) {
        //请求的服务端的接口
        Integer port = 10001;
        new Thread(new TimeClientHandler("127.0.0.1",port),"TimeClient-0001").start();
    }
}

package com.netty.bio.asynchronous;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args) {
        Integer port=10001;
        ServerSocket serverSocket=null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("the server is start in port:"+port);
            Socket socket = null;
            //创建io任务的线程池
            TimeSeverHandlerExecutorPool timeSeverHandlerExecutorPool = new TimeSeverHandlerExecutorPool(50,10000);
            while (true){
                socket = serverSocket.accept();
                //开启线程去处理请求(在多客户端请求的时候回创建多个线程，而线程又是宝贵的资源)
                //此处去掉阻塞是io的方式
                //new Thread(new TimeServerHandler(socket)).start();
                timeSeverHandlerExecutorPool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    System.out.println("The time server is close");
                    serverSocket.close();
                    serverSocket=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

package com.netty.bio.asynchronous;

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
        try (Socket socket = new Socket("127.0.0.1", port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String result = in.readLine();
            System.out.println("Now is :" + result);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

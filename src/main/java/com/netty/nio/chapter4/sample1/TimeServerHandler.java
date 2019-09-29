package com.netty.nio.chapter4.sample1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler implements Runnable {
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true)){

            String currentTime=null;
            String body = null;
            while (true){
                body = in.readLine();
                if (org.apache.commons.lang3.StringUtils.isEmpty(body)){
                    break;
                    }
                System.out.println("The time server receive order:"+body);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?simpleDateFormat.format(new Date(System.currentTimeMillis())):"BAD ORDER";
                out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.socket=null;
            }
        }
    }
}

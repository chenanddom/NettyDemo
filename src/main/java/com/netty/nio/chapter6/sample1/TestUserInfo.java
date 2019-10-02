package com.netty.nio.chapter6.sample1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUserInfo {
    public static void main(String[] args) throws IOException {
        //使用传统的JDK序列化技术进行序列化
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("zhangsan");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] bytes = bos.toByteArray();
        System.out.println("The jdk serializable length is :" + bytes.length);
        bos.close();
        System.out.println("---------------------------------------------");
        System.out.println("The byte array serializable length is:" + info.codeC().length);


    }
}

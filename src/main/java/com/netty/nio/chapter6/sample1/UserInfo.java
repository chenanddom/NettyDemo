package com.netty.nio.chapter6.sample1;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 8240444118011889392L;

    private String userName;

    private int userID;


public UserInfo buildUserName(String userName){
    this.userName = userName;
    return this;
}

public UserInfo buildUserID(int userID){
    this.userID = userID;
    return this;
}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final int getUserID() {
        return userID;
    }

    public final void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * 使用基于ByteBuffer的通用二进制编解码技术对UserInfo对象实例进行编码
     * @return
     */
    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value=null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}

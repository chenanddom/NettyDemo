package com.netty.nio.chapter5.sample5;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class SubReqClientHandler extends ChannelHandlerAdapter {
    public SubReqClientHandler() {
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 1000; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }
    private SubscibeReqProto.SubscibeReq subReq(int i) {
        SubscibeReqProto.SubscibeReq.Builder builder = SubscibeReqProto.SubscibeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("Chendom");
        builder.setProductName("Netty Book For Protobuf");
        List<String> address = new ArrayList<String>();
        address.add("NanJing YuHuaTai");
        address.add("BeiJing LiuLiChang");
        builder.addAllAddress(address);
        return builder.build();
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        ctx.close();
    }
}

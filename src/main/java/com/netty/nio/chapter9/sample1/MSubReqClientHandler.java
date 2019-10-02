package com.netty.nio.chapter9.sample1;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MSubReqClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //在链路激活的时候循环构造10条订购请求消息，最后一次性地发送给服务端。
        for (int i = 0; i < 1000; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("南京市江宁区方山国家地质公园");
        req.setPhoneNumber("138xxxxxxxxx");
        req.setProductName("Netty For Marshalling");
        req.setSubReqID(i);
        req.setUserName("Lilinfeng");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //由于对象解码器已经对订购请求应答消息进行了自动解码，
        //因此，SubReqClientHandler接收到的消息已经是解码成功后的订购应答消息。
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

package com.netty.nio.chapter4.sample3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class TimeClientHandler extends ChannelHandlerAdapter {

    private int counter;
    private byte[] req;

    public TimeClientHandler() {
    req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
          ByteBuf firestMessage = null;
          for (int i=0;i<100;i++) {
              firestMessage = Unpooled.buffer(req.length);
              firestMessage.writeBytes(req);
              ctx.writeAndFlush(firestMessage);
          }
//            firestMessage.release();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8");
        String body = (String) msg;
        System.out.println("Now is :"+body+"; the counter is:"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Unexpected exception from downstream :"+cause.getMessage());
        ctx.close();
    }
}

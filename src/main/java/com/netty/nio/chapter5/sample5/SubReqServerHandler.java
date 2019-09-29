package com.netty.nio.chapter5.sample5;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        SubscibeReqProto.SubscibeReq req = (SubscibeReqProto.SubscibeReq)msg;
        if("Chendom".equalsIgnoreCase(req.getUserName())){
            System.out.println("Server accept client subsrcibe req :["+req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }

    }

    private SubscibeRespProto.SubscribeResp resp(int subReqID){
        SubscibeRespProto.SubscribeResp.Builder builder = SubscibeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book succeed, 3 days later , sent to the designated address ");
        return builder.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }



}
package com.netty.nio.chapter12;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {
            "只要功夫深，铁杵磨成针。",
            "旧时王谢堂前燕，飞入寻常百姓加。",
            "洛阳亲友如相问，一片冰心在玉壶。",
            "一寸光阴一寸金，寸金难买寸光阴",
            "老骥伏枥，旨在潜力。劣势暮年，壮心不已。"
    };

    private String nextQuote() {
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }

    //对UDP进行封装，netty封装成DatagramPacket对象
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        //将DatagramPacket回想转换位字符串，然后对请求消息进行合法性判断
        String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        //符合条件的进行回复
        if ("谚语字典查询?".equals(req)){
            channelHandlerContext.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果:"+nextQuote(),
                            CharsetUtil.UTF_8),datagramPacket.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}

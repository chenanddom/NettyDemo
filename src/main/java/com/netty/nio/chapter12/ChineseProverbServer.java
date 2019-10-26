package com.netty.nio.chapter12;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ChineseProverbServer {

public void run(int port) throws InterruptedException {

    EventLoopGroup group = new NioEventLoopGroup();
    try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChineseProverbServerHandler());
        bootstrap.bind(port).channel().closeFuture().await();


    }finally {
        group.shutdownGracefully();
    }
}

    public static void main(String[] args) throws InterruptedException {
        new ChineseProverbServer().run(10000);
    }

}

package com.netty;

import com.netty.handler.DisCardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description:一句话的功能说明
 * @Author: chendom
 * @Date: 2019/9/9 13:36
 * @Version 1.0
 */
public class DiscardServer {

    private Integer port;

    public DiscardServer(Integer port) {
        this.port = port;
    }

    public void run()throws Exception{
        //boss事件循环器，主要付姐接收进来的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //work事件循环器，主要负责处理接收到的请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //NIO服务启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                //一个新的Channel如何接收进来的连接
                .channel(NioServerSocketChannel.class)
                //这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。ChannelInitializer是一个特殊的处理类，
                // 他的目的是帮助使用者配置一个新的Channel。也许你想通过增加一些处理类比如DiscardServerHandle来配置
                // 一个新的Channel或者其对应的ChannelPipeline来实现你的网络程序。当你的程序变的复杂时，可能你会增加
                // 更多的处理类到pipline上，然后提取这些匿名类到最顶层的类上。
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline().addLast(new DisCardServerHandler());
                    }
                }).option(ChannelOption.SO_BACKLOG,128)
                //你关注过option()和childOption()吗？option()是提供给NioServerSocketChannel用来接收进来的连接。
                // childOption()是提供给由父管道ServerChannel接收到的连接，在这个例子中也是NioServerSocketChannel。
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture future = bootstrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    public static void main(String[] args) {
        try {
            new DiscardServer(8080).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

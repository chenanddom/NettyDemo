package com.netty.nio.chapter4.sample3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class TimeServer {

public void bind(int port) throws InterruptedException {
    //配置服务端的NIO线程组
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {
        //用于NIO服务端的服务辅助启动类
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup)
                //这个NioSctpServerChannel就类似于ServerSocketChannel
                .channel(NioServerSocketChannel.class)
                /**
                 * 服务器端TCP内核模块维护有2个队列，我们称之为A，B吧
                  客户端向服务端connect的时候，发送带有SYN标志的包（第一次握手）
                   服务端收到客户端发来的SYN时，向客户端发送SYN ACK 确认(第二次握手)
                   此时TCP内核模块把客户端连接加入到A队列中，然后服务器收到客户端发来的ACK时（第三次握手）
                   TCP没和模块把客户端连接从A队列移到B队列，连接完成，应用程序的accept会返回
                   也就是说accept从B队列中取出完成三次握手的连接
                   A队列和B队列的长度之和是backlog,当A，B队列的长之和大于backlog时，新连接将会被TCP内核拒绝
                  所以，如果backlog过小，可能会出现accept速度跟不上，A.B 队列满了，导致新客户端无法连接， 
                   要注意的是，backlog对程序支持的连接数并无影响，backlog影响的只是还没有被accept 取出的连接
                 */
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());
        //绑定端口，同步等待成功
        ChannelFuture f = b.bind(port).sync();
        //等待服务端监听端口关闭
        f.channel().closeFuture().sync();
    }finally {
        //优雅的退出，释放线程池的资源
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}

private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(new TimeServerHandler());
    }
}
    public static void main(String[] args) throws InterruptedException {
        int port=10000;
        new TimeServer().bind(port);

    }

}

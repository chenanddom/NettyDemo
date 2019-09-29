package com.netty.nio.chapter5.sample5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient {

    public void connect(int port , String host) throws Exception{

        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            //处理半包问题(这个一定要放在最前面，否则就会出错)
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //添加ProtobufDecoder解码器
                            ch.pipeline().addLast(new ProtobufDecoder(SubscibeRespProto.SubscribeResp.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new SubReqClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();

            //等待客户端链路关闭
            f.channel().closeFuture().sync();

        }finally{
            //释放NIO线程
            group.shutdownGracefully();
        }

    }
    public static void main(String[] args) throws Exception{

        int port = 8080;
        if(args !=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch(Exception e){

            }
        }
        new SubReqClient().connect(port, "127.0.0.1");
    }
}
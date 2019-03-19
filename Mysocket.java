package org;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Mysocket {
    public static void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            System.out.println("kaishi");
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpResponseDecoder());//序列话
                            ch.pipeline().addLast(new HttpObjectAggregator(65535));
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码

                            ch.pipeline().addLast(new TimeClientHandler());
//反序列化

                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();

            // 当代客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Mysocket.connect(80,"www.tju.edu.cn");
        System.out.println("wan");
    }
}

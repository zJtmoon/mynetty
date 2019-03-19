package org;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.Executors;

public class HttpServer {

    //private static Logger log = Logger.getLogger(HttpServer.class);

    public void startinbound(int port) throws Exception {
//        # RHEL/CentOS/Fedora:
//        sudo yum install autoconf automake libtool make tar \
//        glibc-devel libaio-devel \
//        libgcc.i686 glibc-devel.i686
//        # Debian/Ubuntu:
//        sudo apt-get install autoconf automake libtool make tar \
//        gcc-multilib libaio-dev
        EventLoopGroup bossGroup = new EpollEventLoopGroup(); //mainReactor    1个线程
        EventLoopGroup workerGroup = new EpollEventLoopGroup();   //subReactor       线程数量等价于cpu个数+1

//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            b.group(bossGroup, workerGroup);
                    b.channel(EpollServerSocketChannel.class);
                    b.childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            ch.pipeline().addLast(new HttpResponseEncoder());//序列话
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            ch.pipeline().addLast(new HttpRequestDecoder());//反序列化
                            ch.pipeline().addLast(new HttpObjectAggregator(65535));//合包
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpServerInboundHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();//启动服务

//            f.channel().closeFuture().sync();//关闭
    }

    public static void main(String[] args) throws Exception {
//        Thread inbound=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpServer serverIn = new HttpServer();
//                try {
//                    serverIn.startinbound(30000);
//                } catch (Exception e) {
//                 //   log.error("Inbound Server crash!!!",e);
//                    System.exit(1);
//                }
//            }
//        });
      //  log.debug("aaaa");
        new HttpServer().startinbound(30000);
    }
}

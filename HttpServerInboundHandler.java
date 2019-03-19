package org;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
//@ChannelHandler.Sharable
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    //    private static Logger log = Logger.getLogger(HttpServerInboundHandler.class);
    private HttpRequest request;
    private String uri = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)//服务器数据读取    msg为传来的序列化对象
            throws Exception {
        System.out.println(msg);

        try {
            FullHttpRequest fhr = (FullHttpRequest) msg;//反序列化
            //fhr.headers();头的信息

            ByteBuf buf = fhr.content();
            String message = buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();
            String tt = fhr.uri();
            System.out.println(tt + "bnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(tt.getBytes("UTF-8")));
//            FullHttpResponse response=new DefaultFullHttpResponse(HTTP_1_1,OK);
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().setInt(CONTENT_LENGTH,
            response.content().readableBytes());
//
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            File file = new File("/home/zjt/aaa.txt");
            InputStream fis = new FileInputStream(file);
//		InputStream fis1 = new FileInputStream("/opt/test/nohup.out");
            byte[] bbuf = new byte[(int) file.length()];
            fis.read(bbuf);
            String s = new String(bbuf, "utf-8");



            FullHttpResponse response1 = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(s.getBytes("UTF-8")));
            //    FullHttpResponse response=new DefaultFullHttpResponse(HTTP_1_1,OK);
            response1.headers().set(CONTENT_TYPE, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            response1.headers().setInt(CONTENT_LENGTH,
                    response1.content().readableBytes());
//        int hasRead = 0;
//        StringBuffer stringBuffer=new StringBuffer();
//        while ((hasRead = fis.read(bbuf)) > 0 )
//        {
//           String S=new String(bbuf , 0 , hasRead,"utf-8" );
//           stringBuffer.append(S);
//
//        }
//        fis.close();

            //ctx.writeAndFlush(s);
            ctx.write(response1);
            //ctx.write(response);
            ctx.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String queueName ="testQueue";
//
//        if (msg instanceof HttpRequest) {
//            request = (HttpRequest) msg;
//            uri = request.uri();
//            queueName = uri.substring(1, uri.length());
//            log.debug("Queue Name is: " + queueName);
//        }
//        if (msg instanceof HttpContent) {
//            long start=System.nanoTime();
//            try {
//                HttpContent content = (HttpContent) msg;
//                ByteBuf buf = content.content();
//                String message = buf.toString(io.netty.util.CharsetUtil.UTF_8);
//                buf.release();
//                log.debug("message is: " + message);
//
//            } catch (Exception e) {
//                log.error("system error:", e);
//            }
//            String result = "OK";
//            log.debug("result is: " + result);
//            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
//                    OK, Unpooled.wrappedBuffer(result.getBytes("UTF-8")));
//            response.headers().set(CONTENT_TYPE, "text/plain");
//            response.headers().setInt(CONTENT_LENGTH,
//                    response.content().readableBytes());
//            if (HttpHeaderUtil.isKeepAlive(request)) {
//                response.headers().set(CONNECTION, KEEP_ALIVE);
//            }
//            ctx.write(response);
//            ctx.flush();
//            System.out.println(System.nanoTime()-start);
//        }
        //   log.debug("--------------------------------------");
    }

//    @Override
//    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//
//      //  FileOutputStream fileOutputStream=new FileOutputStream("/home/zjt/aaa.txt");
//
//        File file = new File("/home/zjt/aaa.txt");
//        InputStream fis = new FileInputStream(file);
////		InputStream fis1 = new FileInputStream("/opt/test/nohup.out");
//        byte[] bbuf = new byte[(int)file.length()];
//        fis.read(bbuf);
//        String s = new String(bbuf,"utf-8");
//
////        int hasRead = 0;
////        StringBuffer stringBuffer=new StringBuffer();
////        while ((hasRead = fis.read(bbuf)) > 0 )
////        {
////           String S=new String(bbuf , 0 , hasRead,"utf-8" );
////           stringBuffer.append(S);
////
////        }
////        fis.close();
//
//        ctx.writeAndFlush(s);
//
//    }
////
////
////    @Override
////    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {//读取完成调取
////        ctx.flush();//也可以在上一方法
////    }
////
////    @Override
////    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {//异常补货
////      //  log.error(cause.getMessage());
////        ctx.close();
//    }

}
package org;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

//	private static final Logger logger = Logger
//			.getLogger(TimeClientHandler.class.getName());

	/**
	 * Creates a client-side handler.
	 */
	public TimeClientHandler() {

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {


		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

		String host = "http://www.tju.edu.cn:80";
		URI uri = new URI(host);
		FullHttpRequest request=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"http://www.tju.edu.cn:80");
		request.headers().set(HttpHeaders.Names.ACCEPT ," text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING,"gzip, deflate");
		request.headers().set(HttpHeaders.Names.ACCEPT_LANGUAGE,"en-GB,en;q=0.5");
		request.headers().set(HttpHeaders.Names.CONNECTION,"keep-alive");
//		request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,"0");
		request.headers().set(HttpHeaders.Names.HOST,host);
		request.headers().set(HttpHeaders.Names.USER_AGENT," Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0]");

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	    ctx.writeAndFlush(request);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println(msg);
FullHttpResponse fullHttpResponse= (FullHttpResponse) msg;
		System.out.println(fullHttpResponse);
		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");


		HttpContent response= (HttpContent) msg;
		ByteBuf s =response.content();
		byte[] bytes = new byte[s.readableBytes()];
		String st = new String (bytes,"UTF-8");
		System.out.println("========================================");

		System.out.println(st);
		System.out.println("========================================");
		FileOutputStream outputStream=new FileOutputStream("/home/zjt/aaaaa.txt");
		outputStream.write(st.getBytes());
		outputStream.flush();





		System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		//FullHttpResponse fhr = (FullHttpResponse) msg;//反序列化
//		System.out.println(((FullHttpResponse) msg).content());
//		ByteBuf s =msg.content();
//		byte[] bytes = new byte[s.readableBytes()];
//		String st = new String (bytes,"UTF-8");
//		System.out.println("========================================");
//
//		System.out.println(st);
//		System.out.println("========================================");
//		FileOutputStream outputStream=new FileOutputStream("/home/zjt/aaa.html");
//		outputStream.write(st.getBytes());
//		outputStream.flush();
//		String mess = s.toString();
//		System.out.println(mess);
//		System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttt");
//		ByteBuf buf = (ByteBuf)msg;
//		System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttt");
//		byte[] req = new byte[buf.readableBytes()];
//		System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
//		buf.readBytes(req);
//		System.out.println("llllllllllllllllllllllllllllllllllllllllllll");
//	//	String body = new String(req,"UTF-8");
//		String message = s.toString(CharsetUtil.UTF_8);
//		File f = new File("/home/zjt/aa1a.txt");
//		if(!f.exists()){
//			f.createNewFile();
//		}
//		FileOutputStream outputStream1=new FileOutputStream("/home/zjt/aa1a.txt");
//		outputStream1.write(req);
//		outputStream1.flush();

	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 释放资源
//		logger.warning("Unexpected exception from downstream : "
//				+ cause.getMessage());
		ctx.close();
	}
}

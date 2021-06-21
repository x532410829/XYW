package com.Panacea.demo;

import javax.annotation.PostConstruct;

import com.Panacea.unity.nettyHandler.LoginHandler;
import com.Panacea.unity.nettyHandler.ServerMsgHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LoggingHandler;

public class NettyDemo {
	
	
	  private final EventLoopGroup loopGroup = new NioEventLoopGroup();
	  private ChannelPipeline pipeline;
	  private ServerBootstrap bootstrap;
	
	
	  @PostConstruct
		public void init() {
		  startServer();
	  }
	  
	  private void startServer() {
		    bootstrap = new ServerBootstrap();
		    bootstrap.group(loopGroup);
		    bootstrap.channel(NioServerSocketChannel.class);
		    bootstrap.childHandler(channelHandler);
		    bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		    // 监听端口8090
		    bootstrap.bind(8090);
		  }
	  
	  private final ChannelInitializer<SocketChannel> channelHandler = new ChannelInitializer<SocketChannel>() {
		    @Override
		    protected void initChannel(SocketChannel ch) throws Exception {
		      pipeline = ch.pipeline();
		      pipeline.addLast(new LoggingHandler());
		      addOutBoundHandler();
		      pipeline.addLast(new MessageToByteEncoder<byte[]>() {

		        @Override
		        protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
		          out.writeBytes(msg);
		        }
		      });
		      addInBoundHandler();
		      addLocalHandler();
		    }
		  };

		  private void addOutBoundHandler() {
		    pipeline.addLast(new ChannelOutboundHandlerAdapter() {
		      @Override
		      public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		        super.write(ctx, msg, promise);
		      }

		      @Override
		      public void flush(ChannelHandlerContext ctx) throws Exception {
		        super.flush(ctx);
		      }

		      @Override
		      public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		        System.out.println("连接断开");
		        super.disconnect(ctx, promise);
		      }

		      @Override
		      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		        System.out.println(String.format("[异常：]%s", cause.getCause().getMessage()));
		        super.exceptionCaught(ctx, cause);
		      }

		    });
		  }

		  private void addInBoundHandler() {
		    pipeline.addLast(new ChannelInboundHandlerAdapter() {
		      @Override
		      public void channelActive(ChannelHandlerContext ctx) throws Exception {
		        // 通道激活时触发
		        System.out.println("通道激活");
		        // channelGroup.add(ctx.channel());
		        // channel = ctx.channel();
//		        sendLoginMessage(ctx.channel());
		        super.channelActive(ctx);
		      }

		      @Override
		      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		        System.out.println("socket连接已断开");
		        super.channelInactive(ctx);
		      }

		      @Override
		      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		        // 异常处理
		        System.out.println(String.format("[异常：]%s", cause.getMessage()));
		        super.exceptionCaught(ctx, cause);
		      }
		    });
		  }

//		  private void sendLoginMessage(Channel channel) {
//			    channel.writeAndFlush("发起登录验证");
//			  }
	  

		  /**
		   * 添加内部handler
		   */
		  private void addLocalHandler() {
			    pipeline.addLast(new LoginHandler());
			    pipeline.addLast(new ServerMsgHandler());
		    
		  }
	  

}

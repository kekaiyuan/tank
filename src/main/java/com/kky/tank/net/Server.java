package com.kky.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * I/O Request
 * via {@link Channel} or
 * {@link ChannelHandlerContext}
 * |
 * +---------------------------------------------------+---------------+
 * |                           ChannelPipeline         |               |
 * |                                                  \|/              |
 * |    +---------------------+            +-----------+----------+    |
 * |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * |               |                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  .               |
 * |               .                                   .               |
 * | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
 * |        [ method call]                       [method call]         |
 * |               .                                   .               |
 * |               .                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * |               |                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * +---------------+-----------------------------------+---------------+
 * |                                  \|/
 * +---------------+-----------------------------------+---------------+
 * |               |                                   |               |
 * |       [ Socket.read() ]                    [ Socket.write() ]     |
 * |                                                                   |
 * |  Netty Internal I/O Threads (Transport Implementation)            |
 * +-------------------------------------------------------------------+
 */
public class Server {

    //开启一个客户端通道组，用一个默认的线程处理事件，实现转发
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //只负责客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //负责每个Socket上的事件的处理
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TankMsgEncoder())
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(5000)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server started");

            //会永远等待close()方法的调用,实现长时间启动
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter {

    //把通道加入通道组
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Server.clients.writeAndFlush(msg);
        System.out.println(msg);
    }


    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        cause.printStackTrace();
        //关闭资源
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}


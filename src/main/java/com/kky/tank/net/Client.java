package com.kky.tank.net;

import com.kky.tank.GameModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static final Client INSTANCE = new Client();

    private Channel channel = null;

    private Client() {
    }

    public void connect() {
        /*
         * 事件处理的线程池，无论是连接还是读写都由线程池中的线程处理
         * 参数为线程池的大小，无参默认为CPU的核心数*2
         * 客户端一般一个线程就够了
         */
        EventLoopGroup group = new NioEventLoopGroup(1);

        //复制启动类
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置线程池
            ChannelFuture channelFuture = bootstrap.group(group)
                    //设置连接类型
                    .channel(NioSocketChannel.class)
                    //channel上有事件时传给谁处理
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TankMsgEncoder())
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 5000);

            //设置监听器，监听连接的返回
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("connected");
                        channel = future.channel();
                    } else {
                        System.out.println("not connected");

                    }
                }
            });

            //会永远等待close()方法的调用,实现长时间启动
            try {
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
//        if(msg instanceof TankMoveMsg){
//            System.out.println("111");
//        }
        channel.writeAndFlush(msg);
    }



//    public static void main(String[] args) {
//        Client client = new Client();
//        client.connect();
//    }

    public void closeConnect() {
        //send("_bye_");

    }
}

class ClientHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channel第一次连上可用，写出一个字符串
        //ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
        //ctx.writeAndFlush(buf);
        ctx.writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getMyTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
        //ByteBuf buf = null;


//            buf = (ByteBuf) msg;
//            byte[] bytes = new byte[buf.readableBytes()];
//            buf.getBytes(buf.readerIndex(), bytes);
        msg.handle();


    }
}






package com.kky.tank.net;

import com.kky.tank.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //两个Int加起来是8字节，没读全之前不处理
        if (in.readableBytes() < 8) {
            return;
        }

        in.markReaderIndex();

        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();
        if(in.readableBytes()<length){
            in.resetReaderIndex();
            return;
        }
        //in.markReaderIndex();

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        switch (msgType){
            case TankJoin:
                TankJoinMsg tankJoinMsg = new TankJoinMsg();
                tankJoinMsg.parse(bytes);
                out.add(tankJoinMsg);
                break;
            case TankDirChanged:
                TankDirChangedMsg tankDirChangedMsg = new TankDirChangedMsg();
                tankDirChangedMsg.parse(bytes);
                out.add(tankDirChangedMsg);
                break;
            case TankStop:
                TankStopMsg tankStopMsg = new TankStopMsg();
                tankStopMsg.parse(bytes);
                out.add(tankStopMsg);
                break;
            case BulletNew:
                BulletNewMsg bulletNewMsg = new BulletNewMsg();
                bulletNewMsg.parse(bytes);
                out.add(bulletNewMsg);
                break;
            case TankDie:
                TankDieMsg tankDieMsg = new TankDieMsg();
                tankDieMsg.parse(bytes);
                out.add(tankDieMsg);
                break;
        }

    }
}

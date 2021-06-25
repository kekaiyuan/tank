package com.kky.tank.net;

import com.kky.tank.Dir;
import com.kky.tank.GameModel;
import com.kky.tank.MsgType;

import java.io.*;
import java.util.UUID;

public class TankDirChangedMsg extends Msg {

    public Dir dir;
    public UUID id;

    public TankDirChangedMsg(Dir dir, UUID id) {
        this.dir = dir;
        this.id = id;
    }

    public TankDirChangedMsg() {

    }

    @Override
    public void handle() {
        GameModel.INSTANCE.tankDirChanged(this.dir,this.id);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(dir.ordinal());
            dataOutputStream.writeLong(id.getMostSignificantBits());
            dataOutputStream.writeLong(id.getLeastSignificantBits());

            dataOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        }
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dataInputStream = null;
        try{
            dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            this.dir = Dir.values()[dataInputStream.readInt()];
            this.id = new UUID(dataInputStream.readLong(),dataInputStream.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDirChanged;
    }

    @Override
    public String toString() {
        return "TankDirChangedMsg{" +
                "dir=" + dir +
                ", id=" + id +
                '}';
    }
}

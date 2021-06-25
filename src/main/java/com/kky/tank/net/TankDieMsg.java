package com.kky.tank.net;

import com.kky.tank.Dir;
import com.kky.tank.GameModel;
import com.kky.tank.MsgType;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg {

    public int impactX;
    public int impactY;
    public UUID id;

    public TankDieMsg(int impactX, int impactY, UUID id) {
        this.impactX = impactX;
        this.impactY = impactY;
        this.id = id;
    }

    public TankDieMsg() {

    }

    @Override
    public void handle() {
        GameModel.INSTANCE.tankDie(impactX, impactY, id);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(impactX);
            dataOutputStream.writeInt(impactY);
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
        try {
            dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            this.impactX = dataInputStream.readInt();
            this.impactY = dataInputStream.readInt();
            this.id = new UUID(dataInputStream.readLong(), dataInputStream.readLong());
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
        return MsgType.TankDie;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "impactX=" + impactX +
                ", impactY=" + impactY +
                ", id=" + id +
                '}';
    }
}

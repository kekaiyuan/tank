package com.kky.tank.net;

import com.kky.tank.GameModel;
import com.kky.tank.MsgType;
import com.kky.tank.tank.Tank;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{
    public int x, y;
    public UUID id = null;

    public TankJoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.id = tank.getId();
    }

    public TankJoinMsg(int x, int y, UUID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public TankJoinMsg() {

    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeInt(x);
            dataOutputStream.writeInt(y);
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
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();
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
        return MsgType.TankJoin;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if(!GameModel.INSTANCE.add(this)){
            return;
        }

        // GameModel.INSTANCE.add(new PlayerTank(tankJoinMsg.x, tankJoinMsg.y));
        System.out.println(this.toString());
        //System.out.println(new String(bytes));
        Client.INSTANCE.send(new TankJoinMsg(GameModel.INSTANCE.getMyTank()));
    }
}

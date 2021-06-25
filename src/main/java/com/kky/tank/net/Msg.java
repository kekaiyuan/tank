package com.kky.tank.net;

import com.kky.tank.MsgType;

public abstract class Msg {
    public abstract void handle();
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract MsgType getMsgType();
}

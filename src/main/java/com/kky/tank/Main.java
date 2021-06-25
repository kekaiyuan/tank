package com.kky.tank;

import com.kky.tank.frame.TankFrame;
import com.kky.tank.net.Client;
import com.kky.tank.tank.RobotTank;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * @author 柯凯元
 * @create 2021/6/11 16:29
 */
public class Main {

    public static void main(String[] args) {
        //显示窗口
        TankFrame tankFrame = TankFrame.INSTANCE;
        tankFrame.setVisible(true);

        //背景音乐
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isBackVoice").toString());

        if(isVoice){
            new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        }

        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tankFrame.repaint();
            }
        }).start();

        //客户端连接服务器
        Client.INSTANCE.connect();

    }

}

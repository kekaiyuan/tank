package com.kky.tank;

import com.kky.tank.frame.TankFrame;
import com.kky.tank.tank.RobotTank;

import java.util.Properties;

/**
 * @author 柯凯元
 * @create 2021/6/11 16:29
 */
public class Main {

    public static void main(String[] args) {


        TankFrame tf = new TankFrame();

        //敌人初始化
        for (int i = 0; i < Integer.parseInt(PropertyMgr.get("initTankCount").toString()); i++) {
            tf.tanks.add(new RobotTank(50 + i * 140, 200, tf));
        }




        //背景音乐
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isBackVoice").toString());
        if(isVoice){
            new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        }


        while (true) {
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

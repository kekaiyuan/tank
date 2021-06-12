package com.kky.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 柯凯元
 * @create 2021/6/11 16:29
 */
public class Main {

    public static void main(String[] args) {
        TankFrame tf = new TankFrame();

        //敌人初始化
        for (int i = 0; i < 5; i++) {
            tf.enemyTanks.add(new Tank(50 + i * 140, 100, Dir.DOWN, Team.TEAM_B,tf));
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

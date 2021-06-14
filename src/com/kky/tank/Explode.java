package com.kky.tank;

import com.kky.tank.frame.TankFrame;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Explode {

    //public static int WIDTH = ResourceMgr.explosions[0].getWidth();
    //public static int HEIGHT = ResourceMgr.explosions[0].getHeight();

    private int x, y;
    TankFrame tankFrame = null;

    //表示爆炸的步骤
    private int step = 0;

    //x、y为爆炸的中心点
    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isExplodeVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
        }

    }

    public void paint(Graphics g) {
        //System.out.println(step);
        //int explosionX = x+
        int explodeX = x - ResourceMgr.explosions[step].getWidth() / 2;
        int explodeY = y - ResourceMgr.explosions[step].getHeight() / 2;
        g.drawImage(ResourceMgr.explosions[step++], explodeX, explodeY, null);
        if (step >= ResourceMgr.explosions.length) {
            //step = 0;
            tankFrame.explodes.remove(this);
        }
    }

}

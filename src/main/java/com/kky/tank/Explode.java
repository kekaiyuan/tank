package com.kky.tank;

import com.kky.tank.frame.TankFrame;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Explode extends GameObject{

    //爆炸的中心点
    private int x, y;

    //表示爆炸的步骤
    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

        //爆炸音效
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isExplodeVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
        }
    }

    public void paint(Graphics g) {
        int explodeX = x - ResourceMgr.explosions[step].getWidth() / 2;
        int explodeY = y - ResourceMgr.explosions[step].getHeight() / 2;
        g.drawImage(ResourceMgr.explosions[step++], explodeX, explodeY, null);
        if (step >= ResourceMgr.explosions.length) {
            GameModel.INSTANCE.remove(this);
        }
    }

}

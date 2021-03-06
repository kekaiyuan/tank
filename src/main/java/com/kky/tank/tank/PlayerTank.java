package com.kky.tank.tank;

import com.kky.tank.*;
import com.kky.tank.fire.FireMode;
import com.kky.tank.fire.FireStrategy;
import com.kky.tank.fire.FourDirFireStrategy;
import com.kky.tank.fire.SingleFireStrategy;
import com.kky.tank.frame.TankFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 柯凯元
 * @create 2021/6/13 20:10
 */

/**
 * 玩家坦克
 */
public class PlayerTank extends Tank {

    //开火模式，可切换
    private int fireMode = 0;

    public PlayerTank(int x, int y) {
        super(x, y);
        dir = Dir.UP;
        team = Team.PLAYER;
        isMoving = false;
        tankImage = ResourceMgr.playerTank;
    }

    public int getFireMode() {
        return fireMode;
    }

    public void changeFireMode() {
        fireMode = (fireMode + 1) % FireMode.values().length;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void move() {
        //移动音效
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isTankMoveVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }

        //是否能够移动
        if (isMoving) {
            super.move();
        }
    }

    public void fire() {
        //开火音效
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isTankFireVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }

        //开火策略
        switch (fireMode) {
            case 0:
                super.fire(SingleFireStrategy.getSingleFireStrategy());
                break;
            case 1:
                super.fire(FourDirFireStrategy.getFourFireStrategy());
                break;
        }

    }

}

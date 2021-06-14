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
public class PlayerTank extends Tank {

    private int fireMode = 0;
    //private boolean isMoving = false;

    public PlayerTank(int x, int y, TankFrame tankFrame) {
        super(x, y, tankFrame);
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

    protected void move() {
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isTankMoveVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }

        if (isMoving) {
            super.move();
        }

    }

    public void fire() {
        Boolean isVoice = Boolean.parseBoolean(PropertyMgr.get("isVoice").toString());
        isVoice = isVoice && Boolean.parseBoolean(PropertyMgr.get("isTankFireVoice").toString());
        if (isVoice) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }

        switch (fireMode) {
            case 0:
                super.fire(SingleFireStrategy.getSingleFireStrategy());
                break;
            case 1:
                super.fire(FourDirFireStrategy.getFourFireStrategy());
                break;
        }

//        String className = "com.kky.tank.fire."+FireMode.values()[fireMode].toString();
//        String methodName = "get"+FireMode.values()[fireMode].toString();
//
//        try {
//            Class c = Class.forName(className);
//            FireStrategy fireStrategy = (FireStrategy) c.newInstance();
//            Method method = fireStrategy.getClass().getDeclaredMethod(methodName);
//            super.fire((FireStrategy) method.invoke(fireStrategy));
//
//            //super.fire((FireStrategy) Class.forName(className).newInstance());
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }


    }


}

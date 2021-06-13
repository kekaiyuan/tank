package com.kky.tank.tank;

import com.kky.tank.Dir;
import com.kky.tank.ResourceMgr;
import com.kky.tank.frame.TankFrame;
import com.kky.tank.Team;

/**
 * @author 柯凯元
 * @create 2021/6/13 20:10
 */
public class PlayerTank extends Tank{

    public PlayerTank(int x, int y, TankFrame tankFrame) {
        super(x, y, tankFrame);
        dir = Dir.UP;
        team = Team.PLAYER;
        isMoving = false;
        tankImage = ResourceMgr.playerTank;

    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}

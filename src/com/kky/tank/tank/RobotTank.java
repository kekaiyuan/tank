package com.kky.tank.tank;

import com.kky.tank.Dir;
import com.kky.tank.ResourceMgr;
import com.kky.tank.frame.TankFrame;
import com.kky.tank.Team;

import java.awt.*;
import java.util.Random;

/**
 * @author 柯凯元
 * @create 2021/6/13 20:10
 */
public class RobotTank extends Tank {

    Random random = new Random();

    public RobotTank(int x, int y, TankFrame tankFrame) {
        super(x, y, tankFrame);
        dir = Dir.DOWN;
        team = Team.ROBOT;
        isMoving = true;
        tankImage = ResourceMgr.robotTank;

    }

    public void paint(Graphics g){

        if (random.nextInt(100) > 90) {
            super.dir = Dir.values()[random.nextInt(4)];
            //this.dir = Dir.LEFT;
        }

        if (random.nextInt(100) > 95) {
            fire();
        }
        super.paint(g);
    }

}

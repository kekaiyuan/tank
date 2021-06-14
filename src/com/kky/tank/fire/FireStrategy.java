package com.kky.tank.fire;

import com.kky.tank.Dir;
import com.kky.tank.Team;
import com.kky.tank.frame.TankFrame;

/**
 * @author 柯凯元
 * @create 2021/6/13 22:31
 */
public abstract class FireStrategy {

    public abstract void fire(int bulletX, int bulletY, Dir dir, Team team, TankFrame tankFrame);

}

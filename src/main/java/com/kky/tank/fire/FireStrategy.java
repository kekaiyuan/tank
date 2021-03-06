package com.kky.tank.fire;

import com.kky.tank.Dir;
import com.kky.tank.GameModel;
import com.kky.tank.Team;
import com.kky.tank.frame.TankFrame;

/**
 * @author 柯凯元
 * @create 2021/6/13 22:31
 */

/**
 * 开火策略
 */
public abstract class FireStrategy {

    public abstract void fire(int bulletX, int bulletY, Dir dir, Team team);

}

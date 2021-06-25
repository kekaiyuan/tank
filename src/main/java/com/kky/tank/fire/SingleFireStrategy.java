package com.kky.tank.fire;

import com.kky.tank.Bullet;
import com.kky.tank.Dir;
import com.kky.tank.GameModel;
import com.kky.tank.Team;
import com.kky.tank.frame.TankFrame;
import com.kky.tank.net.BulletNewMsg;
import com.kky.tank.net.Client;

/**
 * @author 柯凯元
 * @create 2021/6/13 22:32
 */

/**
 * 单发
 */
public class SingleFireStrategy extends FireStrategy {

    private static final SingleFireStrategy SINGLE_FIRE_STRATEGY = new SingleFireStrategy();

    private SingleFireStrategy(){}

    public static SingleFireStrategy getSingleFireStrategy() {
        return SINGLE_FIRE_STRATEGY;
    }

    @Override
    public void fire(int bulletX, int bulletY, Dir dir, Team team) {
        GameModel.INSTANCE.add(new Bullet(bulletX, bulletY, dir
                , GameModel.INSTANCE.getMyTank().getId()));
    }
}

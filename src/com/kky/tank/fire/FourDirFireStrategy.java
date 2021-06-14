package com.kky.tank.fire;

/**
 * @author 柯凯元
 * @create 2021/6/13 22:40
 */

import com.kky.tank.Bullet;
import com.kky.tank.Dir;
import com.kky.tank.Team;
import com.kky.tank.frame.TankFrame;

/**
 * 四连发
 */
public class FourDirFireStrategy extends FireStrategy {

    private static final FourDirFireStrategy FOUR_DIR_FIRE_STRATEGY = new FourDirFireStrategy();

    private FourDirFireStrategy() {
    }

    public static FourDirFireStrategy getFourFireStrategy() {
        return FOUR_DIR_FIRE_STRATEGY;
    }

    @Override
    public void fire(int bulletX, int bulletY, Dir dir, Team team, TankFrame tankFrame) {
        for (int i = 0; i < 4; i++) {
            tankFrame.bullets.add(new Bullet(bulletX, bulletY, Dir.values()[i], team, tankFrame));
        }
        //tankFrame.bullets.add(new Bullet(bulletX, bulletY, dir, team, tankFrame));
    }
}

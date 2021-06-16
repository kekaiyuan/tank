package com.kky.cor;

import com.kky.tank.*;
import com.kky.tank.tank.PlayerTank;
import com.kky.tank.tank.Tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:21
 */
public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o2 instanceof PlayerTank) {
            return false;
        }
        if (o1 instanceof Tank && o2 instanceof Bullet) {
            GameObject temp = o1;
            o1 = o2;
            o2 = temp;
        }
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            if (bullet.getTeam().equals(tank.getTeam())) {
                return false;
            }

            Rectangle bulletRect = bullet.getRectangle();
            Rectangle tankRect = tank.getRectangle();

            if (bulletRect.intersects(tankRect)) {
                //相交区域
                Rectangle intersection = bulletRect.intersection(tankRect);

                //计算撞击点
                int impactX = intersection.x + intersection.width / 2;
                int impactY = intersection.y + intersection.height / 2;

                GameModel.getInstance().add(new Explode(impactX, impactY));
                GameModel.getInstance().remove(bullet);
                GameModel.getInstance().remove(tank);
                return true;
            }
            return true;
        }
        return false;
    }


}

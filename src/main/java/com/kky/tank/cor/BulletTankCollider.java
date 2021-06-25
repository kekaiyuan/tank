package com.kky.tank.cor;

import com.kky.tank.*;
import com.kky.tank.net.Client;
import com.kky.tank.net.TankDieMsg;
import com.kky.tank.tank.PlayerTank;
import com.kky.tank.tank.Tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:21
 */

/**
 * 坦克和子弹的碰撞
 * 碰撞时销毁子弹和坦克，并添加爆炸
 */
public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Bullet) {
            GameObject temp = o1;
            o1 = o2;
            o2 = temp;
        }

        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            if (bullet.getId().equals(tank.getId())) {
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

                GameModel.INSTANCE.add(new Explode(impactX, impactY));

                //通过网络发送坦克死亡的消息
                TankDieMsg msg = new TankDieMsg(impactX,impactY, tank.getId());
                Client.INSTANCE.send(msg);

                //从Model层中删除对象
                GameModel.INSTANCE.remove(bullet);
                GameModel.INSTANCE.remove(tank);
            }
            return true;
        }
        return false;
    }

}

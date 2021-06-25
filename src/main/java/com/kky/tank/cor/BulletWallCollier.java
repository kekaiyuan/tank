package com.kky.tank.cor;

import com.kky.tank.Bullet;
import com.kky.tank.GameModel;
import com.kky.tank.GameObject;
import com.kky.tank.Wall;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/16 18:38
 */

/**
 * 子弹和墙的碰撞
 * 碰撞时销毁子弹
 */
public class BulletWallCollier implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Wall && o2 instanceof Bullet){
            GameObject temp = o1;
            o1 = o2;
            o2 = temp;
        }
        if(o1 instanceof Bullet && o2 instanceof Wall){
            Bullet bullet = (Bullet)o1;
            Wall wall = (Wall)o2;
            Rectangle bulletRect = bullet.getRectangle();
            Rectangle wallRect = wall.getRectangle();
            if (bulletRect.intersects(wallRect)) {
                GameModel.INSTANCE.remove(bullet);
            }
            return true;
        }
        return false;
    }

}

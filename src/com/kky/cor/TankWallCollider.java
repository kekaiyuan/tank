package com.kky.cor;

import com.kky.tank.Bullet;
import com.kky.tank.GameObject;
import com.kky.tank.Wall;
import com.kky.tank.tank.Tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/16 18:18
 */
public class TankWallCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Tank) {
            GameObject temp = o1;
            o1 = o2;
            o2 = temp;
        }
        if (o1 instanceof Tank && o2 instanceof Wall) {
            Rectangle tankRect = o1.getRectangle();
            Rectangle wallRect = o2.getRectangle();
            if(tankRect.intersects(wallRect)){
                ((Tank)o1).back();
            }
            return true;
        }
        return false;
    }

}

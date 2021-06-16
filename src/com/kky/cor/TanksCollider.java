package com.kky.cor;

import com.kky.tank.GameObject;
import com.kky.tank.tank.Tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:38
 */
public class TanksCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 == o2) {
            return false;
        }
        if (o1 instanceof Tank && o2 instanceof Tank) {
            //((Tank) o1).collideWithTank(((Tank) o2));
            Tank tank1 = (Tank)o1;
            Tank tank2 = (Tank)o2;
            if(tank1.getRectangle().intersects(tank2.getRectangle())){
                tank1.back();
                tank2.back();
            }
            //System.out.println("a");
            return true;
        }
        return false;
    }

}



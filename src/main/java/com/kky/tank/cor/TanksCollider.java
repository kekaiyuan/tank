package com.kky.tank.cor;

import com.kky.tank.GameObject;
import com.kky.tank.tank.Tank;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:38
 */

/**
 * 坦克和坦克的碰撞
 * 碰撞两辆坦克回退到各自的上一步的位置
 */
public class TanksCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 == o2) {
            return false;
        }
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank tank1 = (Tank)o1;
            Tank tank2 = (Tank)o2;
            if(tank1.getRectangle().intersects(tank2.getRectangle())){
                tank1.back();
                tank2.back();
            }
            return true;
        }
        return false;
    }

}



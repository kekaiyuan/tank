package com.kky.tank.cor;

import com.kky.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 柯凯元
 * @create 2021/6/15 21:16
 */

/**
 * 碰撞检测的责任链
 * CollideChain为什么要继承Collider？
 * 这样两个链条就可以拼接到一起
 */
public class ColliderChain implements Collider {

    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        add(new BulletTankCollider());
        add(new TanksCollider());
        add(new BulletWallCollier());
        add(new TankWallCollider());
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (colliders.get(i).collide(o1, o2)) {
                return false;
            }
        }
        return true;
    }

}

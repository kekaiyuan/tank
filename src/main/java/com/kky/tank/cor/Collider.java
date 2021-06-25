package com.kky.tank.cor;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:19
 */

import com.kky.tank.GameObject;

/**
 * 负责游戏物体的碰撞
 */
public interface Collider {

    boolean collide(GameObject o1,GameObject o2);

}

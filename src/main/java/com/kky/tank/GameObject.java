package com.kky.tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:03
 */

/**
 * 所有游戏实体的父类
 */
public abstract class GameObject {

    //坐标
    int x, y;

    //游戏实体的矩形，用于碰撞检测
    protected Rectangle rectangle = null;

    public abstract void paint(Graphics g);

    public Rectangle getRectangle() {
        return rectangle;
    }

}

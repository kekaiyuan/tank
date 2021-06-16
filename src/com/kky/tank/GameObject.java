package com.kky.tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/15 18:03
 */
public abstract class GameObject {

    int x, y;
    protected Rectangle rectangle = null;

    public abstract void paint(Graphics g);

    public Rectangle getRectangle() {
        return rectangle;
    }

}

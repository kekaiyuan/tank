package com.kky.tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/16 17:44
 */

/**
 * 游戏实体之墙
 */
public class Wall extends GameObject {

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
        rectangle = new Rectangle(x,y,ResourceMgr.wall.getWidth(),ResourceMgr.wall.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.wall, x, y, null);
    }

}

package com.kky.tank.tank;

import com.kky.tank.*;
import com.kky.tank.fire.FireStrategy;
import com.kky.tank.frame.TankFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 柯凯元
 * @create 2021/6/12 2:37
 */
public class Tank extends GameObject {

    int x, y,oldX, oldY;
    Dir dir;
    static final int SPEED = Integer.parseInt(PropertyMgr.get("tankSpeed").toString());
    boolean isMoving = true;
    Team team = null;
    BufferedImage[] tankImage = null;

    public Tank(int x, int y) {
        //x，y为左上角坐标
        this.x = x;
        this.y = y;
        oldX = x;
        oldY = y;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(tankImage[dir.ordinal()], x, y, null);
    }

    public void back() {
        x = oldX;
        y = oldY;
    }

    public void move() {
        oldX = x;
        oldY = y;
        switch (dir) {
            case UP:
                y -= SPEED;
                if (y - GameModel.getInstance().getTopBound() < 0)
                    y = GameModel.getInstance().getTopBound();
                break;
            case DOWN:
                y += SPEED;
                if (y + tankImage[dir.ordinal()].getHeight() > GameModel.getInstance().getDownBound())
                    y = GameModel.getInstance().getDownBound() - tankImage[dir.ordinal()].getHeight();
                break;
            case LEFT:
                x -= SPEED;
                if (x < 0)
                    x = 0;
                break;
            case RIGHT:
                x += SPEED;
                if (x + tankImage[dir.ordinal()].getWidth() > GameModel.getInstance().getRightBound())
                    x = GameModel.getInstance().getRightBound() - tankImage[dir.ordinal()].getWidth();
                break;
        }
    }

    public void fire(FireStrategy fireStrategy) {
        //子弹的x，y为坦克中心点坐标
        int bulletX = this.x + tankImage[dir.ordinal()].getWidth() / 2;
        int bulletY = this.y + tankImage[dir.ordinal()].getHeight() / 2;
        fireStrategy.fire(bulletX, bulletY, this.dir, this.team);
    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(x, y, tankImage[dir.ordinal()].getWidth(),
                tankImage[dir.ordinal()].getHeight());
        return rectangle;
    }

}
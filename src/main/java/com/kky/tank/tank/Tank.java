package com.kky.tank.tank;

import com.kky.tank.*;
import com.kky.tank.fire.FireStrategy;
import com.kky.tank.frame.TankFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author 柯凯元
 * @create 2021/6/12 2:37
 */
public class Tank extends GameObject {

    //坐标和上一次的坐标
    int x, y, oldX, oldY;
    //方向
    Dir dir;
    //速度
    static final int SPEED = Integer.parseInt(PropertyMgr.get("tankSpeed").toString());
    //是否能够移动
    boolean isMoving = true;
    //队伍
    Team team = null;
    //图片
    BufferedImage[] tankImage = null;
    //id
    private UUID id = UUID.randomUUID();
    //是否存活
    private boolean isLiving = true;

    public Tank(int x, int y) {
        //x，y为左上角坐标
        this.x = x;
        this.y = y;
        oldX = x;
        oldY = y;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Team getTeam() {
        return team;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Dir getDir() {
        return dir;
    }

    public boolean getLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(tankImage[dir.ordinal()], x, y, null);
    }

    //回退，当与坦克或墙碰撞时调用
    public void back() {
        x = oldX;
        y = oldY;
    }

    //移动，带有边界检测
    public void move() {
        oldX = x;
        oldY = y;
        switch (dir) {
            case UP:
                y -= SPEED;
                if (y - GameModel.INSTANCE.getTopBound() < 0)
                    y = GameModel.INSTANCE.getTopBound();
                break;
            case DOWN:
                y += SPEED;
                if (y + tankImage[dir.ordinal()].getHeight() > GameModel.INSTANCE.getDownBound())
                    y = GameModel.INSTANCE.getDownBound() - tankImage[dir.ordinal()].getHeight();
                break;
            case LEFT:
                x -= SPEED;
                if (x < 0)
                    x = 0;
                break;
            case RIGHT:
                x += SPEED;
                if (x + tankImage[dir.ordinal()].getWidth() > GameModel.INSTANCE.getRightBound())
                    x = GameModel.INSTANCE.getRightBound() - tankImage[dir.ordinal()].getWidth();
                break;
        }
    }

    //开火
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
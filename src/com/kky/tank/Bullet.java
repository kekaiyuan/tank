package com.kky.tank;

import com.kky.tank.frame.TankFrame;
import com.kky.tank.tank.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Bullet {

    //子弹速度
    private static final int SPEED = Integer.parseInt(PropertyMgr.get("bulletSpeed").toString());
    //    //子弹大小
    //    private static final int WIDTH = 30, HEIGHT = 30;
    //持有一个窗口的引用
    TankFrame tankFrame = null;
    private Rectangle rectangle = new Rectangle();

    //左上角坐标
    private int x, y;
    //方向
    private Dir dir;



    private Team team = null;

    public Bullet(int x, int y, Dir dir, Team team, TankFrame tankFrame) {
        this.dir = dir;

        this.x = x;
        this.y = y;
        this.team = team;
        this.tankFrame = tankFrame;
        rectangle.width = ResourceMgr.bullet[dir.ordinal()].getWidth();
        rectangle.height = ResourceMgr.bullet[dir.ordinal()].getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Team getTeam() {
        return team;
    }



    //画子弹
    public void paint(Graphics g) {
        int bulletX = x - ResourceMgr.bullet[dir.ordinal()].getWidth() / 2;
        int bulletY = y - ResourceMgr.bullet[dir.ordinal()].getHeight() / 2;
        g.drawImage(ResourceMgr.bullet[dir.ordinal()], bulletX, bulletY, null);
        move();
    }

    //子弹移动
    private void move() {
        //子弹移动

        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }

        rectangle.x=this.x;
        rectangle.y=this.y;

        //判断子弹是否越界，是则销毁对象
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            tankFrame.bullets.remove(this);
        }
    }


    public void collideWith(Tank tank) {
        rectangle.x=this.x;
        rectangle.y=this.y;

        if (this.getTeam().equals(tank.getTeam()))
            return;

        if (rectangle.intersects(tank.getRectangle())) {
            //相交区域
            Rectangle intersection = rectangle.intersection(tank.getRectangle());

            //计算撞击点
            int impactX = intersection.x + intersection.width / 2;
            int impactY = intersection.y + intersection.height / 2;

            tankFrame.explodes.add(new Explode(impactX, impactY, tankFrame));
            tankFrame.bullets.remove(this);
            tankFrame.tanks.remove(tank);
        }
    }

}

package com.kky.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Bullet {

    //子弹速度
    private static final int SPEED = 10;
    //    //子弹大小
//    private static final int WIDTH = 30, HEIGHT = 30;
    //持有一个窗口的引用
    TankFrame tankFrame = null;

    private int x, y;
    //方向
    private Dir dir;
    //子弹是否存在
    //private boolean live = true;
    private BufferedImage imageBullet = null;

    private Team team = null;

    public Bullet(int bulletX, int bulletY, Dir dir, Team team, TankFrame tankFrame) {
        this.dir = dir;
        setImageBullet();
        this.x = bulletX - imageBullet.getWidth() / 2;
        this.y = bulletY - imageBullet.getHeight() / 2;
        this.team = team;
        this.tankFrame = tankFrame;
    }

//    public boolean getLive() {
//        return live;
//    }

    public Team getTeam() {
        return team;
    }

    public void setImageBullet() {
        switch (dir) {
            case UP:
                imageBullet = ResourceMgr.bulletUp;
                break;
            case DOWN:
                imageBullet = ResourceMgr.bulletDown;
                break;
            case LEFT:
                imageBullet = ResourceMgr.bulletLeft;
                break;
            case RIGHT:
                imageBullet = ResourceMgr.bulletRight;
                break;
        }
    }

    //画子弹
    public void paint(Graphics g) {
        setImageBullet();
        g.drawImage(imageBullet, x, y, null);
        move();
    }

    //子弹移动a
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

        //判断子弹是否越界，是则销毁对象
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            tankFrame.bullets.remove(this);
        }
    }

    public Rectangle getRectangle() {
        Rectangle bulletRectangle = new Rectangle(x, y, imageBullet.getWidth(), imageBullet.getHeight());
        return bulletRectangle;
    }

}

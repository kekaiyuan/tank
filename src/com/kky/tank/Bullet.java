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

    private BufferedImage imageBullet = null;

    private Team team = null;

    public Bullet(int x, int y, Dir dir, Team team, TankFrame tankFrame) {
        this.dir = dir;
        setImageBullet();
        this.x = x;
        this.y = y;
        this.team = team;
        this.tankFrame = tankFrame;
        rectangle.width = imageBullet.getWidth();
        rectangle.height = imageBullet.getHeight();
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
        int bulletX = x - imageBullet.getWidth() / 2;
        int bulletY = y - imageBullet.getHeight() / 2;
        g.drawImage(imageBullet, bulletX, bulletY, null);
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

    public Rectangle getRectangle() {
        Rectangle bulletRectangle = new Rectangle(x, y, imageBullet.getWidth(), imageBullet.getHeight());
        return bulletRectangle;
    }

    public void collideWith(Tank tank) {
        Rectangle bulletRectangle = this.getRectangle();

        if (this.getTeam().equals(tank.getTeam()))
            return;

        if (bulletRectangle.intersects(tank.getRectangle())) {
            //相交区域
            Rectangle intersection = bulletRectangle.intersection(tank.getRectangle());

            //计算撞击点
            int impactX = intersection.x + intersection.width / 2;
            int impactY = intersection.y + intersection.height / 2;

            tankFrame.explodes.add(new Explode(impactX, impactY, tankFrame));
            tankFrame.bullets.remove(this);
            tankFrame.enemyTanks.remove(tank);
        }
    }

}

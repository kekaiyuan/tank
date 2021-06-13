package com.kky.tank.tank;

import com.kky.tank.*;
import com.kky.tank.frame.TankFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author 柯凯元
 * @create 2021/6/12 2:37
 */
public class Tank {

    protected int x, y;
    protected Dir dir;
    protected static final int SPEED = Integer.parseInt(PropertyMgr.get("tankSpeed").toString());
    protected boolean isMoving;
    protected TankFrame tankFrame = null;
    protected static final int TANK_WIDTH = 50, TANK_HEIGHT = 50;
    //private BufferedImage tankImage = null;
    protected Team team = null;
    protected Random random = new Random();
    protected BufferedImage[] tankImage = null;
    protected Rectangle rectangle = new Rectangle();

    public Tank(int x, int y, TankFrame tankFrame) {
        //x，y为左上角坐标
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

//    public void setDir(Dir dir) {
//        this.dir = dir;
//    }
//
//    public void setMoving(boolean moving) {
//        isMoving = moving;
//    }

    public Team getTeam() {
        return team;
    }

    public void paint(Graphics g) {
        g.drawImage(tankImage[dir.ordinal()], x, y, null);
        move();
    }

    protected void move() {

        if (!isMoving)
            return;

        switch (dir) {
            case UP:
                y -= SPEED;
                if (y - tankFrame.getInsets().top < 0)
                    y = tankFrame.getInsets().top;
                break;
            case DOWN:
                y += SPEED;
                if (y + tankImage[dir.ordinal()].getHeight() > tankFrame.GAME_HEIGHT)
                    y = tankFrame.GAME_HEIGHT - tankImage[dir.ordinal()].getHeight();
                break;
            case LEFT:
                x -= SPEED;
                if (x < 0)
                    x = 0;
                break;
            case RIGHT:
                x += SPEED;
                if (x + tankImage[dir.ordinal()].getWidth() > tankFrame.GAME_WIDTH)
                    x = tankFrame.GAME_WIDTH - tankImage[dir.ordinal()].getWidth();
                break;
        }

        for (int i = 0; i < tankFrame.enemyTanks.size(); i++) {
            if (this == tankFrame.enemyTanks.get(i)) {
                continue;
            } else {
                Rectangle rectangle1 = this.getRectangle();
                Rectangle rectangle2 = tankFrame.enemyTanks.get(i).getRectangle();
                if (rectangle1.intersects(rectangle2)) {
                    int x1 = rectangle2.x;
                    int x2 = rectangle2.x + rectangle2.width;
                    if (x1 <= x && x <= x2) {
                        if (dir == Dir.LEFT)
                            x = x2;
                        if (dir == Dir.RIGHT)
                            x = x1;
                    }
                    int y1 = rectangle2.y;
                    int y2 = rectangle2.y + rectangle2.height;
                    if (y1 <= y && y < y2) {
                        if (dir == Dir.UP)
                            y = y2;
                        if (dir == Dir.DOWN)
                            y = y1;
                    }
                }
            }
        }
    }

    private void robotMove() {

        if (random.nextInt(100) > 90) {
            this.dir = Dir.values()[random.nextInt(4)];
            //this.dir = Dir.LEFT;
        }

        if (random.nextInt(100) > 95) {
            fire();
        }
    }

    public void fire() {
        //子弹的x，y为坦克中心点坐标
        int bulletX = this.x + tankImage[dir.ordinal()].getWidth() / 2;
        int bulletY = this.y + tankImage[dir.ordinal()].getHeight() / 2;
        tankFrame.bullets.add(new Bullet(bulletX, bulletY, this.dir, this.team, this.tankFrame));
    }

    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = this.tankImage[dir.ordinal()].getWidth();
        rectangle.height = this.tankImage[dir.ordinal()].getHeight();
        return rectangle;
    }

}

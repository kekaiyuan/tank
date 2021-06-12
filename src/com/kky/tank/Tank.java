package com.kky.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author 柯凯元
 * @create 2021/6/12 2:37
 */
public class Tank {

    private int x, y;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 5;
    private boolean isMoving = true;
    private TankFrame tankFrame = null;
    private static final int TANK_WIDTH = 50, TANK_HEIGHT = 50;
    private BufferedImage tankImage = null;
    private Team team = null;
    private Random random = new Random();

    public Tank(int x, int y, Dir dir, Team team, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.team = team;
        this.tankFrame = tankFrame;
        setTankImage();
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Team getTeam() {
        return team;
    }

    public void setTankImage() {
        switch (dir) {
            case UP:
                tankImage = ResourceMgr.tankUp;
                break;
            case DOWN:
                tankImage = ResourceMgr.tankDown;
                break;
            case LEFT:
                tankImage = ResourceMgr.tankLeft;
                break;
            case RIGHT:
                tankImage = ResourceMgr.tankRight;
                break;
        }
    }

    public void paint(Graphics g) {
        setTankImage();
        g.drawImage(tankImage, x, y, null);
        move();
    }

    private void move() {
        if (!isMoving)
            return;

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

        if (x < 0)
            x = 0;
        if (x + tankImage.getWidth() > tankFrame.GAME_WIDTH)
            x = tankFrame.GAME_WIDTH - tankImage.getWidth();
        if (y - tankFrame.getInsets().top < 0)
            y = tankFrame.getInsets().top;
        if (y + tankImage.getHeight() > tankFrame.GAME_HEIGHT)
            y = tankFrame.GAME_HEIGHT - tankImage.getHeight();
        //System.out.println(tankFrame.getInsets().bottom);

        if(random.nextInt(10)>8){
            fire();
        }

    }

    public void fire() {
        int bulletX = this.x + tankImage.getWidth() / 2;
        int bulletY = this.y + tankImage.getHeight() / 2;
        tankFrame.bullets.add(new Bullet(bulletX, bulletY, this.dir, this.team,this.tankFrame));
    }

    public Rectangle getRectangle() {
        Rectangle tankRectangle = new Rectangle(x, y, tankImage.getHeight(), tankImage.getHeight());
        return tankRectangle;
    }

}

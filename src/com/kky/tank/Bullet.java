package com.kky.tank;

import com.kky.tank.frame.TankFrame;
import com.kky.tank.tank.Tank;

import java.awt.*;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Bullet extends GameObject {

    //子弹速度
    private static final int SPEED = Integer.parseInt(PropertyMgr.get("bulletSpeed").toString());


    //左上角坐标
    private int x, y;
    //方向
    private Dir dir;

    private Team team = null;

    public Bullet(int x, int y, Dir dir, Team team) {
        this.dir = dir;

        this.x = x;
        this.y = y;
        this.team = team;
        rectangle = new Rectangle(x,y,ResourceMgr.bullet[dir.ordinal()].getWidth(),ResourceMgr.bullet[dir.ordinal()].getHeight());

    }

    public Team getTeam() {
        return team;
    }

    //画子弹
    public void paint(Graphics g) {
        int bulletX = x - ResourceMgr.bullet[dir.ordinal()].getWidth() / 2;
        int bulletY = y - ResourceMgr.bullet[dir.ordinal()].getHeight() / 2;
        g.drawImage(ResourceMgr.bullet[dir.ordinal()], bulletX, bulletY, null);
    }

    //子弹移动
    public void move() {

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

        rectangle.x = this.x;
        rectangle.y = this.y;

        //判断子弹是否越界，是则销毁对象
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            GameModel.getInstance().remove(this);
        }
    }



    public boolean colliderWithWall(Wall wall) {
        Rectangle wallRect = wall.getRectangle();
        if (rectangle.intersects(wallRect)) {
            GameModel.getInstance().remove(this);
            return true;
        }
        return false;
    }

}

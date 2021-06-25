package com.kky.tank;

import com.kky.tank.frame.TankFrame;
import com.kky.tank.tank.Tank;

import java.awt.*;
import java.util.UUID;

/**
 * @author 柯凯元
 * @create 2021/6/12 3:03
 */
public class Bullet extends GameObject {

    //子弹速度
    private static final int SPEED = Integer.parseInt(PropertyMgr.get("bulletSpeed").toString());

    //方向
    private Dir dir;

    //子弹从属于哪个坦克
    private UUID id = null;

    public Bullet(int x, int y, Dir dir, UUID id) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.id = id;
        this.rectangle = new Rectangle(x,y,ResourceMgr.bullet[dir.ordinal()].getWidth(),ResourceMgr.bullet[dir.ordinal()].getHeight());
    }

    public UUID getId() {
        return id;
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
            GameModel.INSTANCE.remove(this);
        }
    }

}

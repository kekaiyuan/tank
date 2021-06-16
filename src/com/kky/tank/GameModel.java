package com.kky.tank;

import com.kky.cor.ColliderChain;
import com.kky.tank.fire.FireMode;
import com.kky.tank.tank.PlayerTank;
import com.kky.tank.tank.RobotTank;
import com.kky.tank.tank.Tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 柯凯元
 * @create 2021/6/15 17:08
 */
public class GameModel {

    private PlayerTank myTank = new PlayerTank(350, 400);

    //碰撞检测的责任链
    ColliderChain colliderChain = new ColliderChain();

    private int tankNum;
    private int bulletNum;
    private int explodeNum;
    private int topBound, downBound, leftBound, rightBound;

    private static final GameModel instance = new GameModel();

    private List<GameObject> objects = new ArrayList<>();

    private GameModel() {
        //添加玩家坦克
        add(myTank);
        //初始化敌方坦克
        for (int i = 0; i < Integer.parseInt(PropertyMgr.get("initTankSum").toString()); i++) {
            add(new RobotTank(50 + i * 140, 200));
        }

        for (int i = 0; i < Integer.parseInt(PropertyMgr.get("wallSum").toString()); i++) {
            add(new Wall(100 + ResourceMgr.wall.getWidth() * (i % 10), 400 + i / 10 * 200));
        }
    }

    public int getTopBound() {
        return topBound;
    }

    public void setTopBound(int topBound) {
        this.topBound = topBound;
    }

    public int getDownBound() {
        return downBound;
    }

    public void setDownBound(int downBound) {
        this.downBound = downBound;
    }

    public void setLeftBound(int leftBound) {
        this.leftBound = leftBound;
    }

    public int getRightBound() {
        return rightBound;
    }

    public void setRightBound(int rightBound) {
        this.rightBound = rightBound;
    }

    public static GameModel getInstance() {
        return instance;
    }

    public void add(GameObject gameObject) {
        if (gameObject instanceof Tank) {
            tankNum++;
        } else if (gameObject instanceof Bullet) {
            bulletNum++;
        } else if (gameObject instanceof Explode) {
            explodeNum++;
        }
        objects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        if (gameObject instanceof Tank) {
            tankNum--;
        } else if (gameObject instanceof Bullet) {
            bulletNum--;
        } else if (gameObject instanceof Explode) {
            explodeNum--;
        }
        objects.remove(gameObject);
    }

    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bulletNum, 10, 60);
        g.drawString("坦克的数量：" + tankNum, 10, 80);
        g.drawString("爆炸的数量：" + explodeNum, 10, 100);
        g.drawString("开火模式为：" + FireMode.values()[myTank.getFireMode()], 10, 120);

        g.setColor(color);

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof Tank) {
                ((Tank) objects.get(i)).move();
                continue;
            }
            if (objects.get(i) instanceof Bullet) {
                ((Bullet) objects.get(i)).move();
                continue;
            }
        }

        //碰撞检测，使用责任链
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                colliderChain.collide(o1, o2);
            }
        }

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
    }

    public PlayerTank getMyTank() {
        return myTank;
    }

}

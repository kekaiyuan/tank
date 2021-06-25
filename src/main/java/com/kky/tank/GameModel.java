package com.kky.tank;

import com.kky.tank.cor.ColliderChain;
import com.kky.tank.fire.FireMode;
import com.kky.tank.frame.TankFrame;
import com.kky.tank.net.TankJoinMsg;
import com.kky.tank.tank.PlayerTank;
import com.kky.tank.tank.Tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author 柯凯元
 * @create 2021/6/15 17:08
 */

/**
 * 游戏model层
 */
public class GameModel {

    //单例模式
    public static final GameModel INSTANCE = new GameModel();

    private PlayerTank myTank = null;

    //碰撞检测的责任链
    ColliderChain colliderChain = new ColliderChain();

    //游戏实体数量
    private int tankNum;
    private int bulletNum;
    private int explodeNum;

    //游戏边界
    private int topBound, downBound, leftBound, rightBound;

    //存储游戏实体
    private List<GameObject> objects = new ArrayList<>();

    private GameModel() {

        //生成玩家的坦克
        Random random = new Random();
        myTank = new PlayerTank(random.nextInt(TankFrame.GAME_WIDTH)
                , random.nextInt(TankFrame.GAME_HEIGHT));
        add(myTank);

//        //初始化敌方坦克
//        for (int i = 0; i < Integer.parseInt(PropertyMgr.get("initTankSum").toString()); i++) {
//            add(new RobotTank(50 + i * 140, 200));
//        }

        //画墙
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

    //添加游戏实体
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

    //删除游戏实体
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

        //移动坦克和子弹
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

        //绘制游戏
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
    }

    public PlayerTank getMyTank() {
        return myTank;
    }

    //根据网络消息添加坦克
    public boolean add(TankJoinMsg tankJoinMsg) {
        for (GameObject object : objects) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                if (tank.getId().equals(tankJoinMsg.id)) {
                    return false;
                }
            }
        }
        PlayerTank playerTank = new PlayerTank(tankJoinMsg.x, tankJoinMsg.y);
        playerTank.setId(tankJoinMsg.id);
        add(playerTank);
        return true;
    }

    //根据网络消息更改坦克方向
    public void tankDirChanged(Dir dir, UUID id) {
        for (GameObject object : objects) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                if (tank.getId().equals(id) && !myTank.getId().equals(id)) {
                    tank.setDir(dir);
                    tank.setMoving(true);
                }
            }
        }
    }

    //根据网络消息停止坦克
    public void tankStop(UUID id) {
        for (GameObject object : objects) {
            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                if (tank.getId().equals(id) && !myTank.getId().equals(id)) {
                    tank.setMoving(false);
                }
            }
        }
    }

    //根据网络消息创建子弹
    public void BulletNew(UUID id) {
        for (GameObject object : objects) {
            if (object instanceof Tank) {
                PlayerTank tank = (PlayerTank) object;
                if (tank.getId().equals(id) && !myTank.getId().equals(id)) {
                    tank.fire();
                }
            }
        }
    }

    //根据网络消息删除坦克
    public void tankDie(int impactX, int impactY, UUID id) {
        add(new Explode(impactX,impactY));
        for (GameObject object : objects) {
            if (object instanceof Tank) {
                PlayerTank tank = (PlayerTank) object;
                if (tank.getId().equals(id) ) {
                    objects.remove(tank);
                }
                if(myTank.getId().equals(id)){
                    myTank.setLiving(false);
                }
            }
        }
    }
}

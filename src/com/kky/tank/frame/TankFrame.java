package com.kky.tank.frame;

import com.kky.tank.Bullet;
import com.kky.tank.Dir;
import com.kky.tank.Explode;
import com.kky.tank.PropertyMgr;
import com.kky.tank.tank.PlayerTank;
import com.kky.tank.tank.Tank;
import com.sun.deploy.util.JVMParameters;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 柯凯元
 * @create 2021/6/11 19:03
 */
public class TankFrame extends Frame {


    //Tank myTank = new Tank(350, 400, Dir.UP, Team.PLAYER, this);
    PlayerTank myTank = new PlayerTank(350, 400, this);

    public List<Tank> enemyTanks = new ArrayList<>();

    //Bullet bullet = null;
    public List<Bullet> bullets = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();
    public static final int GAME_WIDTH = Integer.parseInt(PropertyMgr.get("gameWidth").toString());
    public static final int GAME_HEIGHT = Integer.parseInt(PropertyMgr.get("gameHeight").toString());

    public TankFrame() throws HeadlessException {



        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);
        setLocationRelativeTo(null);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void addBullet() {

    }

    @Override
    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.drawString("敌军的数量：" + enemyTanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(color);
        myTank.paint(g);

        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for(int j =0;j<enemyTanks.size();j++){
                bullets.get(i).collideWith(enemyTanks.get(j));
            }
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
    }

    class MyKeyListener extends KeyAdapter {

        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
            }

            setMainTankDir();
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    myTank.fire();
                    break;
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
            }
            setMainTankDir();
        }

        //设置坦克方向
        private void setMainTankDir() {

            if (up)
                myTank.setDir(Dir.UP);
            else if (down)
                myTank.setDir(Dir.DOWN);
            else if (left)
                myTank.setDir(Dir.LEFT);
            else if (right)
                myTank.setDir(Dir.RIGHT);
            else {
                myTank.setMoving(false);
                return;
            }
            myTank.setMoving(true);
        }

    }

}


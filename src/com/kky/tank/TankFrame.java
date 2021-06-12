package com.kky.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 柯凯元
 * @create 2021/6/11 19:03
 */
public class TankFrame extends Frame {

    Tank myTank = new Tank(350, 400, Dir.UP, Team.TEAM_A,this);
    List<Tank> enemyTanks = new ArrayList<>();
    //Bullet bullet = null;
    List<Bullet> bullets = new ArrayList<>();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    public TankFrame() throws HeadlessException {

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

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
        g.setColor(color);
        //System.out.println(bullets.size());
        myTank.paint(g);

        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(g);
        }

//        for(Iterator<Bullet> it=bullets.iterator();it.hasNext();){
//            it.next().paint(g);
//        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            Rectangle bulletRectangle = bullets.get(i).getRectangle();
            for (int j = 0; j < enemyTanks.size(); j++) {
                if(bullets.get(i).getTeam().equals(enemyTanks.get(j).getTeam())){
                    continue;
                }
                Rectangle tankRectangle = enemyTanks.get(j).getRectangle();
                if (bulletRectangle.intersects(tankRectangle)) {
                    bullets.remove(i);
                    enemyTanks.remove(j);
                    break;
                }
            }
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


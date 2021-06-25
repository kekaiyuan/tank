package com.kky.tank.frame;

import com.kky.tank.*;
import com.kky.tank.net.BulletNewMsg;
import com.kky.tank.net.Client;
import com.kky.tank.net.TankDirChangedMsg;
import com.kky.tank.net.TankStopMsg;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 柯凯元
 * @create 2021/6/11 19:03
 */

/**
 * 游戏的View层
 */
public class TankFrame extends Frame {

    //游戏的高度和宽度
    public static final int GAME_WIDTH = Integer.parseInt(PropertyMgr.get("gameWidth").toString());
    public static final int GAME_HEIGHT = Integer.parseInt(PropertyMgr.get("gameHeight").toString());

    //单例模式
    public static final TankFrame INSTANCE = new TankFrame();

    private TankFrame() throws HeadlessException {

        //初始化
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("tank war");
        this.setLocationRelativeTo(null);

        //监听键盘
        this.addKeyListener(new MyKeyListener());

        //监听关闭窗口时间
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //设置游戏边界，防止游戏实体越界
        GameModel.INSTANCE.setLeftBound(0);
        GameModel.INSTANCE.setTopBound(this.getInsets().top);
        GameModel.INSTANCE.setDownBound(GAME_HEIGHT);
        GameModel.INSTANCE.setRightBound(GAME_WIDTH);
    }

    //双缓冲
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

    @Override
    public void paint(Graphics g) {
        if(GameModel.INSTANCE == null){
            System.out.println("null");
        }
        GameModel.INSTANCE.paint(g);
    }

    //根据四个方向键设置坦克方向
    class MyKeyListener extends KeyAdapter {
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        @Override
        public void keyPressed(KeyEvent e) {
            if(!GameModel.INSTANCE.getMyTank().getLiving()){
                return;
            }
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
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(!GameModel.INSTANCE.getMyTank().getLiving()){
                return;
            }

            switch (e.getKeyCode()) {
                case KeyEvent.VK_CONTROL:
                    GameModel.INSTANCE.getMyTank().changeFireMode();
                    break;
                case KeyEvent.VK_SPACE:
                    GameModel.INSTANCE.getMyTank().fire();

                    //通过网络发送本机坦克的开火消息
                    BulletNewMsg msg = new BulletNewMsg(GameModel.INSTANCE.getMyTank().getId());
                    Client.INSTANCE.send(msg);

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
                GameModel.INSTANCE.getMyTank().setDir(Dir.UP);
            else if (down)
                GameModel.INSTANCE.getMyTank().setDir(Dir.DOWN);
            else if (left)
                GameModel.INSTANCE.getMyTank().setDir(Dir.LEFT);
            else if (right)
                GameModel.INSTANCE.getMyTank().setDir(Dir.RIGHT);
            else {
                GameModel.INSTANCE.getMyTank().setMoving(false);

                //网络发送本机坦克的停止消息
                TankStopMsg msg = new TankStopMsg(GameModel.INSTANCE.getMyTank().getId());
                Client.INSTANCE.send(msg);

                return;
            }

            GameModel.INSTANCE.getMyTank().setMoving(true);

            //通过网络发送本机坦克的方向改变的消息
            TankDirChangedMsg msg = new TankDirChangedMsg(GameModel.INSTANCE.getMyTank().getDir()
                    ,GameModel.INSTANCE.getMyTank().getId());
            Client.INSTANCE.send(msg);
        }

    }

}


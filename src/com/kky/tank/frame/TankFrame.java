package com.kky.tank.frame;

import com.kky.tank.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 柯凯元
 * @create 2021/6/11 19:03
 */
public class TankFrame extends Frame {

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

        GameModel.getInstance().setLeftBound(0);
        GameModel.getInstance().setTopBound(this.getInsets().top);
        GameModel.getInstance().setDownBound(GAME_HEIGHT);
        GameModel.getInstance().setRightBound(GAME_WIDTH);
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

    @Override
    public void paint(Graphics g) {

        GameModel.getInstance().paint(g);
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
            //repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_CONTROL:
                    GameModel.getInstance().getMyTank().changeFireMode();
                    break;
                case KeyEvent.VK_SPACE:
                    GameModel.getInstance().getMyTank().fire();
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
                GameModel.getInstance().getMyTank().setDir(Dir.UP);
            else if (down)
                GameModel.getInstance().getMyTank().setDir(Dir.DOWN);
            else if (left)
                GameModel.getInstance().getMyTank().setDir(Dir.LEFT);
            else if (right)
                GameModel.getInstance().getMyTank().setDir(Dir.RIGHT);
            else {
                GameModel.getInstance().getMyTank().setMoving(false);
                return;
            }
            GameModel.getInstance().getMyTank().setMoving(true);
        }

    }

}


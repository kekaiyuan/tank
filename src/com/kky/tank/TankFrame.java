package com.kky.tank;

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

    int x = 200, y = 200;

    public TankFrame() throws HeadlessException {

        setSize(800, 600);
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

    @Override
    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        //x += 10;
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
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
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
        }

    }

//    class MyKeyListener extends KeyAdapter {
//
//        boolean up = false;
//        boolean down = false;
//        boolean left = false;
//        boolean right = false;
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_LEFT:
//                    x -= 10;
//                    break;
//                case KeyEvent.VK_UP:
//                    y -= 10;
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    x += 10;
//                    break;
//                case KeyEvent.VK_DOWN:
//                    y += 10;
//                    break;
//                default:
//                    break;
//            }
//            repaint();
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            super.keyReleased(e);
//        }
//
//    }
}

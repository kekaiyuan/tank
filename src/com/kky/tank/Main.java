package com.kky.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 柯凯元
 * @create 2021/6/11 16:29
 */
public class Main {

    public static void main(String[] args) {
        TankFrame tf = new TankFrame();

        while(true){
            try {
                Thread.sleep(50);
                tf.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

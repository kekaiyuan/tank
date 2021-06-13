package com.kky.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 柯凯元
 * @create 2021/6/12 15:33
 */

/**
 * 图片资源管理
 */
public class ResourceMgr {

    //图片的路径
    private static String picturePath = "src/images/";

    //坦克上下左右四个方向的图片
    //public static BufferedImage playerTankUp, playerTankDown, playerTankLeft, playerTankRight;
    public static BufferedImage[] playerTank = new BufferedImage[4];
    //
    public static BufferedImage[] robotTank = new BufferedImage[4];
    //子弹上下左右四个方向的图片
    public static BufferedImage bulletUp, bulletDown, bulletLeft, bulletRight;
    //public static BufferedImage[] bullets = new BufferedImage[4];
    //爆炸的图片
    public static BufferedImage[] explosions = new BufferedImage[16];

    static {
        try {
            String playerTankPath = PropertyMgr.get("playerTankImage").toString();
            playerTank[0] = ImageIO.read(new File(PropertyMgr.get("playerTankImage").toString()));
            playerTank[1] = ImageUtil.rotateImage(playerTank[0], 180);
            playerTank[2] = ImageUtil.rotateImage(playerTank[0], -90);
            playerTank[3] = ImageUtil.rotateImage(playerTank[0], 90);

            robotTank[0] = ImageIO.read(new File(PropertyMgr.get("robotTankImage").toString()));
            robotTank[1] = ImageUtil.rotateImage(robotTank[0], 180);
            robotTank[2] = ImageUtil.rotateImage(robotTank[0], -90);
            robotTank[3] = ImageUtil.rotateImage(robotTank[0], 90);


            //加载子弹图片

            bulletUp = ImageIO.read(new File(picturePath + "bulletU.gif"));
            bulletDown = ImageIO.read(new File(picturePath + "bulletD.gif"));
            bulletLeft = ImageIO.read(new File(picturePath + "bulletL.gif"));
            bulletRight = ImageIO.read(new File(picturePath + "bulletR.gif"));

            //加载爆炸图片
            for (int i = 0; i < 16; i++) {
                explosions[i] = ImageIO.read(new File(picturePath + "e" + (i + 1) + ".gif"));
            }

            System.out.println(playerTank);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

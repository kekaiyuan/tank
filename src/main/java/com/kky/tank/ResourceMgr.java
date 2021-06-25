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
 * 资源管理
 * 使用单例模式
 */
public class ResourceMgr {

    private ResourceMgr(){}

    //玩家坦克的图片
    public static BufferedImage[] playerTank = new BufferedImage[4];
    //电脑坦克的图片
    public static BufferedImage[] robotTank = new BufferedImage[4];
    //子弹的图片
    public static BufferedImage[] bullet = new BufferedImage[4];
    //爆炸的图片
    public static BufferedImage[] explosions = new BufferedImage[Integer.parseInt(PropertyMgr.get("explodeImageSum").toString())];
    //墙的图片
    public static BufferedImage wall = null;

    static {
        try {
            playerTank[0] = ImageIO.read(new File(PropertyMgr.get("playerTankImage").toString()));
            playerTank[1] = ImageUtil.rotateImage(playerTank[0], 180);
            playerTank[2] = ImageUtil.rotateImage(playerTank[0], -90);
            playerTank[3] = ImageUtil.rotateImage(playerTank[0], 90);

            robotTank[0] = ImageIO.read(new File(PropertyMgr.get("robotTankImage").toString()));
            robotTank[1] = ImageUtil.rotateImage(robotTank[0], 180);
            robotTank[2] = ImageUtil.rotateImage(robotTank[0], -90);
            robotTank[3] = ImageUtil.rotateImage(robotTank[0], 90);

            bullet[0] = ImageIO.read(new File(PropertyMgr.get("bulletImage").toString()));
            bullet[1] = ImageUtil.rotateImage(bullet[0], 180);
            bullet[2] = ImageUtil.rotateImage(bullet[0], -90);
            bullet[3] = ImageUtil.rotateImage(bullet[0], 90);

            String explodeImagePath = PropertyMgr.get("explodeImage").toString();
            for (int i = 0; i < explosions.length; i++) {
                explosions[i] = ImageIO.read(new File(explodeImagePath + i + ".gif"));
            }

            wall = ImageIO.read(new File(PropertyMgr.get("wallImage").toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package com.kky.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 柯凯元
 * @create 2021/6/12 15:33
 */
public class ResourceMgr {

    public static BufferedImage tankUp, tankDown, tankLeft, tankRight;
    public static BufferedImage bulletUp,bulletDown,bulletLeft,bulletRight;

    static{
        try {
            tankUp=ImageIO.read(new File("src/images/tankU.gif"));
            tankDown=ImageIO.read(new File("src/images/tankD.gif"));
            tankLeft=ImageIO.read(new File("src/images/tankL.gif"));
            tankRight=ImageIO.read(new File("src/images/tankR.gif"));
            bulletUp=ImageIO.read(new File("src/images/bulletU.gif"));
            bulletDown=ImageIO.read(new File("src/images/bulletD.gif"));
            bulletLeft=ImageIO.read(new File("src/images/bulletL.gif"));
            bulletRight=ImageIO.read(new File("src/images/bulletR.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

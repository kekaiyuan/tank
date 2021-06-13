package com.kky.tank; /**
 * @author 柯凯元
 * @create 2021/6/13 21:53
 */

import java.io.IOException;
import java.util.Properties;

/**
 * 管理配置文件
 */
public class PropertyMgr {
    private static final Properties properties = new Properties();

    private PropertyMgr(){}

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (properties == null){
            return null;
        }
        return properties.getProperty(key);
    }

}

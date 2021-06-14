package com.kky;

import java.lang.reflect.Method;

/**
 * @author 柯凯元
 * @create 2021/6/14 16:43
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //要调用的类的包名+类名
        String className = "com.kky.Test";
        //找到对应的类
        Class clz = Class.forName(className);
        //创建对象
        Object object = clz.newInstance();

        //通过字符串找到同名方法并调用
        {
            String methodName = "Test";
            Method method = clz.getDeclaredMethod(methodName);
            method.invoke(object);

            method = clz.getDeclaredMethod(methodName, String.class);
            method.invoke(object, "helloworld");

            method = clz.getDeclaredMethod(methodName, String.class, boolean.class);
            method.invoke(object, "helloworld", true);
        }

        System.out.println("----------------");

        //通过getDeclaredMethods()查找对象所具有的所有方法并调用
        {
            //获取Method数组
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method m : methods) {
                System.out.println(m.toString());
            }

            //调用方法
            methods[3].invoke(object);
            methods[1].invoke(object, "helloworld");
            methods[2].invoke(object, "helloworld", true);

        }

    }

    public void Test() {
        System.out.println("I'm Test1");
    }

    public void Test(String str) {
        System.out.println("I'm Test2");
    }

    public void Test(String str, boolean b) {
        System.out.println("I'm Test3");
    }
}

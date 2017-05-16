package com.example.wangqiubo.Units;

/**
 * Created by wangqiubo on 2017/5/12.
 */

public class UnitSuperClass {

    static {
        System.out.println("superclass init");
    }

    public static int value = 123;

    public UnitSuperClass(){
        System.out.println("UnitSuperClass 自定义变量值superTestIntVal: " + superTestIntVal);
        System.out.println("UnitSuperClass 构造函数");
    }


    public int  superTestIntVal = 2;
}

package com.example.wangqiubo.Units;

/**
 * Created by wangqiubo on 2017/5/12.
 */

public class UnitSubClass extends UnitSuperClass {
    static {
        System.out.println("subclass init");
    }

    public UnitSubClass(){
        System.out.println("UnitSubClass 自定义变量值subTestIntVal: " + subTestIntVal);
        System.out.println("UnitSubClass 构造函数");
    }

    public int subTestIntVal = 2;

}

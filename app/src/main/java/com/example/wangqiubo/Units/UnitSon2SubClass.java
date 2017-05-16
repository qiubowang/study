package com.example.wangqiubo.Units;

/**
 * Created by wangqiubo on 2017/5/12.
 */

public class UnitSon2SubClass extends UnitSubClass {
    public static UnitSon2SubClass unitSon2SubClass = new UnitSon2SubClass();
    public static int son2SubValOne = 5;
    public static int son2SubValTwo;

    public static final int  UnitSon2SubIntVal = 3;

    static {
        System.out.println("UnitSon2SubClass init");
        System.out.println("UnitSon2SubIntVal: " + UnitSon2SubIntVal);
        System.out.println("静态块中输出son2SubValOne: " + son2SubValOne);
        System.out.println("静态块中输出son2SubValTwo: " + son2SubValTwo);
    }

    public static final int  UnitSon2SubIntValAfterStaticFile = 4;

    public UnitSon2SubClass(){
        son2SubValOne++;
        son2SubValTwo++;
        System.out.println("构造函数中输出son2SubValOne: " + son2SubValOne);
        System.out.println("构造函数中输出son2SubValTwo: " + son2SubValTwo);

        System.out.println("UnitSon2SubClass 自定义变量值sub2SonTestIntVal: " + sub2SonTestIntVal);
        System.out.println("UnitSon2SubClass 构造函数");
    }

    public static UnitSuperClass getInstance(){
        return unitSon2SubClass;
    }

    public int sub2SonTestIntVal = 2;
}

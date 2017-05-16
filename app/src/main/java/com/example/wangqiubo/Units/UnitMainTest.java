package com.example.wangqiubo.Units;

/**
 * Created by wangqiubo on 2017/5/12.
 */

public class UnitMainTest {
    public static void main(String[] args) {

//        System.out.println(UnitSubClass.value);// 子类调用父亲类静态成员
//       System.out.println(UnitSon2SubClass.son2SubValOne);// 类调用自己的静态成员
//        UnitSubClass[] sca = new UnitSubClass[10];// 创建数组
        UnitSon2SubClass.getInstance();

//        UnitSon2SubClass unitSon2SubClass = new UnitSon2SubClass();

    }
}

package com.example.wangqiubo.Units;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by wangqiubo on 2017/6/20.
 */

public class Java8 {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String args[]){
        //拉姆表达式，列表循环
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");
        stringList.add("four");
        stringList.stream().forEach(n -> System.out.print(n));
//        stringList.forEach(n -> System.out.print(n);


    }

    public void aa(){
        new Thread(() -> System.out.print(false));
    }
}

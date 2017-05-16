// IUserAidlInterface.aidl
package com.example.wangqiubo.myaidl;
import com.example.wangqiubo.myaidl.User;

// Declare any non-default types here with import statements

interface IUserAidlInterface {
    void addUser(in User user);
    List<User> getUsers();
}

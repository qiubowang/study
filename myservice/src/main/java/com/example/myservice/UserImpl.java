package com.example.myservice;

import android.os.RemoteException;

import com.example.wangqiubo.myaidl.IUserAidlInterface;
import com.example.wangqiubo.myaidl.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class UserImpl extends IUserAidlInterface.Stub {
    List<User> userList = new ArrayList<>();
    @Override
    public void addUser(User user) throws RemoteException {
        userList.add(user);
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return userList;
    }
}

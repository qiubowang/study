package com.example.wangqiubo.myapplication;

import android.os.RemoteException;

import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBookCallBackInterface;
import com.example.wangqiubo.myaidl.IBooksAidlInterface;

import java.util.List;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class BookImpl extends IBooksAidlInterface.Stub {
    @Override
    public List<Book> getBookList() throws RemoteException {
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

    }

    @Override
    public void registerBookEvent(IBookCallBackInterface bookEvent) throws RemoteException {

    }

    @Override
    public void unregisterBookEvent(IBookCallBackInterface bookEvent) throws RemoteException {

    }
}

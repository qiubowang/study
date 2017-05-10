// IBookCallBackInterface.aidl
package com.example.wangqiubo.myaidl;

import com.example.wangqiubo.myaidl.Book;

// Declare any non-default types here with import statements

interface IBookCallBackInterface {
    void newBookNotification(in Book newBook);
}

// IBooksAidlInterface.aidl
package com.example.wangqiubo.myaidl;
import com.example.wangqiubo.myaidl.Book;
import com.example.wangqiubo.myaidl.IBookCallBackInterface;


interface IBooksAidlInterface {
        List<Book> getBookList();
        void addBook(in Book book);
        void registerBookEvent(IBookCallBackInterface bookEvent);
        void unregisterBookEvent(IBookCallBackInterface bookEvent);
}

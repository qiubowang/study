// IBooksAidlInterface.aidl
package com.example.wangqiubo.myaidl;
import com.example.wangqiubo.myaidl.Book;

// Declare any non-default types here with import statements

interface IBooksAidlInterface {
        List<Book> getBookList();
        void addBook(in Book book);
}

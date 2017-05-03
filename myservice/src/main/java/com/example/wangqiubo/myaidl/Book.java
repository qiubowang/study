package com.example.wangqiubo.myaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangqiubo on 2017/5/1.
 */

public class Book implements Parcelable {
    private String mBookName;
    private String mBookCatagery;
    private String mBookISO;
    public Book(String bookName, String bookCatagery, String bookISO){
        this.mBookName = bookName;
        this.mBookCatagery = bookCatagery;
        this.mBookISO = bookISO;
    }

    protected Book(Parcel in) {
        mBookName = in.readString();
        mBookCatagery = in.readString();
        mBookISO = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookName);
        dest.writeString(mBookCatagery);
        dest.writeString(mBookISO);

    }
}

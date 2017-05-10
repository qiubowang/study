package com.example.wangqiubo.myaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangqiubo on 2017/5/10.
 */

public class BookCallBack implements Parcelable {
    protected BookCallBack(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookCallBack> CREATOR = new Creator<BookCallBack>() {
        @Override
        public BookCallBack createFromParcel(Parcel in) {
            return new BookCallBack(in);
        }

        @Override
        public BookCallBack[] newArray(int size) {
            return new BookCallBack[size];
        }
    };
}

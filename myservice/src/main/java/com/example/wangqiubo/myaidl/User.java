package com.example.wangqiubo.myaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangqiubo on 2017/5/17.
 */

public class User implements Parcelable {
    private String mUserName;
    private int mUserAge;
    private String mSex;
    protected User(Parcel in) {
        mUserName = in.readString();
        mUserAge = in.readInt();
        mSex = in.readString();

    }

    public User(String name, int age, String sex){
        mUserName = name;
        mUserAge = age;
        mSex = sex;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
        dest.writeInt(mUserAge);
        dest.writeString(mSex);
    }
}

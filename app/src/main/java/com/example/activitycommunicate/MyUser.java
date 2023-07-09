package com.example.activitycommunicate;

import android.os.Parcel;
import android.os.Parcelable;

public class MyUser implements Parcelable {

    private String account;

    private String password;

    private Integer age;

    private String phone;

    public MyUser(String account, String password, Integer age, String phone) {
        this.account = account;
        this.password = password;
        this.age = age;
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public MyUser() {
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeValue(this.age);
        dest.writeString(this.phone);
    }

    public void readFromParcel(Parcel source) {
        this.account = source.readString();
        this.password = source.readString();
        this.age = (Integer) source.readValue(Integer.class.getClassLoader());
        this.phone = source.readString();
    }

    protected MyUser(Parcel in) {
        this.account = in.readString();
        this.password = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
    }

    public static final Creator<MyUser> CREATOR = new Creator<MyUser>() {
        @Override
        public MyUser createFromParcel(Parcel source) {
            return new MyUser(source);
        }

        @Override
        public MyUser[] newArray(int size) {
            return new MyUser[size];
        }
    };
}

package com.example.activitycommunicate;

import android.app.Application;
import android.os.Parcel;

public class MyApplication extends Application {

    private MyUser myUser;

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }
}

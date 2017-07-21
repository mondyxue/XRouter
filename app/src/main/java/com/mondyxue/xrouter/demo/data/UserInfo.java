package com.mondyxue.xrouter.demo.data;

import java.io.Serializable;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
public class UserInfo implements Serializable{

    private long userId;

    public long getUserId(){
        return userId;
    }
    public void setUserId(long userId){
        this.userId = userId;
    }

    @Override public String toString(){
        return "UserInfo{userId=" + getUserId() + "}";
    }

}

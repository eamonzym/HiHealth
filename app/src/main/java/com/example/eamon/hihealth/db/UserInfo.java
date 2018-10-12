package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/3/31.
 * 用户信息表
 */

public class UserInfo implements Serializable{
    private String _id;
    private String username;
    private String useremail;
    private String userpassword;
    private String usertarget;
    private String usersex;
    private Date userbirth;
    private Number userheight;
    private Number userweight;
    private String usersign;
    private String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private Date nowtime;

    public Date getNowtime() {
        return nowtime;
    }

    public void setNowtime(Date nowtime) {
        this.nowtime = nowtime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsertarget() {
        return usertarget;
    }

    public void setUsertarget(String usertarget) {
        this.usertarget = usertarget;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public Date getUserbirth() {
        return userbirth;
    }

    public void setUserbirth(Date userbirth) {
        this.userbirth = userbirth;
    }

    public Number getUserheight() {
        return userheight;
    }

    public void setUserheight(Number userheight) {
        this.userheight = userheight;
    }

    public Number getUserweight() {
        return userweight;
    }

    public void setUserweight(Number userweight) {
        this.userweight = userweight;
    }

    public String getUsersign() {
        return usersign;
    }

    public void setUsersign(String usersign) {
        this.usersign = usersign;
    }
}

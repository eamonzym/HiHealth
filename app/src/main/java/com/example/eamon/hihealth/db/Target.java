package com.example.eamon.hihealth.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 健康目标信息
 * 和目标记录是
 * 1 对 多
 * 健康目标信息
 * 和目标类型是
 * 多 对 1
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Target implements Serializable{
   private String _id;
   private String targetname;
   private Number initialweight;
   private Number targetweight;
   private Number daylossweight;
   private Date targetfinishtime;
   private Date targetcreatetime;
   private Date targetstarttime;
   private String targetstate;
   private String targetnote;
   public String targettype;
   private String username;
   private String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    public Target(){

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

    public String getTargettype() {
        return targettype;
    }

    public void setTargettype(String targettype) {
        this.targettype = targettype;
    }

    public String getTargetname() {
        return targetname;
    }

    public void setTargetname(String targetname) {
        this.targetname = targetname;
    }

    public Number getInitialweight() {
        return initialweight;
    }

    public void setInitialweight(Number initialweight) {
        this.initialweight = initialweight;
    }

    public Number getTargetweight() {
        return targetweight;
    }

    public void setTargetweight(Number targetweight) {
        this.targetweight = targetweight;
    }

    public Number getDaylossweight() {
        return daylossweight;
    }

    public void setDaylossweight(Number daylossweight) {
        this.daylossweight = daylossweight;
    }

    public Date getTargetfinishtime() {
        return targetfinishtime;
    }

    public void setTargetfinishtime(Date targetfinishtime) {
        this.targetfinishtime = targetfinishtime;
    }

    public Date getTargetcreatetime() {
        return targetcreatetime;
    }

    public void setTargetcreatetime(Date targetcreatetime) {
        this.targetcreatetime = targetcreatetime;
    }

    public Date getTargetstarttime() {
        return targetstarttime;
    }

    public void setTargetstarttime(Date targetstarttime) {
        this.targetstarttime = targetstarttime;
    }

    public String getTargetstate() {
        return targetstate;
    }

    public void setTargetstate(String targetstate) {
        this.targetstate = targetstate;
    }

    public String getTargetnote() {
        return targetnote;
    }

    public void setTargetnote(String targetnote) {
        this.targetnote = targetnote;
    }
}
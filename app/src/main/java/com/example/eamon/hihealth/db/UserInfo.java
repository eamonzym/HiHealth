package com.example.eamon.hihealth.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/3/31.
 * 用户信息表
 */

public class UserInfo extends DataSupport implements Serializable{

    private int id;
    @Column(unique = true)
    private String user_Id;

    @Column(unique = true)
    private String user_Email;

    private String user_password;

    private String user_name;

    private String user_target;

    private String user_sex;

    private Date user_birth;

    private double user_height;

    private double user_weight;

    // 用来标识用户是新用户还是老用户 新用户为0 老用户为1
    private int sign;

    private List<BodyDataLog> bodyDataLogList = new ArrayList<BodyDataLog>();
    // 外键 身体数据记录信息

    private List<HealthReport> healthReportList = new ArrayList<HealthReport>();
//    @Column(nullable = false)
//    private String user_name;
//
//    @Column(nullable = false)
//    private String user_Sex;
//
//    @Column(nullable = false)
//    private Date user_Birth;
//
//    @Column(nullable = false)
//    private String user_City;
//
//    private String user_height;
//
//    private String user_weight;

    public List<BodyDataLog> getBodyDataLogList() {
        return DataSupport.where("userinfo_id = ?", String.valueOf(id)).find(BodyDataLog.class);
    }

    public List<HealthReport> getHealthReportList() {
        return DataSupport.where("userinfo_id = ?", String.valueOf(id)).find(HealthReport.class);
    }

    public void setHealthReportList(List<HealthReport> healthReportList) {
        this.healthReportList = healthReportList;
    }

    public void setBodyDataLogList(List<BodyDataLog> bodyDataLogList) {
        this.bodyDataLogList = bodyDataLogList;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_target() {
        return user_target;
    }

    public void setUser_target(String user_target) {
        this.user_target = user_target;
    }

    public Date getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(Date user_birth) {
        this.user_birth = user_birth;
    }

    public double getUser_height() {
        return user_height;
    }

    public void setUser_height(double user_height) {
        this.user_height = user_height;
    }

    public double getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(double user_weight) {

        this.user_weight = user_weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}

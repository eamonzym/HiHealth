package com.example.eamon.hihealth.db;

import java.io.Serializable;

/**
 * 食物信息
 * 和饮食记录是
 * 多 对 多
 * 作者：Created by eamon
 * 时间：  on 2018/3/27.
 */

public class Food implements Serializable{
    private String _id;
    private String foodimage;
    private String foodname;
    private Number foodcalorie;
    private String foodtype;
    private String fooddescription;
    private String foodsuggest;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFoodImag() {
        return foodimage;
    }

    public void setFoodImag(String foodImag) {
        this.foodimage = foodImag;
    }

    public Food(String foodname, Number foodcalorie, String foodsuggest) {
        this.foodname = foodname;
        this.foodcalorie = foodcalorie;
        this.foodsuggest = foodsuggest;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Number getFoodcalorie() {
        return foodcalorie;
    }

    public void setFoodcalorie(Number foodcalorie) {
        this.foodcalorie = foodcalorie;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getFooddescription() {
        return fooddescription;
    }

    public void setFooddescription(String fooddescription) {
        this.fooddescription = fooddescription;
    }

    public String getFoodsuggest() {
        return foodsuggest;
    }

    public void setFoodsuggest(String foodsuggest) {
        this.foodsuggest = foodsuggest;
    }
}

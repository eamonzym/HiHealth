package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.Food;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/21.
 */

public class FoodInfoMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<Food> foodList;
}

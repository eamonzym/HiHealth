package com.example.eamon.hihealth.calculation;

import android.util.Log;

import com.example.eamon.hihealth.db.UserInfo;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/7.
 */

public class BodyDataCaluation {
    private static final String TAG = "BodyDataCaluation";

    private double height;

    private double weight;

    private String sex;

    private int birth;


    public BodyDataCaluation(UserInfo userInfo) {
        height = userInfo.getUser_height();

        weight = userInfo.getUser_weight();

        sex = userInfo.getUser_sex();

        birth = 21;

        Log.d("TAG", "height is " + height + "; weight is " + weight +
            "; sex is " + sex + "; birth is " + birth);

    }

    public BodyDataCaluation(double height, double weight, String sex, int birth) {
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.birth = birth;
    }

    /**
     * 根据用户记录的身高和体重来计算出用户的BMI
     * @return
     */
    public double getBodyMassIndex() {

        double bmiValue = 0;

        bmiValue = weight / Math.pow(height / 100, 2);

        Log.d("TAG", "bmiValue is " + bmiValue);
        return bmiValue;
    }

    /**
     * 根据用户的性别、体重、身高、年龄来计算出用户的BMR 基本代谢率
     * @return
     */
    public double getBasalMetabolicRate() {

        double bmrValue = 0;

        if ("男".equals(sex)) {

            bmrValue = 66 + (13.7 * weight) + (5 * height) - (6.8 * birth);

        } else if ("女".equals(sex)) {

            bmrValue = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * birth);

        }

        Log.d("TAG", "bmrValue is " + bmrValue);
       return bmrValue;
    }

    /**
     * 根据用户的身高然后按照BMI的标准18.5到24之间来计算出健康体重范围
     * 系统根据high 和lower 来得出上下限
     * @param range
     * @return
     */
    public double getWeightRange(String range) {
        double weightRange = 0;

        if ("high".equals(range)) {

            weightRange = Math.pow(height / 100, 2)* 24;
            Log.d("TAG", "high  weightRange is " + weightRange);

        } else if ("lower".equals(range)) {

            weightRange = Math.pow(height / 100, 2) * 18.5;
            Log.d("TAG", "lower weightRange is " + weightRange);
        }



        return weightRange;
    }

    /**
     * 根据 用户的身高 年龄 性别 来作出判断，由三种算法的平均值来计算出用户的
     * 标准体重
     * @return
     */
    public double getStandardweight() {
        // W = L^3 / N(N = 82)
        double wWeight = Math.pow(height / 10, 3) / 82;

        // 身高 - 110
        double weight = height - 110;
        // 成人标准体重
        double standarWeight = 0;

        // 青春期标准体重
        double pubertyWeight;

        if (height > 170)
        {
            pubertyWeight = height - 114 + (170 - height) * 0.2;

        } else {

            pubertyWeight = height -114 + (170 - height) * 0.4;
        }

        if (birth >= 15 && birth <= 19)
        {
                standarWeight = pubertyWeight;

        } else if (birth == 20) {

                standarWeight = pubertyWeight + height / 100 - 1 / 10;

        } else if (birth > 20) {
                int all = 0 ;
                int n = (birth - 20) / 5 + 1;
                for (int i = 1; i <= n; i++)
                {
                    all = all + i;
                }
                standarWeight = pubertyWeight + height * n / 100 - all / 10;
        }

        if ("男".equals(sex))
        {
            return (standarWeight + weight +wWeight) / 3;

        } else if ("女".equals(sex))
        {
            return (standarWeight - 2.5 + weight + wWeight) / 3;
        }

        Log.d("TAG", "standarWeight is " + standarWeight);
        return standarWeight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

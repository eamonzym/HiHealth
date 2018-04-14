package com.example.eamon.hihealth.calculation;

import android.util.Log;

import java.util.Random;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/10.
 */

public class HealthIndexCaluation {

    private static final String TAG = "HealthIndexCaluation";

    private int healthIndex;

    private double bodyMassIndexScore;

    private double WeightRangeScore;

    private double standardWeightScroe;


    /**
     * 根据用户的身体记录的分析的BMI，和用户自己身体重与标准体重和健康体重范围来比较
     * 得出相应的健康指数
     * @param bodyDataCaluation
     * @return 健康指数
     */
    public int getHealthIndex(BodyDataCaluation bodyDataCaluation)
    {
        // 根据BMI的标准来给用户的BMI指数打分
        if (bodyDataCaluation.getBodyMassIndex() < 18.5)
        {
            Random random = new Random();
            bodyMassIndexScore = random.nextInt(75)%(75-66+1) + 66;
        } else if (bodyDataCaluation.getBodyMassIndex() >= 18.5 &&
                bodyDataCaluation.getBodyMassIndex() < 25) {
            Random random = new Random();
            bodyMassIndexScore = random.nextInt(90)%(90-76+1) + 76;
        } else if (bodyDataCaluation.getBodyMassIndex() >= 25 &&
                bodyDataCaluation.getBodyMassIndex() < 30) {
            Random random = new Random();
            bodyMassIndexScore = random.nextInt(65)%(65-55+1) + 55;
        } else if (bodyDataCaluation.getBodyMassIndex() >= 30) {
            Random random = new Random();
            bodyMassIndexScore = random.nextInt(55)%(55-50+1) + 50;
        }
        Log.d("TAG", "bodyMassIndexScore is " + bodyMassIndexScore);
        // 根据健康体重范围的标准来给用户的健康体重范围指数打分
        if (bodyDataCaluation.getWeight() >= bodyDataCaluation.getWeightRange("lower") &&
                bodyDataCaluation.getWeight() <= bodyDataCaluation.getWeightRange("high")){
            Random random = new Random();
            WeightRangeScore = random.nextInt(85)%(85-75+1) + 75;
        } else {
            Random random = new Random();
            WeightRangeScore = random.nextInt(70)%(70-60+1) + 60;
        }
        Log.d("TAG", "WeightRangeScore is " + WeightRangeScore);

        // 根据标准体重的标准来给用户的标准体重指数打分
        if (Math.abs(bodyDataCaluation.getWeight() - bodyDataCaluation.getStandardweight()) >= 5 &&
                Math.abs(bodyDataCaluation.getWeight() - bodyDataCaluation.getStandardweight())  <=10) {
            Random random = new Random();
            standardWeightScroe = random.nextInt(75)%(75-65+1) + 65;
        } else if (Math.abs(bodyDataCaluation.getWeight() - bodyDataCaluation.getStandardweight()) >= 0 &&
                Math.abs(bodyDataCaluation.getWeight() - bodyDataCaluation.getStandardweight())  <5) {
            Random random = new Random();
            standardWeightScroe = random.nextInt(85)%(85-76+1) + 76;
        } else {
            Random random = new Random();
            standardWeightScroe = random.nextInt(65)%(65-60+1) + 60;
        }
        Log.d("TAG", "standardWeightScroe is " + standardWeightScroe );
        // 最后的健康的健康指数为三个指标指数的平均值
        healthIndex = (int)( standardWeightScroe + WeightRangeScore + bodyMassIndexScore ) / 3 ;

        Log.d("TAG", "healthIndex is " + healthIndex );
        return healthIndex;
    }

    /**
     * 根据用户的健康指数得分来评级，给出相应的等级
     * @return String grade(A,B,C,D,E)
     */
    public String getGrade()
    {
        String grade = "";

        if (healthIndex >= 80 && healthIndex <= 90) {
            grade = "A";
        } else if (healthIndex >= 76 && healthIndex <80) {
            grade = "B";
        } else if (healthIndex >= 70 && healthIndex <76) {
            grade = "C";
        } else if (healthIndex >= 65 && healthIndex <70) {
            grade = "D";
        } else if (healthIndex >= 60 && healthIndex < 65) {
            grade = "E";
        } else if (healthIndex >=50 && healthIndex < 60) {
            grade = "F";
        }

        return grade;
    }

}

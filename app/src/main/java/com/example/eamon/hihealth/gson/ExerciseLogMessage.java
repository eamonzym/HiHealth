package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.ExerciseLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/1.
 */

public class ExerciseLogMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<ExerciseLog> exerciseLogList;

    public List<Date> exerciseLogTime;

    public StatisValue statisValue;

    public class StatisValue {
        public String min;

        public String avg;

        public String max;
    }

}

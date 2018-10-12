package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.TargetLog;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/16.
 */

public class TargetLogMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<TargetLog> targetLogs;

    public StatisValue statisValue;

    public class StatisValue {
        public String min;

        public String avg;

        public String max;
    }
}

package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.HabitLog;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/20.
 */

public class HabitLogMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<HabitLog> habitLogList;

    public boolean stampe;
}

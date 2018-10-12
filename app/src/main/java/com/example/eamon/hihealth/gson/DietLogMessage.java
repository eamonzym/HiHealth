package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.DietLog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/24.
 */

public class DietLogMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<DietLog> dietLogList;

    public List<Date> dietLogTime;
}

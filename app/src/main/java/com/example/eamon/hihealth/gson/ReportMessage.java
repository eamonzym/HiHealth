package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.UserInfo;

import java.io.Serializable;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/15.
 */

public class ReportMessage implements Serializable {

    public ResponseMessage responseMessage;

    public BodyValueMessage bodyDataValue;

    public String report;

    public UserInfo userInfo;
}

package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.UserInfo;

import java.io.Serializable;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/9.
 */

public class UserInfoMessage implements Serializable {
    public ResponseMessage responseMessage;

    public UserInfo userInfo;
}

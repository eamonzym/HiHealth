package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.Target;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/14.
 */

public class TargetInfoMessage implements Serializable {
    public ResponseMessage responseMessage;

    public List<Target> targetList;

    public Target ongoingtarget;

}

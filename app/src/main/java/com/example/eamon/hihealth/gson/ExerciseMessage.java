package com.example.eamon.hihealth.gson;

import com.example.eamon.hihealth.db.Exercise;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/1.
 */

public class ExerciseMessage implements Serializable {

    public ResponseMessage responseMessage;

    public List<Exercise> exerciseList;
}

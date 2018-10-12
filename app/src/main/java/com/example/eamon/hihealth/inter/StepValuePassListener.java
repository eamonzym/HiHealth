package com.example.eamon.hihealth.inter;

/**
 * 当步数是有效的时候，对其步数数据进行改变
 * 作者：Created by eamon
 * 时间：  on 2018/4/15.
 */

public interface StepValuePassListener {
    public void stepChanged(int steps);
}

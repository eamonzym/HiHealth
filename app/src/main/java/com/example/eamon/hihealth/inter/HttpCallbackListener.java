package com.example.eamon.hihealth.inter;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/21.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}

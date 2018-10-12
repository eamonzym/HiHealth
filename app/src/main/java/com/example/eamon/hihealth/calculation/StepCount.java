package com.example.eamon.hihealth.calculation;

import com.example.eamon.hihealth.inter.StepCountListener;
import com.example.eamon.hihealth.inter.StepValuePassListener;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/15.
 */

public class StepCount implements StepCountListener {
    private int count = 0;
    private int mCount = 0;
    private StepValuePassListener mStepValuePassListener;
    private long timeOfLastPeak = 0;
    private long timeOfThisPeak = 0;

    /**
     * 连续走十步才会开始记步数
     * 连续走了9步以下，停留超过3秒计数清空
     */
    @Override
    public void countStep() {
        this.timeOfLastPeak = this.timeOfThisPeak;
        this.timeOfThisPeak = System.currentTimeMillis();
        // 如果停留时间小于三秒则开始计数
        if (this.timeOfThisPeak - this.timeOfLastPeak <= 3000L) {
            // 如果计数小于9则继续累加
            if (this.count < 9) {
                this.count++;
                // 如果等于9继续累加且进入记步状态
            } else if (this.count == 9) {
                this.count++;
                this.mCount += this.count;
                notifyListener();
                // 大于9
            } else {
                this.mCount++;
                notifyListener();
            }
            // 超过3秒之后 置1？？？
        } else {
            this.count = 1;
        }
    }

   public void initListener(StepValuePassListener listener) {
        this.mStepValuePassListener = listener;
   }

   public void notifyListener () {
        if (this.mStepValuePassListener != null)
            this.mStepValuePassListener.stepChanged(this.mCount);
   }

   public void setSteps(int initValue) {
        this.mCount = initValue;
        this.count = 0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
   }

}

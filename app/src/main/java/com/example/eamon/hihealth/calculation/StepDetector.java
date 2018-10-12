package com.example.eamon.hihealth.calculation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.eamon.hihealth.inter.StepCountListener;

/**
 *  判断当前的事件是否可作为步点
 * 作者：Created by eamon
 * 时间：  on 2018/4/15.
 */

public class StepDetector implements SensorEventListener {
    // 存放三轴数据（X,Y,Z）
    float [] oriValues = new float[3];
    final  int ValueNum = 4;
    // 用于存放计算阈值的波峰波谷差值
    float [] tempValue = new float[ValueNum];
    int tempCount = 0;
    // 是否上升的标志位
    boolean isDirectionUp = false;
    // 持续上升次数
    int continueUpCount = 0;
    // 上一点的持续上升的次数，为了记录波峰的上升次数
    int continueUpFormerCount = 0;
    // 上一点的状态，上升还是下降
    boolean lastStatus = false;
    // 波峰值
    float peakOfWave = 0;
    // 波谷值
    float valleyOfWave = 0;
    // 此次波峰的时间
    long timeOfThisPeak = 0;
    // 上次波峰的时间
    long timeOfLastPeak = 0;
    // 当前的时间
    long timeOfNow = 0;
    // 当前传感器的值
    float gravityNew = 0;
    // 上次传感器的值
    float gravityOld = 0;
    // 动态阈值需要动态的数据，这个值用于这些动态数据的阈值
    final float InitialValue = (float) 1.3;
    // 初始阈值
    float ThreadValue = (float) 2.0;
    // 波峰波谷时间差
    int TimeInterval = 250;

    private StepCountListener mStepListeners;

    @Override
    /**
     * 继承自SensorEventListener
     * 实现onSensorChanged方法 和 onAccuracyChanged方法
     * onSensorChanged方法是 感应信息变化时触发，当从具有完全相同传感器值（时间戳更新）
     * 的传感器获取新读数时，也会触发
     * onAccuracyChanged方法是 当注册传感器的精度发生变化时，触发
     */
    public void onSensorChanged(SensorEvent event) {
        for (int i = 0; i < 3; i++) {
            oriValues[i] = event.values[i];
        }
        // 获取的值是 ？ ？？
        gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
                + oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
        detectorNewStep(gravityNew);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // 初始化步数统计
    public void initListener(StepCountListener listener) {
        this.mStepListeners = listener;
    }

    /**
     * 检测步数，并开始记步
     * 传入sersor中的数据
     * 如果检测到了波峰，并且符合时间差以及阈值的条件，则判断为一步
     * 符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
     * @param values
     */
    public void detectorNewStep(float values) {
        if (gravityOld == 0) {
            gravityOld = values;
        } else {
            if (detectorPeak(values, gravityOld)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= ThreadValue)) {
                    timeOfThisPeak = timeOfNow;

                    /**
                     *  更新界面的处理，不涉及到算法
                     *  在更新界面之前，有如下处理，处理无效的运动，不更新页面的步数
                     *  连续记录10才开始刷新步数
                     *  如记录了9步而用户当前没有走路超过了3秒，则前面的9步失效，且不会刷新页面的步数
                     *  连续记录了9步之后，用户继续运动，之前的数据才有效
                     */
                    mStepListeners.countStep();
                }

                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= InitialValue)) {
                    timeOfLastPeak = timeOfNow;
                    ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
                }
            }
        }

        gravityOld = values;
    }

    /**
     * 检测波峰
     * 以下四个条件判断为波峰：
     * 目前点为下降趋势：isDirectionUp为false
     * 之前的点为上升的趋势：lastStatus为true
     * 到波峰为止持续上升大于等于两次（？？）
     * 波峰值大于20
     *
     * 记录波谷值
     * 观察波形图，可以发现在出现步子的地方，波谷的下一个就是波峰，有比较明显的特征以及差值
     * 要记录每次的波谷值，为了和下次的波峰做对比
     * @param newValue
     * @param oldValue
     * @return
     */
    public boolean detectorPeak(float newValue, float oldValue) {

        lastStatus =isDirectionUp;

        /**
         * 比较当前值和上一次的值
         * 如果当前值比上一次的值大 则还是属于上升状态 增加连续上升次数
         * 否则变为下降状态 连续上升次数置为零
         */
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        /**
         * 当 当前状态为下降，上一次状态为上升
         * 且 上一点的持续上升次数大于等于两次 或者 波峰值大于20
         * 满足时 则达到波峰
         *
         * 否则当上一次状态为下降 当前状态为上升的时候
         * 是波谷 不满足
         */
        if (!isDirectionUp && lastStatus
                && (continueUpFormerCount >=2 || oldValue >= 20)) {
            peakOfWave = oldValue;
            return  true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    /**
     * 阈值的计算
     * 通过波峰波谷的差值计算阈值
     * 记录4个值，存入tempValue数组中
     * 将数组传入函数averageValue中计算阈值
     * @param value
     * @return
     */
    public float peakValleyThread(float value) {
        float tempThread = ThreadValue;
        /**
         * 如果当前记录了四个值则传入averageValue计算阈值
         * 否则继续记录
         */
        if (tempCount < ValueNum) {
            tempValue[tempCount] = value;
            tempCount++;
        } else {
            tempThread = averageValue(tempValue, ValueNum);
            for (int i = 1; i < ValueNum; i++) {
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[ValueNum - 1] = value;

        }
        return  tempThread;
    }

    /**
     * 梯度化阈值
     * 计算数组的平均值
     * 通过平均值将阈值梯度化在一个范围里
     * @param value
     * @param n
     * @return
     */
    public float averageValue(float value[], int n) {
        float ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / ValueNum;
        // ?? 范围的确定
        if (ave >= 8)
            ave = (float) 4.3;
        else if (ave >= 7 && ave < 8)
            ave = (float) 3.3;
        else if (ave >=4 && ave<7)
            ave = (float) 2.3;
        else if (ave >=3 && ave < 4)
            ave = (float) 2.0;
        else {
            ave = (float) 1.3;
        }
        return ave;
    }

}

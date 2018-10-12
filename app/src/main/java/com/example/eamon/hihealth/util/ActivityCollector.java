package com.example.eamon.hihealth.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/9.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}

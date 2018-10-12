package com.example.eamon.hihealth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/15.
 */

public class DateFormatUtil {

    public static String dateChangeUtil(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    public static Date stringToDate(String strDate) throws Exception{
        return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
    }

    public static String getDateStr(String day, long dayAddNum) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    public static String getDateNow() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}

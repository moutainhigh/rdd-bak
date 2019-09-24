package com.cqut.czb.bn.entity.dto.food.AppOrderPage;

import java.util.Calendar;
import java.util.Date;

/**
 * 对时间进行处理
 */
public class DamaiDay {

    public static String getDamaiDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "1";
            case Calendar.TUESDAY:
                return "2";
            case Calendar.WEDNESDAY:
                return "3";
            case Calendar.THURSDAY:
                return "4";
            case Calendar.FRIDAY:
                return "5";
            case Calendar.SATURDAY:
                return "6";
            case Calendar.SUNDAY:
                return "7";
            default:
                return "";
        }
    }

}

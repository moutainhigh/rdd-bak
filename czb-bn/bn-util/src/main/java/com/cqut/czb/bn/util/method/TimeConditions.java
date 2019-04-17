package com.cqut.czb.bn.util.method;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConditions {
    public static OpinionScreenDisplayConditions getTimeConditions(Integer dateType){
        /** 设置日期格式以便下面第二个选择语句设置时间 **/
        SimpleDateFormat day = new SimpleDateFormat("y-MM-dd HH:mm:ss");//设置日期格式为天,大写的H为24小时制，小写为12
        SimpleDateFormat day1 = new SimpleDateFormat("y-MM-dd");//设置日期格式为天
        SimpleDateFormat month = new SimpleDateFormat("y-MM");//设置日期格式为月
        SimpleDateFormat year = new SimpleDateFormat("y");//设置日期格式为天年

        OpinionScreenDisplayConditions conditions = new OpinionScreenDisplayConditions();// 大屏显示的条件实体类
        conditions.setDateType(dateType);// 设置条件实体类中的分时类型

        /** 选择语句计算本季度的开始日期 **/
        SimpleDateFormat monthQuarter = new SimpleDateFormat("MM");//设置日期格式为月
        String quarter = new String();  // 设置季度时间
        String endDay =new String();    // 设置月份的endDay
        String endDay2 = new String();  //  判断下平年或闰年，将设置2月的endDay

        Date date = new Date(); //new一个本地时间（因服务器比本地时间少八小时，所以加8小时的毫秒）
        date.setTime(date.getTime() + 28800000);
        if(Integer.parseInt(year.format(date))%4 == 0 &&  Integer.parseInt(year.format(date))%100 != 0 && Integer.parseInt(year.format(date))%400 == 0){
            endDay2 = endDay2 + "-29";
        }
        else{
            endDay2 = endDay2 + "-28";
        }
        switch (monthQuarter.format(date)){
            case "01": quarter = "01";endDay = "-31";break;
            case "02": quarter = "01";endDay = endDay + endDay2;break;
            case "03": quarter = "01";endDay = "-31";break;
            case "04": quarter = "04";endDay = "-30";break;
            case "05": quarter = "04";endDay = "-31";break;
            case "06": quarter = "04";endDay = "-30";break;
            case "07": quarter = "07";endDay = "-31";break;
            case "08": quarter = "07";endDay = "-31";break;
            case "09": quarter = "07";endDay = "-30";break;
            case "10": quarter = "10";endDay = "-31";break;
            case "11": quarter = "10";endDay = "-30";break;
            case "12": quarter = "10";endDay = "-31";
        }

        /** 选择语句设置条件实体类中的时间条件 **/
        switch (dateType){
            case 0: conditions.setDateStart(day1.format(date));conditions.setDate(day1.format(date) +
                    " 23:59:59");
                break;
            case 1: conditions.setDate(month.format(date) +
                    endDay);conditions.setDateMonth(month.format(date));
                break;
            case 2: conditions.setDate(year.format(date) +
                    "-" +
                    String.format("%02d",(int)(Integer.parseInt(quarter) + 2)) +
                    getEndDay(String.format("%02d",(int)(Integer.parseInt(quarter) + 2))) +
                    " 23:59:59");
                conditions.setDateQuarter(year.format(date) + "-" + quarter);
                break;
            case 3: conditions.setDate(year.format(date) +
                    "-12-31 23:59:59");
                conditions.setDateYear(year.format(date));
        }
        return conditions;
    }

    /** 获得一个月的截止日期，只用于设置实体类中的时间条件为季度日期时 **/
    public static String getEndDay(String month){
        SimpleDateFormat year = new SimpleDateFormat("y");//设置日期格式为天年

        Date date = new Date(); //new一个本地时间（因服务器比本地时间少八小时，所以加8小时的毫秒）
        date.setTime(date.getTime() + 28800000);
        String endDay2 = new String();
        if(Integer.parseInt(year.format(date))%4 == 0 &&  Integer.parseInt(year.format(date))%100 != 0 && Integer.parseInt(year.format(date))%400 == 0){
            endDay2 = endDay2 + "-29";
        }
        else{
            endDay2 = endDay2 + "-28";
        }
        String endDay = new String();
        switch (month){
            case "01": endDay = "-31";break;
            case "02": endDay = endDay + endDay2;break;
            case "03": endDay = "-31";break;
            case "04": endDay = "-30";break;
            case "05": endDay = "-31";break;
            case "06": endDay = "-30";break;
            case "07": endDay = "-31";break;
            case "08": endDay = "-31";break;
            case "09": endDay = "-30";break;
            case "10": endDay = "-31";break;
            case "11": endDay = "-30";break;
            case "12": endDay = "-31";break;
        }
        return  endDay;
    }
}

package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.CareerStatisticsMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.CareerStatisticsDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.OrdinaryUserDirectDTO;
import com.cqut.czb.bn.entity.entity.UserRole;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.CareerStatisticsService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CareerStatisticsServiceImpl implements CareerStatisticsService {
    @Autowired
    CareerStatisticsMapperExtra mapperExtra;

    @Override
    public JSONResult statistics(String userId) {
        //统计收益信息
        CareerStatisticsDTO firstData = mapperExtra.statistics(userId);
        //统计人数信息
        CareerStatisticsDTO secondData = mapperExtra.getUsers(userId);

        //因为返回结果frstData，所以将secondData数据设置到firstData中
        if( null != secondData.getPartnerCount()) {
            firstData.setPartnerCount(secondData.getPartnerCount());
        } else {
            firstData.setPartnerCount(0);
        }

        if( null != secondData.getOrdinaryUserCount())
            firstData.setOrdinaryUserCount(secondData.getOrdinaryUserCount());
        else {
            firstData.setOrdinaryUserCount(0);
        }

        if( null != secondData.getDirectVipCount()) {
            firstData.setDirectVipCount(secondData.getDirectVipCount());
        } else {
            firstData.setDirectVipCount(0);
        }

        if( null != secondData.getIndirectVipCount()) {
            firstData.setIndirectVipCount(secondData.getIndirectVipCount());
        } else {
            firstData.setIndirectVipCount(0);
        }

        return new JSONResult("统计数据查询成功", 200, firstData);
    }

    @Override
    public JSONResult getDirectAndIndirectIncome(int type, String userId) {
        List<DirectAndIndirectDTO> result = new ArrayList<>();
        if(type == 0) {
            result = getTime(Calendar.DATE, result, userId);
        } else if(type == 1){
            result = getTime(Calendar.MONTH, result, userId);
        } else if(type == 2) {
            result = getTime(Calendar.YEAR, result, userId);
        }

        if(result.size() != 0){
            result.get(0).setAllDirectVipIncome(mapperExtra.getAllVipDirectIncome(userId));
        }

        return new JSONResult("数据查询成功", 200, result);
    }

    @Override
    public JSONResult getOrdinaryDirectNum(int type, String userId) {
        List<OrdinaryUserDirectDTO> result = new ArrayList<>();
        if(type == 0) {
            result = getOrdinaryUserDirectDTO(Calendar.DATE, result, userId);
        } else if(type == 1){
            result = getOrdinaryUserDirectDTO(Calendar.MONTH, result, userId);
        } else if(type == 2) {
            result = getOrdinaryUserDirectDTO(Calendar.YEAR, result, userId);
        }

        return new JSONResult("数据查询成功", 200, result);
    }

    private List<DirectAndIndirectDTO> getTime(int type, List<DirectAndIndirectDTO> result, String userId) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        SimpleDateFormat day = null;//设置日期格式为天,大写的H为24小时制，小写为12
        String startTime = "";
        String endTime = "";
        String startTime2 = "";
        String endTime2 = "";
        String startTime3 = "";
        String endTime3 = "";
        if(type == Calendar.DATE) {
            day = new SimpleDateFormat("y-MM-dd");
            startTime = day.format(calendar.getTime()) + " 00:00:00";
            endTime = day.format(calendar.getTime()) + " 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + " 00:00:00";
            endTime2 = day.format(calendar.getTime()) + " 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + " 00:00:00";
            endTime3 = day.format(calendar.getTime()) + " 23:59:59";
        } else if(type == Calendar.MONTH){
            day = new SimpleDateFormat("y-MM");
            startTime = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime2 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime3 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";
        } else if(type == Calendar.YEAR){
            day = new SimpleDateFormat("y");
            startTime = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime2 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime3 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";
        }

        System.out.println("现：" + startTime + endTime);
        DirectAndIndirectDTO todayData = mapperExtra.getDirectAndIndirectIncome(userId, startTime, endTime);
        result.add(todayData);

        DirectAndIndirectDTO yesterdayData = mapperExtra.getDirectAndIndirectIncome(userId, startTime2, endTime2);
        result.add(yesterdayData);
        System.out.println("前：" + startTime2 + endTime2);

        DirectAndIndirectDTO theDayBeforeYesterdayData = mapperExtra.getDirectAndIndirectIncome(userId, startTime3, endTime3);
        result.add(theDayBeforeYesterdayData);
        System.out.println("前前：" + startTime3 + endTime3);

        return  result;
    }

    private List<OrdinaryUserDirectDTO> getOrdinaryUserDirectDTO(int type, List<OrdinaryUserDirectDTO> result, String userId) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        SimpleDateFormat day = null;//设置日期格式为天,大写的H为24小时制，小写为12
        String startTime = "";
        String endTime = "";
        String startTime2 = "";
        String endTime2 = "";
        String startTime3 = "";
        String endTime3 = "";
        if(type == Calendar.DATE) {
            day = new SimpleDateFormat("y-MM-dd");
            startTime = day.format(calendar.getTime()) + " 00:00:00";
            endTime = day.format(calendar.getTime()) + " 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + " 00:00:00";
            endTime2 = day.format(calendar.getTime()) + " 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + " 00:00:00";
            endTime3 = day.format(calendar.getTime()) + " 23:59:59";
        } else if(type == Calendar.MONTH){
            day = new SimpleDateFormat("y-MM");
            startTime = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime2 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + "-01 00:00:00";
            endTime3 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + " 23:59:59";
        } else if(type == Calendar.YEAR){
            day = new SimpleDateFormat("y");
            startTime = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";

            calendar.add(type, -1);
            startTime2 = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime2 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";

            calendar.add(type, -1);
            startTime3 = day.format(calendar.getTime()) + "-01-01 00:00:00";
            endTime3 = day.format(calendar.getTime()) + "-" + (calendar.getActualMaximum(Calendar.MONTH) + 1) + "-31 23:59:59";
        }

        System.out.println("现：" + startTime + endTime);
        OrdinaryUserDirectDTO todayData = mapperExtra.getOrdinaryDirectNum(userId, startTime, endTime);
        result.add(todayData);

        OrdinaryUserDirectDTO yesterdayData = mapperExtra.getOrdinaryDirectNum(userId, startTime2, endTime2);
        result.add(yesterdayData);
        System.out.println("前：" + startTime2 + endTime2);

        OrdinaryUserDirectDTO theDayBeforeYesterdayData = mapperExtra.getOrdinaryDirectNum(userId, startTime3, endTime3);
        result.add(theDayBeforeYesterdayData);
        System.out.println("前前：" + startTime3 + endTime3);

        return  result;
    }

    @Override
    public JSONResult getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers) {
        CareerStatisticsDTO numberOfDevelopment = mapperExtra.getNumberOfDevelopment(statisticsDevelopmentNumbers);
        return new JSONResult("发展人数数据查询成功", 200, numberOfDevelopment);
    }

    @Override
    public JSONResult initPermission() {
        List<UserRole> firsts = mapperExtra.selectFirstUser(2);

        for(UserRole data: firsts){
            data.setId(StringUtil.createId());
        }
        mapperExtra.updateLoginPc(firsts);
        mapperExtra.insertPermission(firsts);

        List<UserRole> seconds = mapperExtra.selectFirstUser(1);

        for(UserRole data: seconds){
            data.setId(StringUtil.createId());
        }

        mapperExtra.updateLoginPc(seconds);
        mapperExtra.insertPermission(seconds);

        return new JSONResult("true");
    }
}

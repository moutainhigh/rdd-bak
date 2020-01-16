package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.ManagerStaticsMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.CareerStatisticsDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.DirectAndIndirectDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.ManagerStaticsDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.StatisticsDevelopmentNumbers;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.ManagerStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ManagerStaticsServiceImpl implements ManagerStaticsService {

    @Autowired
    ManagerStaticsMapperExtra managerStaticsMapperExtra;
    @Override
    public JSONResult statistics() {

        //统计收益信息
        ManagerStaticsDTO firstData = managerStaticsMapperExtra.statistics();
        //统计人数信息
        ManagerStaticsDTO secondData = managerStaticsMapperExtra.getUsers();

        //因为返回结果firstData，所以将secondData数据设置到firstData中
        if(secondData.getOrdinaryPartner() != null) {
            firstData.setOrdinaryPartner(secondData.getOrdinaryPartner());
        } else {
            firstData.setOrdinaryPartner(0);
        }

        if(secondData.getOrdinaryUser() != null) {
            firstData.setOrdinaryUser(secondData.getOrdinaryUser());
        }
        else {
            firstData.setOrdinaryUser(0);
        }

        if(secondData.getBusinessPartner() != null) {
            firstData.setBusinessPartner(secondData.getBusinessPartner());
        } else {
            firstData.setBusinessPartner(0);
        }

        if(secondData.getUserCount() != null) {
           firstData.setUserCount(secondData.getUserCount());
        } else {
            firstData.setUserCount(0);
        }

        if(secondData.getVipUser() != null) {
            firstData.setVipUser(secondData.getVipUser());
        } else {
            firstData.setVipUser(0);
        }

        if(secondData.getDirectVipCount() != null) {
            firstData.setDirectVipCount(secondData.getDirectVipCount());
        } else {
            firstData.setDirectVipCount(0);
        }

        if(secondData.getIndirectVipCount() != null) {
            firstData.setIndirectVipCount(secondData.getIndirectVipCount());
        } else {
            firstData.setIndirectVipCount(0);
        }
        return new JSONResult("统计数据查询成功", 200, firstData);
    }


    @Override
    public JSONResult getNumberOfDevelopment(StatisticsDevelopmentNumbers statisticsDevelopmentNumbers){
        ManagerStaticsDTO numberOfDevelopment = managerStaticsMapperExtra.getNumberOfDevelopment(statisticsDevelopmentNumbers);
        return new JSONResult("发展人数数据查询成功", 200, numberOfDevelopment);
    }

    @Override
    public JSONResult getDirectAndIndirectIncome(int type) {
        List<DirectAndIndirectDTO> result = new ArrayList<>();
        if(type == 0) {
            result = getTime(Calendar.DATE, result);
        } else if(type == 1){
            result = getTime(Calendar.MONTH, result);
        } else if(type == 2) {
            result = getTime(Calendar.YEAR, result);
        }

        return new JSONResult("数据查询成功", 200, result);
    }

    private List<DirectAndIndirectDTO> getTime(int type, List<DirectAndIndirectDTO> result) {
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
        DirectAndIndirectDTO todayData = managerStaticsMapperExtra.getDirectAndIndirectIncome(startTime, endTime);
        result.add(todayData);

        DirectAndIndirectDTO yesterdayData = managerStaticsMapperExtra.getDirectAndIndirectIncome(startTime2, endTime2);
        result.add(yesterdayData);
        System.out.println("前：" + startTime2 + endTime2);

        DirectAndIndirectDTO theDayBeforeYesterdayData = managerStaticsMapperExtra.getDirectAndIndirectIncome(startTime3, endTime3);
        result.add(theDayBeforeYesterdayData);
        System.out.println("前前：" + startTime3 + endTime3);

        return  result;
    }
}

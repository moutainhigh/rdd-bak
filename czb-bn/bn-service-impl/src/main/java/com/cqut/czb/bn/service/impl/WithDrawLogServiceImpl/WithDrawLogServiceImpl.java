package com.cqut.czb.bn.service.impl.WithDrawLogServiceImpl;

import com.cqut.czb.bn.dao.mapper.IncomeLogMapperExtra;
import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;
import com.cqut.czb.bn.entity.entity.withDrawed.Income;
import com.cqut.czb.bn.entity.entity.withDrawed.IncomeInputDTO;
import com.cqut.czb.bn.entity.entity.withDrawed.IncomeOutputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.WithDrawLog.WithDrawLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WithDrawLogServiceImpl implements WithDrawLogService {
    @Autowired
    IncomeLogMapperExtra mapperExtra;

    @Autowired
    MyWalletMapperExtra myWalletMapperExtra;

    @Override
    public JSONResult getLogData(IncomeLog incomeLog, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<IncomeLog> incomeLogs =  mapperExtra.getLogData(incomeLog);

        return new JSONResult("数据获取成功", 200, new PageInfo(incomeLogs));
    }

    @Override
    public JSONResult getUserIncomeInfo(IncomeInputDTO incomeInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<Income> incomeList = myWalletMapperExtra.getUserIncomeInfo(incomeInputDTO);
        PageInfo<Income> incomes = new PageInfo(incomeList);
        IncomeOutputDTO incomeOutputDTO = new IncomeOutputDTO();
        incomeOutputDTO.setIncomeList(incomes);

        IncomeOutputDTO incomeOne = myWalletMapperExtra.getAllInfo();

        incomeOutputDTO.setAllAbleWithdrawed(incomeOne.getAllAbleWithdrawed());
        incomeOutputDTO.setAllWithdrawed(incomeOne.getAllWithdrawed());

        //获取今日的
        SimpleDateFormat day = new SimpleDateFormat("y-MM-dd");//设置日期格式为天,大写的H为24小时制，小写为12
        Date today = new Date();
        String todayStr = day.format(today);
        IncomeInputDTO inputDTO = new IncomeInputDTO();
        inputDTO.setStartTime(todayStr + "00:00:00");
        inputDTO.setEndTime(todayStr + "23:59:59");

        IncomeOutputDTO incomeTwo = myWalletMapperExtra.todayAllInfo(inputDTO);

        incomeOutputDTO.setTodayWithdrawed(incomeTwo.getTodayWithdrawed());
        incomeOutputDTO.setTodayWithdrawedTimes(incomeTwo.getTodayWithdrawedTimes());

        return new JSONResult("获取数据成功", 200, incomeOutputDTO);
    }
}

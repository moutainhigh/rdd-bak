package com.cqut.czb.bn.entity.entity.withDrawed;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class IncomeOutputDTO {
    private PageInfo<Income> incomeList;

    private Double allAbleWithdrawed;

    private Double allWithdrawed;

    private Double todayWithdrawed;

    private Double todayWithdrawedTimes;

    public PageInfo<Income> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(PageInfo<Income> incomeList) {
        this.incomeList = incomeList;
    }

    public Double getAllAbleWithdrawed() {
        return allAbleWithdrawed;
    }

    public void setAllAbleWithdrawed(Double allAbleWithdrawed) {
        this.allAbleWithdrawed = allAbleWithdrawed;
    }

    public Double getAllWithdrawed() {
        return allWithdrawed;
    }

    public void setAllWithdrawed(Double allWithdrawed) {
        this.allWithdrawed = allWithdrawed;
    }

    public Double getTodayWithdrawed() {
        return todayWithdrawed;
    }

    public void setTodayWithdrawed(Double todayWithdrawed) {
        this.todayWithdrawed = todayWithdrawed;
    }

    public Double getTodayWithdrawedTimes() {
        return todayWithdrawedTimes;
    }

    public void setTodayWithdrawedTimes(Double todayWithdrawedTimes) {
        this.todayWithdrawedTimes = todayWithdrawedTimes;
    }
}

package com.cqut.czb.bn.entity.dto.rentCar;

import java.util.List;

/**
 * 此类去接收json数据
 */
public class AddCompanyContractList {
    private String startTime;

    private String endTime;

    private List<CompanyPersonDTO> addCompanyContractList;

    public List<CompanyPersonDTO> getAddCompanyContractList() {
        return addCompanyContractList;
    }

    public void setAddCompanyContractList(List<CompanyPersonDTO> addCompanyContractList) {
        this.addCompanyContractList = addCompanyContractList;
    }

    public AddCompanyContractList( ) {
    }

    public AddCompanyContractList(List<CompanyPersonDTO> addCompanyContractList) {
        this.addCompanyContractList = addCompanyContractList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

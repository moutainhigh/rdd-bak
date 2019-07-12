package com.cqut.czb.bn.entity.dto.Commodity;

import java.util.List;

/**
 * author:De-qiang Chen
 * function:The information to be displayed in
 * each navigation bar for merchants to enter
 */

public class AllCommodityDTO {

    private String  navName;//导航栏名字

    private List<ServiceDetailsDTO> serviceDetailsDTOList;//this is all goods;

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public List<ServiceDetailsDTO> getServiceDetailsDTOList() {
        return serviceDetailsDTOList;
    }

    public void setServiceDetailsDTOList(List<ServiceDetailsDTO> serviceDetailsDTOList) {
        this.serviceDetailsDTOList = serviceDetailsDTOList;
    }
}

package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PartnerConsumptionMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PartnerConsumptionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerConsumptionServiceImpl implements PartnerConsumptionService {
    @Autowired
    PartnerConsumptionMapperExtra partnerConsumptionMapperExtra;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Override
    public PageInfo<PartnerDTO> getConsumptionList(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
//        if (partnerDTO.getSuperior()==null||("").equals(partnerDTO.getSuperior())){
//            userMapperExtra.insertAllSubUser(partnerDTO.getSuperior());
//        }
        //待优化
        if (partnerDTO.getOrderBy()!=null&&partnerDTO.getOrderBy()!=""){    //判断数据是否需要自定义排序
            if("nearTime".equals(partnerDTO.getOrderBy())){  //根据最近一次消费时间进行排序
                partnerDTO.setOrderBy("SUM(b.money)");
                if (partnerDTO.getOrder()!=null&&!"".equals(partnerDTO.getOrder())){
                    if ("descending".equals(partnerDTO.getOrder())){
                            partnerDTO.setOrder("DESC");                //排序方式
                    }else {
                        partnerDTO.setOrder("ASC");
                    }
                }else {
                    partnerDTO.setOrder("ASC");
                }
            };
            if("totalMoney".equals(partnerDTO.getOrderBy())){  //根据消费总金额进行排序
                partnerDTO.setOrderBy("MAX(b.create_at)");
                if (partnerDTO.getOrder()!=null&&!"".equals(partnerDTO.getOrder())){
                    if ("descending".equals(partnerDTO.getOrder())){
                        partnerDTO.setOrder("DESC");
                    }else {
                        partnerDTO.setOrder("ASC");
                    }
                }else {
                    partnerDTO.setOrder("ASC");
                }
            };
            if("consumptionCount".equals(partnerDTO.getOrderBy())){  //根据消费次数进行排序
                partnerDTO.setOrderBy("COUNT(b.money)");
                if (partnerDTO.getOrder()!=null&&!"".equals(partnerDTO.getOrder())){
                    if ("descending".equals(partnerDTO.getOrder())){
                        partnerDTO.setOrder("DESC");
                    }else {
                        partnerDTO.setOrder("ASC");
                    }
                }else {
                    partnerDTO.setOrder("ASC");
                }
            }
        }
        List<PartnerDTO> partnerDTOList =  partnerConsumptionMapperExtra.selectConsumptionList(partnerDTO);

        return new PageInfo<>(partnerDTOList);
    }

    @Override
    public PageInfo<PartnerDTO> getDetailConsumption(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(partnerConsumptionMapperExtra.selectDetailConsumption(partnerDTO));
    }


}

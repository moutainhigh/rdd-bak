package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.BusinessCommonUserMapperExtra;
import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.BusinessCommonUserVo;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.BusinessCommonUserOutputDTO;
import com.cqut.czb.bn.service.partnerAndOperateCenter.BusinessCommonUserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessCommonUserServiceImpl implements BusinessCommonUserService {

    @Autowired
    private BusinessCommonUserMapperExtra mapperExtra;

    @Override
    public PageInfo<BusinessCommonUserOutputDTO> list(String userId, String mobile, @DateTimeFormat(pattern = "yyyy-MM-dd")Date createAt, String area, String promotionMobile,Integer isVip,PageDTO pageDTO) {
        if(mobile != null ){
            mobile = mobile.trim();
        }
        if(area!=null){
            area = area.trim();
        }
        if(promotionMobile!=null){
            promotionMobile = promotionMobile.trim();
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize(),true);
        BusinessCommonUserVo vo = new BusinessCommonUserVo(userId,mobile,createAt,area,promotionMobile,isVip);
        List<BusinessCommonUserOutputDTO> list = mapperExtra.list(vo);
        ArrayList<String> arrayList = new ArrayList<>();
        for(BusinessCommonUserOutputDTO item : list){
            arrayList.add(item.getUserId());
        }
        list = mapperExtra.listPetrol(arrayList);

        PageInfo<BusinessCommonUserOutputDTO> pageInfo = new PageInfo<>(list);
        return  pageInfo;
    }
}

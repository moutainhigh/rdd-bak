package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.GeneralPartnerMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GetGeneralPartnerListDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GetNumberOfDevelopmentDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerDevelopmentNumbers;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GetGeneralPartnerListVo;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.GeneralPartnerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GeneralPartnerServiceImpl implements GeneralPartnerService {
    @Autowired
    GeneralPartnerMapperExtra mapperExtra;

    @Override
    public JSONResult getGeneralPartnerList(String userId, String account, Date creatAt, Integer areaId, PageDTO pageDTO) {

        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        GetGeneralPartnerListVo vo = new GetGeneralPartnerListVo(userId, account, creatAt, areaId);
        List<GetGeneralPartnerListDTO> generalPartnerList = mapperExtra.selectGeneralPartnerList(vo);
        PageInfo<GetGeneralPartnerListDTO> pageInfo = new PageInfo<>(generalPartnerList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getNumberOfDevelopment(String userId, Integer condition) {

        GeneralPartnerDevelopmentNumbers vo = new GeneralPartnerDevelopmentNumbers(userId,condition);
        GetNumberOfDevelopmentDTO getNumberOfDevelopment = mapperExtra.selectDevelopmentNumbers(vo);
        return new JSONResult("列表数据查询成功", 200, getNumberOfDevelopment);
    }
}

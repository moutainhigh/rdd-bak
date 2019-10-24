package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapper;
import com.cqut.czb.bn.dao.mapper.VipAreaConfigMapperExtra;
import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.dto.VipArea.VipChangeType;
import com.cqut.czb.bn.entity.dto.VipArea.VipPriceAndNote;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.VIPAreaManagementService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 14:37
 */
@Service
public class VIPAreaManagementServiceImpl implements VIPAreaManagementService {

    @Autowired
    VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Autowired
    VipAreaConfigMapper vipAreaConfigMapper;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public PageInfo getVipAreaList(VipAreaDTO vipAreaDTO) {
        PageHelper.startPage(vipAreaDTO.getCurrentPage(), vipAreaDTO.getPageSize());
        return new PageInfo<>(vipAreaConfigMapperExtra.getVipAreaList(vipAreaDTO));
    }

    @Override
    public Boolean addVipArea(VipAreaDTO vipAreaDTO) {
        try{
            vipAreaDTO.setVipAreaConfigId(StringUtil.createId());
            return vipAreaConfigMapperExtra.addVipArea(vipAreaDTO) > 0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public JSONResult closeAll(VipChangeType type) {
        VipAreaDTO vipAreaDTO = new VipAreaDTO();
        List<VipAreaDTO> vipAreaDTOList =  vipAreaConfigMapperExtra.getVipAreaList(vipAreaDTO);

        boolean changeAll = false;

        if(type.getType() == 0) {
            changeAll = vipAreaConfigMapperExtra.closeAll(vipAreaDTOList) > 0;
        } else {
            changeAll = vipAreaConfigMapperExtra.openAll(vipAreaDTOList) > 0;
        }

        if(changeAll)
            return new JSONResult("打开/关闭全地区vip成功", 200);
        else
            return new JSONResult("打开/关闭全地区vip失败", 500);
    }

    @Override
    public Boolean editVipArea(VipAreaConfig vipAreaConfig) {
        vipAreaConfig.setUpdateAt(new Date());
        return vipAreaConfigMapper.updateByPrimaryKeySelective(vipAreaConfig) > 0;
    }

    @Override
    public Boolean deleteVipArea(String vipAreaId) {
        return vipAreaConfigMapper.deleteByPrimaryKey(vipAreaId) > 0;
    }

    @Override
    public VipPriceAndNote getVipPriceAndNote(String area) {
        if(area == null || area == "")
            return null;
        VipPriceAndNote vipPriceAndNote = new VipPriceAndNote();
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipPrice(area);
        vipPriceAndNote.setArea(vipAreaConfig.getArea());
        vipPriceAndNote.setVipPrice(vipAreaConfig.getVipPrice());
        vipPriceAndNote.setNote(dictMapperExtra.selectDictByName("rechargeNote").getContent());
        return vipPriceAndNote;
    }
}
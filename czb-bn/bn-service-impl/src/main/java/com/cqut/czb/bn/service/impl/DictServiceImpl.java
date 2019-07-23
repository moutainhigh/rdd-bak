package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.AppInfoDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public PageInfo<Dict> selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);
        return new PageInfo<>(dictList);
    }

    @Override
    public List<Dict> selectCustomerServiceStaff() {
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "kf";
        dictInputDTO.setName(name);
        return dictMapperExtra.selectDict(dictInputDTO);
    }

    @Override
    public AppInfoDTO selectAndroidInfo(String version) {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "android";
        dictInputDTO.setName(name);
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);
        ArrayList<String> url = new ArrayList<>();
        for(Dict dict: dictList) {
            if("android_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("android_description".equals(dict.getName())) {
                appInfoDTO.setDescription(dict.getContent());
            }
            if(dict.getName().startsWith("android_url")) {
                url.add(dict.getContent());
            }
        }
        Random random = new Random();
        appInfoDTO.setIsUpdate(false);
        appInfoDTO.setUrl(url.get(random.nextInt(url.size())));
        return appInfoDTO;
    }

    @Override
    public AppInfoDTO selectIOSInfo(String version) {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "ios";
        dictInputDTO.setName(name);
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);
        ArrayList<String> url = new ArrayList<>();
        for(Dict dict: dictList) {
            if("ios_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("ios_description".equals(dict.getName())) {
                appInfoDTO.setDescription(dict.getContent());
            }
            if(dict.getName().startsWith("ios_url")) {
                url.add(dict.getContent());
            }
        }
        Random random = new Random();
        appInfoDTO.setUrl(url.get(random.nextInt(url.size())));
        appInfoDTO.setIsUpdate(false);
        return appInfoDTO;
    }

    @Override
    public boolean updateDict(DictInputDTO dictInputDTO) {
        return dictMapperExtra.updateDict(dictInputDTO) > 0;
    }

    @Override
    public boolean deleteDict(DictInputDTO dictInputDTO) {
        return dictMapperExtra.deleteDict(dictInputDTO.getDictId()) > 0;
    }

    @Override
    public boolean insertDict(DictInputDTO dictInputDTO) {
        dictInputDTO.setDictId(StringUtil.createId());
        return dictMapperExtra.insertDict(dictInputDTO) > 0;
    }

    @Override
    public Dict getDicByName(DictInputDTO dictInputDTO) {
        return dictMapperExtra.selectDictByName(dictInputDTO.getName()) ;
    }
}

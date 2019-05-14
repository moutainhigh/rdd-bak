package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.entity.dto.AppInfoDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public List<Dict> selectDictList() {

        return dictMapperExtra.selectDict("");
    }

    @Override
    public List<Dict> selectCustomerServiceStaff() {
        String name = "kf";
        return dictMapperExtra.selectDict(name);
    }

    @Override
    public AppInfoDTO selectAndroidInfo() {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        String name = "android";
        List<Dict> dictList = dictMapperExtra.selectDict(name);
        for(Dict dict: dictList) {
            if("android_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("android_description".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("android_url".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
        }
        return appInfoDTO;
    }

    @Override
    public AppInfoDTO selectIOSInfo() {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        String name = "ios";
        List<Dict> dictList = dictMapperExtra.selectDict(name);
        for(Dict dict: dictList) {
            if("ios_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("ios_description".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("ios_url".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
        }
        return appInfoDTO;
    }
}

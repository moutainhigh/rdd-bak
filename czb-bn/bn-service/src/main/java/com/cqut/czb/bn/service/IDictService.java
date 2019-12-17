package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.AppInfoDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IDictService {

    PageInfo<Dict> selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO);

    List<Dict> selectCustomerServiceStaff();

    AppInfoDTO selectAndroidInfo(User user, String version,String DeviceToken);

    AppInfoDTO selectIOSInfo(User user,String version,String DeviceToken);

    boolean updateDict(DictInputDTO dictInputDTO);

    boolean deleteDict(DictInputDTO dictInputDTO);

    boolean insertDict(DictInputDTO dictInputDTO);

    Dict getDicByName(DictInputDTO dictInputDTO);
}

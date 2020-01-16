package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.OrdinaryManagementMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerUser;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.OrdinaryManagementUser;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.OrdinaryManagement;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: OrdinaryManagement
 * @Author: Iriya720
 * @Date: 2019/12/2
 * @Description:
 * @version: v1.0
 */
@Service
public class OrdinaryManagementImpl implements OrdinaryManagement {

    @Autowired
    private OrdinaryManagementMapperExtra mapper;

    /**
     * 获取普通用户表格
     * @param pageDTO
     * @return
     */
    @Override
    public JSONResult getTableData(GeneralPartnerUserPageDTO pageDTO) {

        String account = pageDTO.getAccount();
        String superiorUserAccount = pageDTO.getSuperiorUserAccount();
        String createAt = pageDTO.getCreateAt();

        if(!"".equals(account) && null != account){
            account = account.trim();
        }
        if(!"".equals(superiorUserAccount) && null != superiorUserAccount){
            superiorUserAccount = superiorUserAccount.trim();
        }
        if(!"".equals(createAt) && null != createAt){
            createAt = pageDTO.getCreateAt().substring(0,10);
        }

        pageDTO.setAccount(account);
        pageDTO.setSuperiorUserAccount(superiorUserAccount);
        pageDTO.setCreateAt(createAt);


        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize(),true);
        List<GeneralPartnerUser> users =  mapper.getAllUser(pageDTO);
        PageInfo<GeneralPartnerUser> pageInfo = new PageInfo<>(users);
        return new JSONResult("查询成功",200,pageInfo);
    }
}

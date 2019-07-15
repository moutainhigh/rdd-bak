package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ApiMapperExtra;
import com.cqut.czb.bn.dao.mapper.RoleApiMapper;
import com.cqut.czb.bn.dao.mapper.RoleMapperExtra;
import com.cqut.czb.bn.entity.entity.Api;
import com.cqut.czb.bn.entity.entity.Role;
import com.cqut.czb.bn.entity.entity.RoleApi;
import com.cqut.czb.bn.service.IManagementApiService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IManagementApiImpl implements IManagementApiService {
    @Autowired
    private ApiMapperExtra apiMapperExtra;

    @Autowired
    private RoleMapperExtra roleMapperExtra;

    @Autowired
    private RoleApiMapper roleApiMapper;

    @Override
    public boolean insertRoleApi() {
        List<Api> apis=apiMapperExtra.selectAllInfo();//查出所有的加了api的接口。
        List<Role> roles=roleMapperExtra.selectAllRole();//查出所有的角色。
        for(int i=0;i<roles.size();i++){
            RoleApi roleApi=new RoleApi();
//            roleApi.setId(StringUtil.createId());
            roleApi.setRoleId(roles.get(i).getRoleId());
            for(int j=0;j<apis.size();j++){
                roleApi.setId(StringUtil.createId());
                roleApi.setApiId(apis.get(j).getApiId());
                roleApiMapper.insert(roleApi);
            }
        }
        return true;
    }
}

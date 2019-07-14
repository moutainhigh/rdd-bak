package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.roleApi.RoleApiIdDTO;
import com.cqut.czb.bn.entity.dto.roleApi.RoleApiUrlDTO;
import com.cqut.czb.bn.entity.entity.RoleApi;

import java.util.List;

public interface RoleApiMapperExtra {
    List<RoleApiIdDTO> getAllRoleApi();

}
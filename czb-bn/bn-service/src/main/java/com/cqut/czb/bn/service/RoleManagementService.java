package com.cqut.czb.bn.service;



import com.cqut.czb.bn.entity.dto.role.*;
import java.util.List;

public interface RoleManagementService{
    List<RoleTypeDTO> selectRoleType();
    boolean insertTest();
}
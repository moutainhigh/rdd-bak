package com.cqut.czb.bn.entity.dto.roleApi;

import com.cqut.czb.bn.entity.entity.RoleApi;

import java.util.List;

public class RoleApiIdDTO {
    private String roleId;
    private List<RoleApiUrlDTO> api;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<RoleApiUrlDTO> getApi() {
        return api;
    }

    public void setApi(List<RoleApiUrlDTO> api) {
        this.api = api;
    }
}

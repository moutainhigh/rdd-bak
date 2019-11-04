package com.cqut.czb.bn.entity.dto.user;

import com.cqut.czb.bn.entity.dto.role.RoleDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.io.Serializable;
import java.util.List;

public class UserDTO extends User implements Serializable {

    private List<RoleDTO> roleList;

    private String roleId;

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}

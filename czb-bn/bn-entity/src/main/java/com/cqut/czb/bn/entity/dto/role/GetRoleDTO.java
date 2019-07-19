package com.cqut.czb.bn.entity.dto.role;

/**
 * RoleManagementController 获取角色信息DTO
 * 设计者:   杨强
 * 更新日期: 2018/4/5
 */
public class GetRoleDTO{

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 是否允许编辑
     */
    private Integer isAllowEdit;

    /**
     * 是否允许删除
     */
    private Integer isAllowDelete;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        if (roleName == ""){
            this.roleName = null;
        }else {
            this.roleName = roleName;
        }
    }

    public Integer getIsAllowEdit() {
        return isAllowEdit;
    }

    public void setIsAllowEdit(Integer isAllowEdit) {
        this.isAllowEdit = isAllowEdit;
    }

    public Integer getIsAllowDelete() {
        return isAllowDelete;
    }

    public void setIsAllowDelete(Integer isAllowDelete) {
        this.isAllowDelete = isAllowDelete;
    }

}

package com.cqut.czb.bn.entity.dto.role;

import javax.validation.constraints.NotNull;

public class RoleIdDTO
{
    /**
     * 角色名
     */
    @NotNull(message = "角色id不能为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

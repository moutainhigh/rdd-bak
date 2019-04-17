package com.cqut.czb.bn.entity.global;

public enum DataScope {

    ALL("所有数据"),

    COMPANY_AND_CHILD("公司及以下数据"),

    COMPANY("公司数据"),

    DEPARTMENT_AND_CHILD("部门及以下数据"),

    DEPARTMENT("部门数据"),

    SELF("个人数据"),

    CUSTOM("自定义数据");

    private String cn;

    DataScope(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }
}

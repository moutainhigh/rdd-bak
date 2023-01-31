package com.cqut.czb.bn.entity.entity.exchangeCode;

import java.util.Date;
import java.io.Serializable;

/**
 * (ExchangeCode)实体类
 *
 * @author makejava
 * @since 2022-12-10 22:54:08
 */
public class ExchangeCode implements Serializable {
    private static final long serialVersionUID = -55291622914552865L;
    
    private String code;
    
    private Integer isuse;

    private Double amount;
    
    private String userid;

    private String useraccount;
    /**
     * 创建时间
     */
    private Date updatetime;

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsuse() {
        return isuse;
    }

    public void setIsuse(Integer isuse) {
        this.isuse = isuse;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}


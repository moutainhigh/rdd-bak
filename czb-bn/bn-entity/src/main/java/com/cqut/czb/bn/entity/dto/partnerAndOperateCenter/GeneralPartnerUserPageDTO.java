package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName: GeneralPartnerUserPageDTO
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description:
 * @version: v1.0
 */
public class GeneralPartnerUserPageDTO extends PageDTO {
    private String userId = null;
    private String account = null;
    private String createAt;
    private String area = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface IVIPService {

    String createVIPOrder(User user);

    boolean purchaseVIP(HttpServletRequest request);
}

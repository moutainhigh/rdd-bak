package com.cqut.czb.bn.service.personCenterService;

import javax.servlet.http.HttpServletRequest;

public interface IVIPService {

    String createVIPOrder(String userId);

    boolean purchaseVIP(HttpServletRequest request);
}

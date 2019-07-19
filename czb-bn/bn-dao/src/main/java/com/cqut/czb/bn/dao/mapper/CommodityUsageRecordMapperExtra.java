package com.cqut.czb.bn.dao.mapper;

import java.util.Date;
import java.util.List;

public interface CommodityUsageRecordMapperExtra {
    int selectOrderIdCount(String orderId);

    List<Date> getUsageList(String orderId);
}

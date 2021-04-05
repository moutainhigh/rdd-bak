package com.cqut.czb.bn.dao.mapper.mallPartnerManage;

import com.cqut.czb.bn.entity.dto.mallPartnerManage.ConsumptionDetailsDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.OrderDetails;
import com.cqut.czb.bn.entity.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallPartnerManageMapperExtra {

    List<MallPartnerDTO> selectMallPartnerList(MallPartnerDTO mallPartnerDTO);

    MallPartnerDTO selectSubordinateDirectChargeOrderTotal(MallPartnerDTO mallPartnerDTO);

    MallPartnerDTO selectSubordinateH5StockOrderTotal(MallPartnerDTO mallPartnerDTO);

    List<OrderDetails> selectDirectChargeOrderDetails(OrderDetails orderDetails);

    List<OrderDetails> selectH5StockOrderDetails(OrderDetails orderDetails);

    List<MallPartnerDTO> getMyFriends(User user);
}

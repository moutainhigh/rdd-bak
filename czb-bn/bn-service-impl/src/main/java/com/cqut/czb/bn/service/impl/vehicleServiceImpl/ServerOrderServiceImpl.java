package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.ServerOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerOrderServiceImpl implements ServerOrderService {
    @Autowired
    VehicleCleanOrderMapperExtra mapperExtra;

    @Autowired
    VehicleCleanOrderMapper mapper;

    @Override
    public JSONResult distribute(VehicleOrderManageDTO manageDTO) {
        CleanRider rider = new CleanRider();
        rider.setRiderName(manageDTO.getRiderName());
        rider.setContactNumber(manageDTO.getRiderPhone());

        CleanRider cleanRider =mapperExtra.getCleanRider(rider);
        if (null != cleanRider) {
            if(cleanRider.getStatus() == 1) {
                return new JSONResult("骑手正在路上", 500);
            } else if (cleanRider.getStatus() == 2) {
                return new JSONResult("骑手正在服务", 500);
            } else {
                VehicleCleanOrder cleanOrder = new VehicleCleanOrder();
                cleanOrder.setServerOrderId(manageDTO.getServerOrderId());
                cleanOrder.setRiderId(cleanRider.getRiderId());
                mapper.updateByPrimaryKeySelective(cleanOrder);
                return new JSONResult("分配骑手成功", 200);
            }
        } else {
            return new JSONResult("没有这个骑手", 500);
        }
    }

    @Override
    public JSONResult complete(VehicleCleanOrderDTO cleanOrderDTO) {
        cleanOrderDTO.setProcessStatus(2);
        if (mapperExtra.updateOrderStateCancel(cleanOrderDTO) > 0) {
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 200);
        }
    }

    @Override
    public JSONResult change() {
        return null;
    }

    @Override
    public JSONResult search(VehicleOrderManageDTO orderManageDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<VehicleOrderManageDTO> orderList = mapperExtra.search(orderManageDTO);
        PageInfo<VehicleOrderManageDTO> orderInfoList = new PageInfo<>(orderList);

        return new JSONResult("查询成功", 200, orderInfoList);
    }
}

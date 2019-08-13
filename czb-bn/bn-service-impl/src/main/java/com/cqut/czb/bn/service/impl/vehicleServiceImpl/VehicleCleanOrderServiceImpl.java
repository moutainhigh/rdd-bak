package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.RiderEvaluateMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import com.cqut.czb.bn.service.vehicleService.VehicleCleanOrderService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VehicleCleanOrderServiceImpl implements VehicleCleanOrderService{

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;
    @Autowired
    RiderEvaluateMapper riderEvaluateMapper;

    @Override
    public List<VehicleCleanOrderDTO> getOrderList(User user) {
        VehicleCleanOrderDTO vehicleCleanOrderDTO = new VehicleCleanOrderDTO();
        vehicleCleanOrderDTO.setUserId(user.getUserId());
         return vehicleCleanOrderMapperExtra.selectById(vehicleCleanOrderDTO);
    }

    @Override
    public Boolean cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user) {
        vehicleCleanOrderDTO.setCancelPersonId(user.getUserId());
        if (vehicleCleanOrderDTO.getPayStatus()!=null){
        vehicleCleanOrderDTO.setPayStatus(2);
        }
        if (vehicleCleanOrderDTO.getProcessStatus()!=null) {
            vehicleCleanOrderDTO.setProcessStatus(4);
        }
        vehicleCleanOrderDTO.setUpdateAt(new Date());
        return vehicleCleanOrderMapperExtra.updateOrderStateCancel(vehicleCleanOrderDTO)>0;
    }

    @Override
    public Boolean completeOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO) {
        if (vehicleCleanOrderDTO.getPayStatus()!=null){
            vehicleCleanOrderDTO.setPayStatus(1);
        }
        if (vehicleCleanOrderDTO.getProcessStatus()!=null) {
            vehicleCleanOrderDTO.setProcessStatus(3);
        }
        RiderEvaluate riderEvaluate = new RiderEvaluate();
        riderEvaluate.setEvaluateId(StringUtil.createId());
        riderEvaluate.setCreateAt(new Date());
        riderEvaluate.setEvaluateLevel(vehicleCleanOrderDTO.getEvaluateLevel());
        riderEvaluate.setEvaluateMessage(vehicleCleanOrderDTO.getEvaluateMessage());
        riderEvaluate.setEvaluateRiderId(vehicleCleanOrderDTO.getRiderId());
        riderEvaluateMapper.insert(riderEvaluate);
        vehicleCleanOrderDTO.setUpdateAt(new Date());
        return vehicleCleanOrderMapperExtra.updateOrderStateComplete(vehicleCleanOrderDTO)>0;
    }
}

package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleCleanOrderService {

    List<VehicleCleanOrderDTO> getOrderList(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user);

    VehicleCleanOrderDTO getOrderPic(VehicleCleanOrderDTO cleanOrderDTO);

    VehicleCleanOrderDTO getServicingOrder(User user);

    Boolean cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user);

    Boolean completeOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user);

    Boolean evaluateRider(RiderEvaluate riderEvaluate,User user);
}

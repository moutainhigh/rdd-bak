package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleCleanOrderService {

    List<VehicleCleanOrderDTO> getOrderList(User user);

    VehicleCleanOrderDTO getServicingOrder(User user);

    Boolean cancelOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO,User user);

    Boolean completeOrder(VehicleCleanOrderDTO vehicleCleanOrderDTO);

    Boolean evaluateRider(RiderEvaluate riderEvaluate,User user);
}

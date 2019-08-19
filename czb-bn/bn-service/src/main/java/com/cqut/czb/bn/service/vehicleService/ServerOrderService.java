package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.TuiKuanDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface ServerOrderService {
    JSONResult distribute(VehicleOrderManageDTO manageDTO);

    JSONResult complete(VehicleCleanOrderDTO cleanOrderDTO);

    JSONResult change();

    JSONResult tuiKuan(TuiKuanDTO tuiKuanDTO);

    JSONResult search(VehicleOrderManageDTO dto, PageDTO pageDTO);
}

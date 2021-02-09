package com.cqut.czb.bn.service.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface WCardAndPICCodeService {

    List<WCardAndPICCodeDTO> getCommodityOrder(String userId);
}

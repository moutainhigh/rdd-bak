package com.cqut.czb.bn.service.withoutCard;

import com.cqut.czb.bn.entity.dto.withoutCard.PetrolSalesWithoutDto;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

/**
 * 作者： 陈爽
 * 模块： 无卡加油消费记录管理
 * 创建时间： 2020/11/17
 */
@Component
public interface WithoutCardSellManagementService {

    PageInfo<PetrolSalesWithoutDto> listPetrolSellManagement(PetrolSalesWithoutDto petrolSalesWithoutDto);

    String getPetrolSellManagementTotal(PetrolSalesWithoutDto petrolSalesWithoutDto);

    Workbook exportSaleRecords(PetrolSalesWithoutDto petrolSalesWithoutDto) throws Exception;
}

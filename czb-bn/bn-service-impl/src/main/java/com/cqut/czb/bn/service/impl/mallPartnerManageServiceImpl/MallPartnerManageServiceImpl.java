package com.cqut.czb.bn.service.impl.mallPartnerManageServiceImpl;

import com.cqut.czb.bn.dao.mapper.mallPartnerManage.MallPartnerManageMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.ConsumptionDetailsDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.OrderDetails;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.mallPartnerManageService.MallPartnerManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MallPartnerManageServiceImpl implements MallPartnerManageService {

    @Autowired
    MallPartnerManageMapperExtra mallPartnerManageMapperExtra;

    @Override
    public JSONResult statisticsMoney() {
        double sum = 0;
        MallPartnerDTO mallPartnerDTO = new MallPartnerDTO();
        MallPartnerDTO newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateDirectChargeOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            sum += newSubordinateDTO.getGrossSales();
        }

        newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateH5StockOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            sum += newSubordinateDTO.getGrossSales();
        }
        return new JSONResult(sum);
    }

    @Override
    public PageInfo<MallPartnerDTO> getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageMapperExtra.selectMallPartnerList(mallPartnerDTO);

        for (MallPartnerDTO temp : mallPartnerDTOList) {
            MallPartnerDTO newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateDirectChargeOrderTotal(temp);
            getAmountTotal(temp, newSubordinateDTO);

            newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateH5StockOrderTotal(temp);
            getAmountTotal(temp, newSubordinateDTO);
        }
        return new PageInfo<>(mallPartnerDTOList);
    }

    @Override
    public List<MallPartnerDTO> getMallPartnerConsumptionDetails(MallPartnerDTO mallPartnerDTO) {
        List<MallPartnerDTO> mallPartnerDTOList = new ManagedList<>();

        mallPartnerDTO.setType(1);
        MallPartnerDTO newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateDirectChargeOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            newSubordinateDTO.setType(1);
        }
        mallPartnerDTOList.add(newSubordinateDTO);

        mallPartnerDTO.setType(2);
        newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateDirectChargeOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            newSubordinateDTO.setType(2);
        }
        mallPartnerDTOList.add(newSubordinateDTO);

        mallPartnerDTO.setType(3);
        newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateH5StockOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            newSubordinateDTO.setType(3);
        }
        mallPartnerDTOList.add(newSubordinateDTO);

        mallPartnerDTO.setType(4);
        newSubordinateDTO = mallPartnerManageMapperExtra.selectSubordinateH5StockOrderTotal(mallPartnerDTO);
        if (newSubordinateDTO != null) {
            newSubordinateDTO.setType(4);
        }
        mallPartnerDTOList.add(newSubordinateDTO);
        sortConsumptionDetailsDTOList(mallPartnerDTOList);
        return mallPartnerDTOList;
    }

    @Override
    public PageInfo<OrderDetails> getEveryOrderDetails(OrderDetails orderDetails, PageDTO pageDTO) {
        if (orderDetails.getType() == 1 || orderDetails.getType() == 2) {
            PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
            List<OrderDetails> orderDetailsList = mallPartnerManageMapperExtra.selectDirectChargeOrderDetails(orderDetails);
            return new PageInfo<>(orderDetailsList);
        } else if (orderDetails.getType() == 3 || orderDetails.getType() == 4) {
            PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
            List<OrderDetails> orderDetailsList = mallPartnerManageMapperExtra.selectH5StockOrderDetails(orderDetails);
            return new PageInfo<>(orderDetailsList);
        }
        return null;
    }

    public void sortConsumptionDetailsDTOList(List<MallPartnerDTO> mallPartnerDTOList) {
        int[] types = {1, 2, 3, 4};
        for (int i = 0; i < types.length; i++) {
            boolean isType = false;
            for (MallPartnerDTO mallPartnerDTO : mallPartnerDTOList) {
                if (mallPartnerDTO == null) {
                    continue;
                }
                if (mallPartnerDTO.getType() == types[i]) {
                    isType = true;
                    break;
                }
            }

            if (!isType) {
                MallPartnerDTO mallPartnerDTO = new MallPartnerDTO();
                mallPartnerDTO.setType(types[i]);
                mallPartnerDTOList.add(mallPartnerDTO);
            }
        }

        for (int i = 0; i < mallPartnerDTOList.size(); i++) {
            if (mallPartnerDTOList.get(i) == null) {
                mallPartnerDTOList.remove(i);
                i = i - 1;
            }
        }

        mallPartnerDTOList.sort(new Comparator<MallPartnerDTO>() {
            @Override
            public int compare(MallPartnerDTO o1, MallPartnerDTO o2) {
                return o1.getType() - o2.getType();
            }
        });
    }

    public void getAmountTotal(MallPartnerDTO temp, MallPartnerDTO newMallPartnerDTO) {
        if (newMallPartnerDTO != null && newMallPartnerDTO != null) {
            temp.setPersonNumber(temp.getPersonNumber() + newMallPartnerDTO.getPersonNumber());
            temp.setOrderNumber(temp.getOrderNumber() + newMallPartnerDTO.getOrderNumber());
            temp.setGrossSales(temp.getGrossSales() + newMallPartnerDTO.getGrossSales());
            temp.setTotalRevenue(temp.getTotalRevenue() + newMallPartnerDTO.getTotalRevenue());
        }
    }
}

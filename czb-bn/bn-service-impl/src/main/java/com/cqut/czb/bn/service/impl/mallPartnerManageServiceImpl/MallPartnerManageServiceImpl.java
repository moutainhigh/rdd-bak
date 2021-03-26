package com.cqut.czb.bn.service.impl.mallPartnerManageServiceImpl;

import com.cqut.czb.bn.dao.mapper.mallPartnerManage.MallPartnerManageMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.ConsumptionDetailsDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.mallPartnerManageService.MallPartnerManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MallPartnerManageServiceImpl implements MallPartnerManageService {

    @Autowired
    MallPartnerManageMapperExtra mallPartnerManageMapperExtra;

    @Override
    public JSONResult statisticsMoney() {
        return new JSONResult(mallPartnerManageMapperExtra.statisticsMoney());
    }

    @Override
    public PageInfo<MallPartnerDTO> getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<MallPartnerDTO> mallPartnerDTOList = mallPartnerManageMapperExtra.selectMallPartnerList(mallPartnerDTO);
        return new PageInfo<>(mallPartnerDTOList);
    }

    @Override
    public JSONResult getMallPartnerConsumptionDetails(User user) {
        List<ConsumptionDetailsDTO> consumptionDetailsDTOList = mallPartnerManageMapperExtra.selectMallPartnerConsumptionDetails(user);
        sortConsumptionDetailsDTOList(consumptionDetailsDTOList);
        return new JSONResult(consumptionDetailsDTOList);
    }

    public void sortConsumptionDetailsDTOList(List<ConsumptionDetailsDTO> consumptionDetailsDTOList) {
        int[] types = {1, 2, 3, 4, 5};
        for (int i = 0; i < types.length; i++) {
            boolean isType = false;
            for (ConsumptionDetailsDTO consumptionDetailsDTO : consumptionDetailsDTOList) {
                if (consumptionDetailsDTO == null) {
                    continue;
                }
                if (consumptionDetailsDTO.getType() == types[i]) {
                    isType = true;
                    break;
                }
            }

            if (!isType) {
                ConsumptionDetailsDTO consumptionDetailsDTO = new ConsumptionDetailsDTO();
                consumptionDetailsDTO.setType(types[i]);
                consumptionDetailsDTOList.add(consumptionDetailsDTO);
            }
        }

        for (int i = 0; i < consumptionDetailsDTOList.size(); i++) {
            if (consumptionDetailsDTOList.get(i) == null) {
                consumptionDetailsDTOList.remove(i);
                i = i - 1;
            }
        }

        consumptionDetailsDTOList.sort(new Comparator<ConsumptionDetailsDTO>() {
            @Override
            public int compare(ConsumptionDetailsDTO o1, ConsumptionDetailsDTO o2) {
                return o1.getType() - o2.getType();
            }
        });
    }
}

package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.dao.mapper.RentCarMapper;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoDTO;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.PersonalContractDTO;
import com.cqut.czb.bn.entity.dto.rentCar.personIncome;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class rentCarServiceImpl implements RentCarService {
    @Autowired
    private RentCarMapper rentCarMapper;

    /**
     * 获取个人收益记录
     * @return personIncome
     */
    @Override
    public personIncome getPersonIncome(){
        // TODO 用户id需要从redis获取
        return rentCarMapper.getPersonIncome("1");
    }

    /**
     * 获取合同概要信息
     * @param contractId
     * @return List<OneContractInfoDTO>
     */
    @Override
    public List<OneContractInfoDTO> getOneContractInfo(String contractId){
        List<OneContractInfoDTO> oneContractInfoDTOList = new ArrayList<>();
        // TODO 此id需要从redis中获取，暂时写死
        oneContractInfoDTOList =rentCarMapper.getOneContractInfo("2",contractId);
        return oneContractInfoDTOList;
    }

    @Override
    public List<PersonalContractDTO> getPersonalContractList(){
        List<PersonalContractDTO> personalContractDTOList = new ArrayList<>();
        // TODO 此id需要从redis中获取，暂时写死
        personalContractDTOList = rentCarMapper.getPersonalContractList("2");

        return personalContractDTOList;
    }
}

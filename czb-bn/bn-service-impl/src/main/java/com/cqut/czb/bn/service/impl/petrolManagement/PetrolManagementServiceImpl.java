package com.cqut.czb.bn.service.impl.petrolManagement;

import com.cqut.czb.bn.dao.mapper.PetrolMapper;
import com.cqut.czb.bn.dao.mapper.PetrolMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetrolManagementServiceImpl implements IPetrolManagementService {

    @Autowired
    PetrolMapperExtra petrolMapperExtra;
    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    /**
     * 获取油卡列表
     *
     * @param inputDTO
     * @return
     */
    @Override
    public PageInfo<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(), true);
        return new PageInfo<>(petrolMapperExtra.getPetrolList(inputDTO));
    }

    /**
     * 上传油卡excel
     *
     * @param inputStream
     * @param originalFileName
     * @return
     */
    @Override
    public int uploadPetrolExcel(InputStream inputStream, String originalFileName) {
        List<Petrol> petrols = null;
        Map<String, Petrol> petrolMap = new HashMap<>();
        try {
            petrols = ImportPetrol.readExcel(originalFileName, inputStream);
            System.out.println(petrols.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 按petrolNum为key 做到去重复的效果
         */
        if (petrols != null) {
            for (Petrol p : petrols) {
                petrolMap.put(p.getPetrolNum(), p);
            }
        }
        List<Petrol> petrolListNoRepeat = new ArrayList<>();
        for(Petrol p:petrolMap.values()){
            petrolListNoRepeat.add(p);
        }
        int countForInsert = petrolMapperExtra.insertPetrolList(petrolListNoRepeat);
        System.out.println("countForInsert " + countForInsert);
        return countForInsert;
    }

    @Override
    public PageInfo<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO) {
        PageHelper.startPage(infoInputDTO.getCurrentPage(), infoInputDTO.getPageSize(), true);
        return new PageInfo<>(petrolSalesRecordsMapperExtra.getPetrolSaleInfoList(infoInputDTO));
    }
}

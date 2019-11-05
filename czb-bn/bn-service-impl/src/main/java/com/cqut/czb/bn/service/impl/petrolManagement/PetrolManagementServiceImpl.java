package com.cqut.czb.bn.service.impl.petrolManagement;

import com.cqut.czb.bn.dao.mapper.PetrolMapperExtra;
import com.cqut.czb.bn.dao.mapper.PetrolSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.ModifyPetrolInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PetrolManagementServiceImpl implements IPetrolManagementService {

    @Autowired
    PetrolMapperExtra petrolMapperExtra;
    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;
    @Autowired
    AppHomePageService appHomePageService;
//    @Autowired
//    PetrolMapper petrolMapper;

    /**
     * 获取油卡列表
     *
     * @param inputDTO
     * @return
     */
    @Override
    public PageInfo<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize(), true);
        List<Petrol> list = petrolMapperExtra.getPetrolList(inputDTO);
        return new PageInfo<>(list);
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
        //excel里面没有重复
        List<Petrol> petrolListNoRepeatForExcel = new ArrayList<>();
        for(Petrol p:petrolMap.values()){
            petrolListNoRepeatForExcel.add(p);
        }

        //油卡缓存里面没有重复
       List<Petrol> petrolListNoRepeatForDB =  removeRepeatPetrolForDB(petrolListNoRepeatForExcel);
        int countForInsert = petrolMapperExtra.insertPetrolList(petrolListNoRepeatForDB);
        System.out.println("countForInsert " + countForInsert);
        return countForInsert;
    }

    @Override
    public PageInfo<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO) {
        PageHelper.startPage(infoInputDTO.getCurrentPage(), infoInputDTO.getPageSize(), true);
        return new PageInfo<>(petrolSalesRecordsMapperExtra.getPetrolSaleInfoList(infoInputDTO));
    }

    @Override
    public int salePetrol(String petrolIds) {
        int result=0;
        if (petrolIds==null || petrolIds.length() == 0){
            result = petrolMapperExtra.saleAllPetrol();
        }else {
            String[] ids = petrolIds.split(",");
            result = petrolMapperExtra.changePetrolState(ids,"1");
        }

        appHomePageService.selectAllPetrol();
        return result;
    }

    @Override
    public int notSalePetrol(String petrolIds) {
        int result=0;
        if (petrolIds==null || petrolIds.length() == 0){
            result = petrolMapperExtra.notSaleAllPetrol();
        }else {
            String[] ids = petrolIds.split(",");
            result = petrolMapperExtra.changePetrolState(ids,"3");
        }

        appHomePageService.selectAllPetrol();
        return result;
    }

    @Override
    public boolean modifyPetrol(ModifyPetrolInputDTO inputDTO) {
        boolean isRemoved = PetrolCache.clearPetrol("AllpetrolMap",inputDTO.getPetrolNum()) >=1;
        if(!isRemoved){
            return isRemoved;
        }else {
            Petrol petrol = new Petrol();
            petrol.setPetrolId(inputDTO.getPetrolId());
            petrol.setArea(inputDTO.getArea());
            petrol.setPetrolDenomination(Double.parseDouble(inputDTO.getPetrolDenomination()));
            petrol.setPetrolPrice(Double.parseDouble(inputDTO.getPetrolPrice()));
            petrol.setPetrolPsw(inputDTO.getPetrolPsw());
            return false;
        }

    }

    @Override
    public String getPetrolMoneyCount(GetPetrolListInputDTO inputDTO) {

        return petrolMapperExtra.sumOfPetrolMoney(inputDTO);
    }

    @Override
    public String getPetrolSaleMoneyCount(GetPetrolSaleInfoInputDTO infoInputDTO) {
        return petrolSalesRecordsMapperExtra.sumOfPetrolSaleMoney(infoInputDTO);
    }

    /**
     * 和数据库油卡对比去除重复
     * @param list
     * @return
     */
    private List<Petrol> removeRepeatPetrolForDB(List<Petrol> list){
        List<Petrol> petrolListNoRepeatForDB = new ArrayList<>();
        for(Petrol p:list){
            if(!PetrolCache.isContainPetorlMap(PetrolCache.AllpetrolMap,p.getPetrolNum())){
                petrolListNoRepeatForDB.add(p);
            }
        }
        return petrolListNoRepeatForDB;
    }

    public JSONResult changePetrolNum(PetrolRechargeInputDTO record){
        boolean isSuccess = false;
        if(record.getUpdatePetrolNum() != null && record.getUpdatePetrolNum() != "")
             isSuccess= petrolSalesRecordsMapperExtra.updatePetrolNum(record) > 0;
        if(isSuccess)
            return new JSONResult("修改成功",200);
        else
            return new JSONResult("修改失敗",500);
    }
}

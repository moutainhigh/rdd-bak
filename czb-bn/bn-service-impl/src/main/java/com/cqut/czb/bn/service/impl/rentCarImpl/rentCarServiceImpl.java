package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.dao.mapper.RentCarMapperExtra;
import com.cqut.czb.bn.entity.dto.rentCar.*;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class rentCarServiceImpl implements RentCarService {
    @Autowired
    private RentCarMapperExtra rentCarMapper;

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
     * 个人获取合同概要信息
     * @param contractId
     * @return List<OneContractInfoDTO>
     */
    @Override
    public List<OneContractInfoDTO> getOneContractInfo(String contractId){
        List<OneContractInfoDTO> oneContractInfoDTOList = new ArrayList<>();
        // TODO 此id需要从redis中获取，暂时写死
        oneContractInfoDTOList =rentCarMapper.getOneContractInfo("1",contractId);
        return oneContractInfoDTOList;
    }

    /**
     * 个人获取合同列表信息
     * @return
     */
    @Override
    public List<PersonalContractDTO> getPersonalContractList(){
        List<PersonalContractDTO> personalContractDTOList = new ArrayList<>();
        // TODO 此id需要从redis中获取，暂时写死
        personalContractDTOList = rentCarMapper.getPersonalContractList("1");

        return personalContractDTOList;
    }

    /**
     * 企业获取合同列表信息
     */
    public List<CompanyContractInfoDTO> getCompanyContractList(){
        List<CompanyContractInfoDTO> companyContractInfoDTOList = new ArrayList<>();
        // TODO 此di需要从redis中获取，暂时写死
        companyContractInfoDTOList = rentCarMapper.getCompanyContractList("2");
        List<ContractAndSignedNumDTO> signedNumDTOList= rentCarMapper.getSignedNumList("2");

        for(CompanyContractInfoDTO cdata:companyContractInfoDTOList){
            for(ContractAndSignedNumDTO sdata:signedNumDTOList){
                if(cdata.getContractId().equals(sdata.getContractId())){
                    cdata.setIsSignNum(sdata.getSignedCarNum());
                    break;
                }
            }
        }

        return companyContractInfoDTOList;
    }

    /**
     * 企业获取合同概要信息列表
     */
    @Override
    public List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(){
        List<OneCompanyContractsPersonDTO> companyPersonList = new ArrayList<>();
        // TODO 此di需要从redis中获取，暂时写死
        companyPersonList = rentCarMapper.getOneCompanyContractInfo("2");

        return companyPersonList;
    }

    /**
     * 新增企业合同
     */
    @Override
    public int addCompanyContract(AddCompanyContractList addCompanyContractList){
        ContractLog contractLog = new ContractLog();

        String contractId = StringUtil.createId();
        contractLog.setRecordId(contractId);
        contractLog.setUserId("3");
        contractLog.setStartTime(addCompanyContractList.getStartTime());
        contractLog.setEndTime(addCompanyContractList.getEndTime());

        try{
            rentCarMapper.insertContractLog(contractLog);
        } catch (Exception e){
            e.printStackTrace();
            return 0;// 插入合同记录出错
        }

        try{
            for(CompanyPersonDTO data:addCompanyContractList.getAddCompanyContractList()){
                PersonCar personCar = new PersonCar();
                personCar.setPersonCarId(StringUtil.createId());
                personCar.setName(data.getName());
                personCar.setPersonId(data.getPersonId());
                personCar.setCarNum(data.getCarNum());
                personCar.setContractId(contractId);
                personCar.setServiceId(data.getService());
                personCar.setPetrolType(data.getPetrolType());
                personCar.setIdentityCode(getIdentityCode(data.getPersonId(), data.getCarNum()));
                rentCarMapper.insertCompanyPerson(personCar);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0; // 插入车辆人员服务出错
        }
        return 1;
    }

    private String getIdentityCode(String id, String num){
        String identityCode = new String();
        String identity1 = id.substring(0,6);
        String identity2 = id.substring(12, 18);
        String identity3 = num.substring(1, 5);
        System.out.println(identity3);
        int[] index1 = new int[3];
        int[] index2 = new int[3];
        index1[2] = -1;
        index2[2] = -1;

        int[] index3 = new int[2];
        index3[1] = -1;

        //如果在随机排列身份证前6为何后6为的过程中，重复出现了相同的下标，则在这中间插入车牌号第2-6中的随机两位字符
        boolean ifInto = false;

        for(int i = 0; index1[2] == -1 && index2[2] == -1; i++ ){
            Integer a = (int) (Math.random()*6);
            Integer b = (int) (Math.random()*6) ;

            if(Arrays.toString(index1).contains(a.toString()) || Arrays.toString(index2).contains(b.toString())){
                System.out.println("c");
                if(!ifInto){
                    for(int j = 0; index3[1] == -1; j++ ){
                        Integer c = (int) (Math.random()*4);
                        if(Arrays.toString(index3).contains(c.toString())){
                            j--;
                            continue;
                        }
                        else{
                            index3[j] = c;
                            identityCode = identityCode + identity3.charAt(c) ;
                        }
                    }
                    ifInto = true;
                }
                i--;
                continue;
            }
            else{
                index1[i] = a;
                index2[i] = b;
                identityCode = identityCode + identity1.charAt(a) + identity2.charAt(b);
            }
        }

        // 没有在上面过程中打印c，则在末尾添加两位字符
        if(!ifInto){
            for(int i = 0; index3[1] == -1; i++ ){
                Integer c = (int) (Math.random()*4);
                if(Arrays.toString(index3).contains(c.toString())){
                    System.out.println("c");
                    i--;
                    continue;
                }
                else{
                    index3[i] = c;
                    identityCode = identityCode + identity3.charAt(c) ;
                }
            }
        }

        return identityCode;
    }
}

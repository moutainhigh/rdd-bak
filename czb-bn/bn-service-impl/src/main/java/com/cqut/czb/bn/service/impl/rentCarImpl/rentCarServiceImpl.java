package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.dao.mapper.RentCarMapperExtra;
import com.cqut.czb.bn.entity.dto.rentCar.*;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONObject;
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
    public personIncome getPersonIncome(String userId){

        return rentCarMapper.getPersonIncome(userId);
    }

    /**
     * 个人获取合同概要信息
     * @param contractId
     * @return List<OneContractInfoDTO>
     */
    @Override
    public List<OneContractInfoDTO> getOneContractInfo(String userId, String contractId){
        List<OneContractInfoDTO> oneContractInfoDTOList = new ArrayList<>();

        oneContractInfoDTOList =rentCarMapper.getOneContractInfo(userId,contractId);
        return oneContractInfoDTOList;
    }

    /**
     * 个人获取合同列表信息
     * @return
     */
    @Override
    public List<PersonalContractDTO> getPersonalContractList(String userId){
        List<PersonalContractDTO> personalContractDTOList = new ArrayList<>();

        personalContractDTOList = rentCarMapper.getPersonalContractList(userId);

        return personalContractDTOList;
    }

    /**
     * 企业获取合同列表信息
     */
    @Override
    public List<CompanyContractInfoDTO> getCompanyContractList(String userId){
        List<CompanyContractInfoDTO> companyContractInfoDTOList = new ArrayList<>();

        companyContractInfoDTOList = rentCarMapper.getCompanyContractList(userId);
        List<ContractAndSignedNumDTO> signedNumDTOList= rentCarMapper.getSignedNumList(userId);

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
    public List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(String userId){
        List<OneCompanyContractsPersonDTO> companyPersonList = new ArrayList<>();
        companyPersonList = rentCarMapper.getOneCompanyContractInfo(userId);

        return companyPersonList;
    }

    /**
     * 新增企业合同
     */
    @Override
    public int addCompanyContract(String userId, AddCompanyContractList addCompanyContractList){
        ContractLog contractLog = new ContractLog();

        String contractId = StringUtil.createId();
        contractLog.setRecordId(contractId);
        contractLog.setUserId(userId);
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

    /**
     * 个人租约新增
     */
    @Override
    public int addPersonContract(String userId, String personId, String identifyCode){
        int isSigned = rentCarMapper.getIsSigned(personId, identifyCode);
        if(isSigned == 1){
            return 99;
        }

        // 如果存在personId、identifyCode都有的车辆人员服务记录，则修改签订状态，并且插入一条合同记录数据
        int ifUpdate = 0;
        try{
            ifUpdate = rentCarMapper.updateCarPerson(personId, identifyCode);
        } catch(Exception e){
            e.printStackTrace();
            return 100;
        }

        // 出错，更新了多条数据的is_signed
        if(ifUpdate > 1){
            return 101; // 数据库中出现相同identifyCode和personId
        } else if(ifUpdate ==0){
            return 103; // 没有更新
        }else if(ifUpdate ==1){
            ContractLog contractLog = new ContractLog();

            String contractId = StringUtil.createId();
            contractLog.setRecordId(contractId);
            contractLog.setUserId(userId);

            // TODO 谭深化——此开始和结束时间存在争议,是用企业的，还是个人自己去填，租金也是，是企业和个人约定好的？怎么算的？——参照真实合同最好
            contractLog.setStartTime("2019-04-02 22:22:22");
            contractLog.setEndTime("2020-04-02 22:22:22");
            contractLog.setRent(500);

            try{
                rentCarMapper.insertContractLogPerson(contractLog);
            } catch (Exception e){
                e.printStackTrace();
                return 102;// 插入个人合同记录出错
            }
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

        //如果在随机排列身份证前6为和后6为的过程中，重复出现了相同的下标，则在这中间插入车牌号第2-6中的随机两位字符
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

    /**
     * 获取个人收益信息列表
     */
    @Override
    public JSONObject getPersonalIncome(String userId){
        List<Income> incomeList = new ArrayList<>();
        incomeList = rentCarMapper.getPersonalIncome(userId);

        JSONObject json = new JSONObject();
        double allIncome = 0;
        for(Income data:incomeList){
            allIncome += data.getAmount();
        }
        json.put("allIncome", allIncome);
        json.put("incomeList", incomeList);


        return json;
    }
}

package com.cqut.czb.bn.service.impl.rentCarImpl;

import com.cqut.czb.bn.dao.mapper.RentCarMapperExtra;
import com.cqut.czb.bn.entity.dto.rentCar.*;
import com.cqut.czb.bn.entity.global.JSONResult;
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
public class RentCarServiceImpl implements RentCarService {
    @Autowired
    private RentCarMapperExtra rentCarMapper;

    /**
     * 获取个人收益记录
     * @return personIncome
     */
    @Override
    public PersonIncome getPersonIncome(String userId){

        return rentCarMapper.getPersonIncome(userId);
    }

    /**
     * 个人获取合同概要信息
     * @param contractId
     * @return List<OneContractInfoDTO>
     */
    @Override
    public OneContractInfoDTO getOneContractInfo(String userId, String contractId){
        return rentCarMapper.getOneContractInfo(userId,contractId);
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

        return rentCarMapper.getCompanyContractListAll(userId);
    }

    /**
     * 企业获取合同概要信息列表
     */
    @Override
    public List<OneCompanyContractsPersonDTO> getOneCompanyContractInfo(String fatherId){
        List<OneCompanyContractsPersonDTO> companyPersonList = new ArrayList<>();
        companyPersonList = rentCarMapper.getOneCompanyContractInfo(fatherId);
        for(OneCompanyContractsPersonDTO data:companyPersonList){
            data.setRent(rentCarMapper.getTaoCanById(data.getRent()) + "/年");
        }

        return companyPersonList;
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
        if (incomeList.size() == 0 || incomeList.get(0).getTime() == null)
            return new JSONObject();
        JSONObject json = new JSONObject();
        double allIncome = 0;
        for(Income data:incomeList){
            allIncome += data.getAmount();
        }
        json.put("allIncome", allIncome);
        json.put("incomeList", incomeList);


        return json;
    }

    /**
     * 合同获取电话号码
     * @param userId
     * @return
     */
    @Override
    public JSONResult getContact(String userId) {
        return new JSONResult("获取成功",200,rentCarMapper.getContact(userId));
    }
}

package com.cqut.czb.bn.service.impl.ThirdBusiness;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetChargeOrderInputDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.MessageManagementService;
import com.cqut.czb.bn.service.ThirdBusinessService.GetUnChargeOrderService;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import com.cqut.czb.bn.util.config.SendMesConfig.MessageModelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetUnChargeOrderServiceImpl implements GetUnChargeOrderService{

    @Autowired
    PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    PetrolSalesRecordsMapper petrolSalesRecordsMapper;

    @Autowired
    ServerOrderServiceImpl serverOrderService;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageManagementService messageManagementService;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public List<GetUnChargeOrderDTO> getUnChargeOrder() {

        List<GetUnChargeOrderDTO> list=petrolSalesRecordsMapperExtra.getUnChargeOrders();
        if(list==null){
            return null;
        }
        else {
            return getPetrolNum(list);
        }
    }

    @Override
    public Boolean InputChargeOrders(String obj) {
        if(obj==null){
            return false;
        }
        List<GetChargeOrderInputDTO> list= JSONObject.parseArray(obj,GetChargeOrderInputDTO.class);
        List<GetChargeOrderInputDTO> successfulOrders=new ArrayList<>();
        List<GetChargeOrderInputDTO> failureOrders=new ArrayList<>();
        //判断哪些没有充值成功
        for(int i=0;i<list.size();i++){
            if("succeed".equals(list.get(i).getState())){
                successfulOrders.add(list.get(i));
            }else if("fail".equals(list.get(i).getState())){
                failureOrders.add(list.get(i));
            }
        }
        //充值成功的进行操作
        SucceedTreatment(successfulOrders);
        //充值失败的进行操作
        FailTreatment(failureOrders);
        return petrolSalesRecordsMapperExtra.inputChargeOrders(successfulOrders)>0;
    }

    //充值成功后发送短信
    public void SucceedTreatment( List<GetChargeOrderInputDTO> list){
        if(list==null){
            return;
        }
        for (int i=0;i<list.size();i++){
            sendSucceedMessage(list.get(i).getOrderId());
        }
    }

    // 发送一个充值失败的消息给管理员
    public void FailTreatment(List<GetChargeOrderInputDTO> list){
        if(list==null){
            return;
        }
        for (int i=0;i<list.size();i++){
            sendFailMessage(list.get(i));
        }
    }

    //给用户发送推送和app消息
    public void sendSucceedMessage(String recordId){
        if(recordId==null){
            return;
        }
        PetrolSalesRecords petrolSalesRecords = petrolSalesRecordsMapper.selectByPrimaryKey(recordId);
//        查询用户的信息
        User user=userMapper.selectByPrimaryKey(petrolSalesRecords.getBuyerId());
        Map<String,String> content = new HashMap<>();
        content.put("petrolKind",  petrolSalesRecords.getPetrolKind().toString());
        content.put("petrolPrice", String.valueOf(petrolSalesRecords.getDenomination()));
        serverOrderService.sendMessage(petrolSalesRecords.getBuyerId(), MesInfo.noticeId.RECHARGE_PETROL_USER.getNoticeId(),content);
        content.put("msgModelId", MessageModelInfo.RECHARGE_SUCCESS_MESSAGE_USER.getMessageModelInfo());
        content.put("receiverId", petrolSalesRecords.getBuyerId());
        content.put("userAccount", user.getUserAccount());
        content.put("petrolNum", petrolSalesRecords.getPetrolNum());
        content.put("petrolDenomination", String.valueOf(petrolSalesRecords.getDenomination()));
        messageManagementService.sendMessageToOne(content, petrolSalesRecords.getBuyerId());
    }


    //充值失败后发送短信
    public void sendFailMessage(GetChargeOrderInputDTO inputDTO){
        if(inputDTO==null){
            return;
        }
        //查出发给谁
        Dict dict=dictMapperExtra.selectDictByName("admin");
        User user=userMapperExtra.findUserByAccount(dict.getContent());
        String recordId=inputDTO.getOrderId();
        PetrolSalesRecords petrolSalesRecords = petrolSalesRecordsMapper.selectByPrimaryKey(recordId);
//        查询用户的信息
        Map<String,String> content = new HashMap<>();
        content.put("petrolNum", petrolSalesRecords.getPetrolNum());
        serverOrderService.sendMessage(user.getUserId(), MesInfo.noticeId.RECHARGE_FAIL.getNoticeId(),content);
        content.put("msgModelId", MessageModelInfo.RECHARGE_FAIL.getMessageModelInfo());
        content.put("message",inputDTO.getMessage());
        content.put("receiverId", user.getUserId());
        content.put("userAccount",user.getUserAccount());
        messageManagementService.sendMessageToOne(content, petrolSalesRecords.getBuyerId());
    }

    public List<GetUnChargeOrderDTO> getPetrolNum(List<GetUnChargeOrderDTO> list){

        for(int i=0;i<list.size();i++){
            list.get(i).setPetrolNum(regExNum(list.get(i).getPetrolNum()));
        }
        return list;
    }

    public String regExNum(String str){
        System.out.println(str);
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        System.out.println( m.replaceAll("").trim());
        return m.replaceAll("").trim();
    }

}

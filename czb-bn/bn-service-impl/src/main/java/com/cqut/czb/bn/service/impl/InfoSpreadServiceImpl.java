package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PartnerMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InfoSpreadServiceImpl implements InfoSpreadService{

    @Autowired
    PartnerMapperExtra partnerMapperExtra;

    @Override
    public PageInfo allPartnerManage(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PartnerDTO> partnerDTOList =  partnerMapperExtra.selectAllPartnerManage(partnerDTO);
        List<PartnerDTO> childCount = partnerMapperExtra.selectAllPartnerCount(partnerDTO);
        if (partnerDTOList.size()!=0&&partnerDTOList!=null&&childCount!=null&&childCount.size()!=0) {
            for (int i = 0; i < partnerDTOList.size(); i++) {
                for (int j=0; j<childCount.size(); j++){
                    if (partnerDTOList.get(i).getUserId().equals(childCount.get(j).getUserId())){
                        partnerDTOList.get(i).setTotalCount(childCount.get(j).getTotalCount());
                        break;
                    }
                }
            }
        }
        return new PageInfo<>(partnerDTOList);
    }

    @Override
    public PartnerDTO getPartnerInfo(PartnerDTO partnerDTO, User user)  {
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        partnerDTO.setUserId(user.getUserId());
        try{
            PartnerDTO partner = partnerMapperExtra.selectHistoryInfo(partnerDTO);
            if(partner!=null&&partner.getMissionStartTime()!=null&&partner.getMissionEndTime()!=null) {
                partner.setMissionStartTime(format.format(format.parse(partner.getMissionStartTime())));
                partner.setMissionEndTime(format.format(format.parse(partner.getMissionEndTime())));        //去掉返回时间末尾的.0
            }
            return partner;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    public List<PartnerDTO> getPartnerChildInfo(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
    }

    @Override
    public PageInfo<PartnerDTO> getNextChildInfo(PartnerDTO partnerDTO, PageDTO pageDTO) {       //查询下一级的列表及每个子级的下一级人数
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PartnerDTO> nextChild = partnerMapperExtra.selectNextChild(partnerDTO);
        List<PartnerDTO> nextChildMoney = partnerMapperExtra.selectNextChildMoney(partnerDTO);
        for(int i=0;i<nextChild.size();i++){
            for (int j=0;j<nextChildMoney.size();j++){
                if (nextChild.get(i).getUserId().equals(nextChildMoney.get(j).getUserId())){
                    nextChild.get(i).setTotalMoney(nextChildMoney.get(j).getTotalMoney());
                    nextChild.get(i).setNearTime(nextChildMoney.get(j).getNearTime());
                    break;
                }
            }
        }
        return new PageInfo<>(nextChild);
    }

    @Override
    public ArrayList getNewChildByDay(PartnerDTO partnerDTO,User user) {
        partnerDTO.setUserId(user.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        ArrayList childByDay = new ArrayList<>();
        List<PartnerDTO> children = partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
        int[] promotion = new int[7];
        int[] consumption = new int[7];
        try {
            partnerDTO.setMonthTime(format.format(new Date()));
            for (int i=0;i<7;i++) {
                if (children!=null&&children.size()!=0) {
                    promotion[i] = (partnerMapperExtra.selectPartnerChildInfoWithDay(children, partnerDTO).size());
                    consumption[i] = (partnerMapperExtra.selectPartnerChildWithDayMoney(children, partnerDTO).size());
                }else {
                    promotion[i] = 0;
                    consumption[i] = 0;
                }
                c.setTime(format.parse(partnerDTO.getMonthTime()));
                c.add(Calendar.DATE, -1);
                partnerDTO.setMonthTime(format.format(c.getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        childByDay.add(promotion);
        childByDay.add(consumption);
        return childByDay;
    }

    @Override
    public PartnerDTO getTotalInfo(PartnerDTO partnerDTO,User user) {
        if (partnerDTO.getUserId()==null){
        partnerDTO.setUserId(user.getUserId());
        }
        List<PartnerDTO> totalChilds = partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
        PartnerDTO partner = partnerMapperExtra.selectPartner(partnerDTO);
        if(totalChilds!=null&&totalChilds.size()!=0) {
            partner.setTotalCount(totalChilds.size());
            Double totalMoney = (Double) changeToBigDecimal(getChildTotalMoney(totalChilds));
            partner.setTotalMoney((totalMoney));
        }else {
            partner.setTotalCount(0);
            partner.setTotalMoney(0.00);
        }
        Double nextTotalMoney = 0.00;
        List<PartnerDTO> nextChildMoney = partnerMapperExtra.selectNextChildMoney(partnerDTO);
        if (nextChildMoney!=null&&nextChildMoney.size()!=0) {
            for (int i = 0; i < nextChildMoney.size(); i++) {
                if (nextChildMoney.get(i).getTotalMoney() != null) {
                    nextTotalMoney += nextChildMoney.get(i).getTotalMoney();
                }
            }
        }
        nextTotalMoney = (Double) changeToBigDecimal(nextTotalMoney);
        partner.setNextTotalMoney(nextTotalMoney);
        return partner;
    }

    public List<PartnerDTO> getPartnerChildInfoWithTime(List<PartnerDTO> list,PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfoWithTime(list,partnerDTO);
    }

    public List<PartnerDTO> getPartnerChildInfoWithMoney(List<PartnerDTO> list,PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfoWithMoney(list,partnerDTO);
    }

    //子级总数
    public int getChildCount(List<PartnerDTO> partnerDTOS){
        int count = 0;          //合伙人下级数量
      if (partnerDTOS!=null&&partnerDTOS.size()!=0){
          count = count + partnerDTOS.size();       //如果有子级就加
          for (int i = 0; i<partnerDTOS.size(); i++){
              count+=getChildCount(partnerDTOS.get(i).getChildPartner());
          }
      }
      return count;
    }
    //取出子级的user_id
    public List<PartnerDTO> getChildIds(List<PartnerDTO> ids,List<PartnerDTO> partnerDTOS){
        if (partnerDTOS!=null&&partnerDTOS.size()!=0) {
            for (int i = 0; i < partnerDTOS.size(); i++) {
                ids.add(partnerDTOS.get(i));
                if(partnerDTOS.get(i).getChildPartner()!=null){
                getChildIds(ids, partnerDTOS.get(i).getChildPartner());}
            }
        }
        return ids;
    }
    //取出父级的user_id
    public List<PartnerDTO> getPartnerIds(List<PartnerDTO> ids,List<PartnerDTO> partnerDTOS){
        if (partnerDTOS!=null&&partnerDTOS.size()!=0) {
            for (int i = 0; i < partnerDTOS.size(); i++) {
                ids.add(partnerDTOS.get(i));
                if (partnerDTOS.get(i).getPartnerList()!=null) {
                    getPartnerIds(ids, partnerDTOS.get(i).getPartnerList());
                }
            }
        }
        return ids;
    }



    //子级消费总金额
    public Double getChildTotalMoney(List<PartnerDTO> partnerDTOS){
        Double count = 0.0;          //合伙人下级消费总数
        if (partnerDTOS!=null&&partnerDTOS.size()!=0){

            for (int i = 0; i<partnerDTOS.size(); i++){
                if (partnerDTOS.get(i).getTotalMoney()!=null) {
                    count = count + partnerDTOS.get(i).getTotalMoney();       //如果有子级就加
                    System.out.println("金额"+partnerDTOS.get(i).getTotalMoney()+"第"+i+"位"+partnerDTOS.get(i).getUserAccount());
                    System.out.println("总金额："+count);
                }
            }
        }
        return count;
    }

    public PartnerDTO selectAllPartnerInfo(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
    }

    public Boolean addChildPromotion(PartnerDTO partnerDTO){
        if (partnerDTO.getUserId()!=null){
            PartnerDTO partner = partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
            if (partner!=null) {
                partnerDTO.setFirstPartner(partner.getFirstPartner());
                partnerDTO.setSecondPartner(partner.getSecondPartner());
                return (partnerMapperExtra.addChildPromotion(partnerDTO) > 0);
            }else {
                return false;
            }
        }else {
            return null;
        }
    }
    public Boolean addChildConsumer(PartnerDTO partnerDTO){
        if (partnerDTO.getUserId()!=null){
            PartnerDTO partner = partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
            if (partner!=null) {
                partnerDTO.setFirstPartner(partner.getFirstPartner());
                partnerDTO.setSecondPartner(partner.getSecondPartner());
                return (partnerMapperExtra.addChildConsumer(partnerDTO) > 0);
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public PageInfo<PartnerDTO> getChildByName(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PartnerDTO> childByName = partnerMapperExtra.selectChildByName(partnerDTO);
        List<PartnerDTO> childMoneyByName = partnerMapperExtra.selectChildMoneyByName(partnerDTO);
        for(int i=0 ; i<childByName.size() ; i++){
            for (int j=0;j<childMoneyByName.size();j++){
                if (childByName.get(i).getUserId().equals(childMoneyByName.get(j).getUserId())){
                    childByName.get(i).setTotalMoney(childMoneyByName.get(j).getTotalMoney());
                    childByName.get(i).setNearTime(childMoneyByName.get(j).getNearTime());
                    break;
                }
            }
        }
        return new PageInfo<>(childByName);
    }

    @Override
    public PageInfo<PartnerDTO> myTotalChildMoney(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PartnerDTO> myTotalChildMoney = partnerMapperExtra.selectMyTotalChildMoney(partnerDTO);
        return new PageInfo<>(myTotalChildMoney);
    }

    public Double changeToBigDecimal(Double num){
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue(); //直接去掉金额小数点两位后面的数
        return num;
    }

}

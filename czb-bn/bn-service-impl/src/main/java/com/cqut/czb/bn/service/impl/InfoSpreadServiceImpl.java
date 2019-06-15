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
    public PartnerDTO getPartnerInfo(PartnerDTO partnerDTO, User user)  {
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        partnerDTO.setUserId(user.getUserId());
//        Calendar c = Calendar.getInstance();
//        try {
//            partnerDTO.setUserId(user.getUserId());
//            if (month.parse(month.format(new Date())).compareTo(month.parse(partnerDTO.getMonthTime()))!=0) { //如果导出时间不为当月
//                PartnerDTO partner = partnerMapperExtra.selectHistoryInfo(partnerDTO);
//                partner.setMissionStartTime(format.format(format.parse(partner.getMissionStartTime())));
//                partner.setMissionEndTime(format.format(format.parse(partner.getMissionEndTime())));
//                return partner;
//            }else {
//                PartnerDTO partner = partnerMapperExtra.selectPartnerInfo(partnerDTO);      //找到合伙人指标数据
//                partner.setMissionStartTime(format.format(format.parse(partner.getMissionStartTime())));
//                partner.setMissionEndTime(format.format(format.parse(partner.getMissionEndTime())));
////                partner.setChildPartner(getPartnerChildInfo(partnerDTO));                      //找到合伙人当月的子级用户
////                PartnerDTO historyChild = new PartnerDTO();                                    //创建新对象用以查询上一个月的数据
////                historyChild.setUserId(partner.getUserId());
////                c.setTime(format.parse(partnerDTO.getMonthTime()));
////                c.add(Calendar.MONTH,-1);
////                Date lastMonth = c.getTime();
////                historyChild.setMonthTime(format.format(lastMonth));                           //取上个月的时间
////                PartnerDTO lastMonthInfo = getPartnerInfo(historyChild);                    //查询上个月合伙人的指标数据
//                List<PartnerDTO> totalChild = getPartnerChildInfo(partnerDTO);
//                List<PartnerDTO> ids = new ArrayList<>();
//                List<PartnerDTO> child = getPartnerChildInfoWithTime(getChildIds(ids,totalChild),partnerDTO);
//                if (child!=null&&child.size()!=0) {
//                    partner.setChildPartner(child);            //获取指定月份中注册的子级用户
//                    partner.setActualPromotionNumber(getChildCount(partner.getChildPartner()));
//                    partner.setActualNewConsumer(getChildCount(getPartnerChildInfoWithMoney(ids,partnerDTO)));
//                }
//                return partner;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new PartnerDTO();
        try{
            PartnerDTO partner = partnerMapperExtra.selectHistoryInfo(partnerDTO);
            partner.setMissionStartTime(format.format(format.parse(partner.getMissionStartTime())));
            partner.setMissionEndTime(format.format(format.parse(partner.getMissionEndTime())));        //去掉返回时间末尾的.0
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
                List<PartnerDTO> ids = new ArrayList<>();
                promotion[i]=(getChildCount(partnerMapperExtra.selectPartnerChildInfoWithDay(getChildIds(ids,children),partnerDTO)));
                consumption[i]=(getChildCount(partnerMapperExtra.selectPartnerChildWithDayMoney(ids,partnerDTO)));
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
        partner.setTotalCount(getChildCount(totalChilds));
        Double totalMoney = getChildTotalMoney(totalChilds);
        partner.setTotalMoney((totalMoney));
        Double nextTotalMoney = 0.00;
        for (int i=0;i<totalChilds.size();i++){
            if (totalChilds.get(i).getTotalMoney()!=null){
            nextTotalMoney+=totalChilds.get(i).getTotalMoney();}
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
                getChildIds(ids, partnerDTOS.get(i).getChildPartner());
            }
        }
        return ids;
    }
    //取出父级的user_id
    public List<PartnerDTO> getPartnerIds(List<PartnerDTO> ids,List<PartnerDTO> partnerDTOS){
        if (partnerDTOS!=null&&partnerDTOS.size()!=0) {
            for (int i = 0; i < partnerDTOS.size(); i++) {
                ids.add(partnerDTOS.get(i));
                getPartnerIds(ids, partnerDTOS.get(i).getPartnerList());
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
                }
                getChildTotalMoney(partnerDTOS.get(i).getChildPartner());
            }
        }
        return count;
    }

    public List<PartnerDTO> selectAllPartnerInfo(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
    }

    public Boolean addChildPromotion(PartnerDTO partnerDTO){
        if (partnerDTO.getUserId()!=null){
            List<PartnerDTO> partnerList = partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
            List<PartnerDTO> ids = new ArrayList<>();
            return (partnerMapperExtra.addChildPromotion(getPartnerIds(ids,partnerList),partnerDTO)>0);
        }else {
            return null;
        }
    }
    public Boolean addChildConsumer(PartnerDTO partnerDTO){
        if (partnerDTO.getUserId()!=null){
            List<PartnerDTO> partnerList = partnerMapperExtra.selectAllPartnerInfo(partnerDTO);
            List<PartnerDTO> ids = new ArrayList<>();
            return (partnerMapperExtra.addChildConsumer(getPartnerIds(ids,partnerList),partnerDTO)>0);
        }else {
            return null;
        }
    }

    public Double changeToBigDecimal(Double num){
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue(); //直接去掉金额小数点两位后面的数
        return num;
    }

}

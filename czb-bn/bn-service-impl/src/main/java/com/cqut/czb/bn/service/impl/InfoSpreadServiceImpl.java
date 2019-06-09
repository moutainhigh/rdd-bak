package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PartnerMapperExtra;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.InfoSpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InfoSpreadServiceImpl implements InfoSpreadService{

    @Autowired
    PartnerMapperExtra partnerMapperExtra;

    @Override
    public PartnerDTO getPartnerInfo(PartnerDTO partnerDTO, User user)  {
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            partnerDTO.setUserId(user.getUserId());
            if (month.parse(month.format(new Date())).compareTo(month.parse(partnerDTO.getMonthTime()))!=0) { //如果导出时间不为当月
                return partnerMapperExtra.selectHistoryInfo(partnerDTO);
            }else {
                PartnerDTO partner = partnerMapperExtra.selectPartnerInfo(partnerDTO);      //找到合伙人指标数据
//                partner.setChildPartner(getPartnerChildInfo(partnerDTO));                      //找到合伙人当月的子级用户
//                PartnerDTO historyChild = new PartnerDTO();                                    //创建新对象用以查询上一个月的数据
//                historyChild.setUserId(partner.getUserId());
//                c.setTime(format.parse(partnerDTO.getMonthTime()));
//                c.add(Calendar.MONTH,-1);
//                Date lastMonth = c.getTime();
//                historyChild.setMonthTime(format.format(lastMonth));                           //取上个月的时间
//                PartnerDTO lastMonthInfo = getPartnerInfo(historyChild);                    //查询上个月合伙人的指标数据
                partner.setChildPartner(getPartnerChildInfoWithTime(partnerDTO));            //获取指定月份中注册的子级用户
                partner.setActualPromotionNumber(getChildCount(partner.getChildPartner()));
                partner.setActualNewConsumer(getChildCount(getPartnerChildInfoWithMoney(partnerDTO)));
                return partner;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PartnerDTO();
    }

    public List<PartnerDTO> getPartnerChildInfo(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
    }

    @Override
    public List<PartnerDTO> getNextChildInfo(PartnerDTO partnerDTO) {       //查询下一级的列表及每个子级的下一级人数
        return partnerMapperExtra.selectNextChild(partnerDTO);
    }

    @Override
    public List<PartnerDTO> getNewChildByDay(PartnerDTO partnerDTO,User user) {
        partnerDTO.setUserId(user.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        List<PartnerDTO> childByDay = new ArrayList<>();
        try {
            partnerDTO.setMonthTime(format.format(new Date()));
            for (int i=0;i<7;i++) {
                PartnerDTO partner = new PartnerDTO();
                partner.setActualPromotionNumber(getChildCount(partnerMapperExtra.selectPartnerChildInfoWithDay(partnerDTO)));
                partner.setActualNewConsumer(getChildCount(partnerMapperExtra.selectPartnerChildWithDayMoney(partnerDTO)));
                childByDay.add(partner);
                c.setTime(format.parse(partnerDTO.getMonthTime()));
                c.add(Calendar.DATE, -1);
                partnerDTO.setMonthTime(format.format(c.getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return childByDay;
    }

    @Override
    public PartnerDTO getTotalInfo(PartnerDTO partnerDTO,User user) {
        partnerDTO.setUserId(user.getUserId());
        List<PartnerDTO> totalChilds = partnerMapperExtra.selectPartnerChildInfo(partnerDTO);
        PartnerDTO partner = new PartnerDTO();
        partner.setTotalCount(getChildCount(totalChilds));
        partner.setTotalMoney(getChildTotallMoney(totalChilds));
        return partner;
    }

    public List<PartnerDTO> getPartnerChildInfoWithTime(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfoWithTime(partnerDTO);
    }

    public List<PartnerDTO> getPartnerChildInfoWithMoney(PartnerDTO partnerDTO){
        return partnerMapperExtra.selectPartnerChildInfoWithMoney(partnerDTO);
    }

    //子级总数
    public int getChildCount(List<PartnerDTO> partnerDTOS){
        int count = 0;          //合伙人下级数量
      while (partnerDTOS!=null&&partnerDTOS.size()!=0){
          count = count + partnerDTOS.size()-1;       //如果有子级就加
          for (int i = 0; i<partnerDTOS.size(); i++){
              getChildCount(partnerDTOS.get(i).getChildPartner());
          }
      }
      return count;
    }
    //子级消费总金额
    public Double getChildTotallMoney(List<PartnerDTO> partnerDTOS){
        Double count = 0.0;          //合伙人下级消费总数
        while (partnerDTOS!=null&&partnerDTOS.size()!=0){

            for (int i = 0; i<partnerDTOS.size(); i++){
                count = count + partnerDTOS.get(i).getTotalMoney();       //如果有子级就加
                getChildTotallMoney(partnerDTOS.get(i).getChildPartner());
            }
        }
        return count;
    }

}
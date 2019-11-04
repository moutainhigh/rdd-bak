package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerBecomeTimeDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipMoney;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.SubPartnerDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.VipCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerMapperExtra {
    //普通合伙人查询下级
    PartnerDTO selectSubVipSecond(PartnerDTO partnerDTO);
    //事业合伙人查询下级
    PartnerDTO selectSubVipFirst(PartnerDTO partnerDTO);
    //事业合伙人查询被普通合伙人分走的下级
    PartnerDTO selectSubVipThird(PartnerDTO partnerDTO);

    List<PartnerDTO> selectAllPartnerManage(PartnerDTO partnerDTO);

    List<PartnerDTO> selectAllPartnerCount(PartnerDTO partnerDTO);

    List<PartnerDTO> selectMyTotalChildMoney(PartnerDTO partnerDTO);

    List<PartnerDTO> selectChildMoneyByName(PartnerDTO partnerDTO);

    List<PartnerDTO> selectChildByName(PartnerDTO partnerDTO);

    List<PartnerDTO> selectAllPartner();

    int addChildPromotion (PartnerDTO partner);

    int addChildConsumer (PartnerDTO partner);

     List<PartnerDTO> selectPartnerChildInfo(PartnerDTO partnerDTO);

    List<PartnerDTO> selectNoPartnerChildInfo(PartnerDTO partnerDTO);

    PartnerDTO selectAllPartnerInfo(PartnerDTO partnerDTO);

     PartnerDTO selectPartnerInfo(PartnerDTO partnerDTO);

     PartnerDTO selectPartner(PartnerDTO partnerDTO);

     PartnerDTO selectMyPartner(String userId);

     PartnerDTO selectHistoryInfo(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithTime(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithDay(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithMoney(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildWithDayMoney(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectNextChild(PartnerDTO partnerDTO);

    List<PartnerDTO> selectNextChildMoney(PartnerDTO partnerDTO);


//合伙人vip收益初始化数据用方法
    List<PartnerBecomeTimeDTO> selectPartnerBecomeTime();

    List<SubPartnerDTO> selectSubPartner(String userId);

    PartnerVipMoney selectFirstPartnerSubVip(PartnerBecomeTimeDTO partnerBecomeTimeDTO);

    PartnerVipMoney selectFirstPartnerSubPetrol(PartnerBecomeTimeDTO partnerBecomeTimeDTO);

    PartnerVipMoney selectSecondPartnerSubVip(PartnerBecomeTimeDTO partnerBecomeTimeDTO);

    PartnerVipMoney selectSecondPartnerSubPetrol(PartnerBecomeTimeDTO partnerBecomeTimeDTO);

    PartnerVipMoney selectFirstBySecondPartnerSubVip(SubPartnerDTO subPartnerDTO);

    PartnerVipMoney selectFirstBySecondPartnerSubPetrol(SubPartnerDTO subPartnerDTO);

    PartnerVipMoney selectFirstNotBySecondPartnerSubVip(SubPartnerDTO subPartnerDTO);

    PartnerVipMoney selectFirstNotBySecondPartnerSubPetrol(SubPartnerDTO subPartnerDTO);

    VipCountDTO selectFirstPartnerSubVipCount(String userId);

    VipCountDTO selectSecondPartnerSubVipCount(PartnerBecomeTimeDTO partnerBecomeTimeDTO);

}

package com.cqut.czb.bn.service.integral;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.integral.*;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.integral.IntegralDeductionInfo;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IntegralService {

    /**
     * 获得用户当前总积分
     * @param userId
     * @return
     */
    JSONResult getCurrentTotalIntegral(String userId, String userAccount);

    /**
     * 积分明细
     */
    PageInfo<IntegralLogDTO> getIntegralDetail(String userId, String userAccount, PageDTO pageDTO);

    /**
     * 赠送积分明细
     * @param userId
     * @return
     */
    List<IntegralDetailsDTO> getOfferIntegralDetail(String userId);

    JSONResult getExchangeDetails(String integralExchangeId);

    /**
     * 购买积分
     */
    JSONResult purchaseIntegral(IntegralInfoDTO integralInfoDTO);

    /**
     * 兑换积分
     */
    JSONResult exchangeIntegral(IntegralExchangeDTO integralExchangeDTO, String userId);

    /**
     * 赠送积分
     */
    JSONResult giveIntegral(IntegralExchangeDTO integralExchangeDTO);

    /**
     * 商品抵扣最高额度
     */
    JSONResult getMaxDeductionAmount(String commodityId);

    /**
     * 积分抵扣
     * @param integralInfoDTO
     * @return
     */
    JSONResult deduction(IntegralInfoDTO integralInfoDTO);

    /**
     * 通过电话号码赠送
     * @param providerId
     * @param receiverPhone
     * @param integralAmount
     * @return
     */
    JSONResult offerIntegralByPhone(String providerId, String receiverPhone, Integer integralAmount);

    /**
     * 创建积分兑换码
     * @param integralExchange
     * @return
     */
    JSONResult createExchangeCode(IntegralExchangeDTO integralExchange);

    /**
     * 获取所有用户积分信息
     * @param userAccount
     * @return
     */
    PageInfo<IntegralInfoDTO> selectIntegralInfoList(String userAccount, PageDTO pageDTO);

    PageInfo<IntegralDistributionDTO> getIntegralDistributionDetails(String userAccount, IntegralDistributionDTO integralDistributionDTO);

    /**
     * 手机号积分补贴
     * @param receiverPhones
     * @param integralAmount
     * @return
     */
    JSONResult subsidyIntegralByPhone(List<String> receiverPhones, Integer integralAmount);

    /**
     * 获取积分购买比例
     * @return
     */
    JSONResult getIntegralRate();

    /**
     * 更新积分购买比例
     * @param dictInputDTO
     * @return
     */
    JSONResult updateIntegralRate(DictInputDTO dictInputDTO);

    /**
     * 查询log信息
     */
    IntegralLogDTO getIntegralInfo(String userId);

    /**
     * 新增积分购买面额
     * @param integralManageDTO
     * @return
     */
    JSONResult insertIntegralValue(IntegralManageDTO integralManageDTO);

    /**
     * 修改积分购买面额
     * @param integralManageDTO
     * @return
     */
    JSONResult updateIntegralValue(IntegralManageDTO integralManageDTO);

    /**
     * 删除积分购买面额
     * @param integralManageDTO
     * @return
     */
    JSONResult deleteIntegralValue(IntegralManageDTO integralManageDTO);

    /**
     * 获取积分购买面额
     * @return
     */
    PageInfo<IntegralManageDTO> getIntegralValueList(PageDTO pageDTO);

    List<User> fuzzyQueryUserPhone(String phone);

    PageInfo<IntegralExchangeLogIdDTO> getExchangeLogDetails(PageDTO pageDTO, IntegralExchangeLogIdDTO integralExchangeLogIdDTO);

    JSONResult insertMaxDeductionAmount(IntegralDeductionInfo integralDeductionInfo);

    JSONResult updateMaxDeductionAmount(IntegralDeductionInfo integralDeductionInfo);

    /**
     * 根据商品类型查询下属商品
     * @param type
     * @return
     */
    JSONResult getCommodityByType(String type);

    /**
     * 根据商品类型分页查询商品
     * @param integralDeductionInfo
     * @return
     */
    PageInfo<IntegralDeductionInfoDTO> getCommodityByPage(IntegralDeductionInfo integralDeductionInfo, PageDTO pageDTO);
}

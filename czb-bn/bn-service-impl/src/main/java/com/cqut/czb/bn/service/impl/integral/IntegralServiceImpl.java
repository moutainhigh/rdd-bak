package com.cqut.czb.bn.service.impl.integral;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingCommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentCommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.*;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralDeductionInfoMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralInfoMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralLogMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegrallogMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.integral.*;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxAttributeDTO;
import com.cqut.czb.bn.entity.entity.integral.*;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.RSA.RSAUtils;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    IntegralServiceImpl integralServiceImpl;

    @Autowired
    IntegrallogMapperExtra integrallogMapperExtra;

    @Autowired
    IntegralDeductionInfoMapperExtra integralDeductionInfoMapperExtra;

    @Autowired
    IntegralDeductionInfoMapper integralDeductionInfoMapper;

    @Autowired
    IntegralInfoMapperExtra integralInfoMapperExtra;

    @Autowired
    IntegralInfoMapper integralInfoMapper;

    @Autowired
    IntegralLogMapper integralLogMapper;

    @Autowired
    IntegralExchangeMapper integralExchangeMapper;

    @Autowired
    IntegralExchangeMapperExtra integralExchangeMapperExtra;

    @Autowired
    IntegralExchangeLogIdMapper integralExchangeLogIdMapper;

    @Autowired
    IntegralExchangeLogIdMapperExtra integralExchangeLogIdMapperExtra;

    @Autowired
    IntegralPurchaseRecordMapperExtra integralPurchaseRecordMapperExtra;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    private BusinessProcessService refuelingCard;

    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    WeChatCommodityMapperExtra weChatCommodityMapperExtra;

    @Autowired
    DirectChargingCommodityMapperExtra directChargingCommodityMapperExtra;

    @Autowired
    EquityPaymentCommodityMapperExtra equityPaymentCommodityMapperExtra;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    IntegralManageMapper integralManageMapper;

    public JSONResult getCurrentTotalIntegral(String userId, String userAccount) {
        if (userAccount == null || userAccount.equals("")) {
            return new JSONResult(integralInfoMapperExtra.selectGainAndLossIntegralByUserId(userId));
        } else {
            User user = userMapperExtra.findUserByAccount(userAccount);
            return new JSONResult(integralInfoMapperExtra.selectGainAndLossIntegralByUserId(user.getUserId()));
        }
    }

    @Override
    public PageInfo<IntegralLogDTO> getIntegralDetail(String userId, String userAccount, PageDTO pageDTO) {
        List<IntegralLogDTO> integralLogDTOList = null;
        if (userAccount == null || userAccount.equals("")) {
            PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
            integralLogDTOList = integrallogMapperExtra.getIntegralDetailsList(userId);
        } else {
            User user = userMapperExtra.findUserByAccount(userAccount);
            PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
            integralLogDTOList = integrallogMapperExtra.getIntegralDetailsList(user.getUserId());
        }
        return new PageInfo<>(integralLogDTOList);
    }

    @Override
    public List<IntegralDetailsDTO> getOfferIntegralDetail(String userId) {
        List<IntegralDetailsDTO> integralDetailsDTOList = integrallogMapperExtra.getOfferIntegralDetail(userId);

        return integralDetailsDTOList;
    }

    @Override
    public JSONResult getExchangeDetails(String integralExchangeId) {
        IntegralExchange exchangeDetails = integralExchangeMapper.selectByPrimaryKey(integralExchangeId);

        return new JSONResult(exchangeDetails);
    }

    @Override
    public JSONResult purchaseIntegral(IntegralInfoDTO integralInfoDTO) {
        return null;
    }

    @Override
    public JSONResult exchangeIntegral(IntegralExchangeDTO integralExchangeDTO, String userId) {
        // 主人兑换码信息
        IntegralExchange integralExchangeMng;

        // 兑换码主人的信息
        final IntegralInfo[] integralInfoMng = new IntegralInfo[1];

        synchronized (Thread.currentThread()) {
            integralExchangeMng = integralExchangeMapperExtra.selectByIntegralExchange(integralExchangeDTO);
            if (integralExchangeMng == null) {
                return new JSONResult("兑换码不存在!");
            }
            if (integralExchangeMng.getFailureTime().before(new Date())) {
                return new JSONResult("兑换码已过期!");
            }
            // 积分兑换明细信息
            IntegralExchangeLogId integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId = integralExchangeLogIdMapperExtra.selectByIntegralExchange(integralExchangeLogId);

            if (integralExchangeLogId != null) {
                return new JSONResult("你已经兑换过该积分!");
            }
            integralInfoMng[0] = integralInfoMapperExtra.selectByUserId(integralExchangeMng.getExchangeSourceId());

            if (integralExchangeMng.getExchangeAmount() > integralInfoMng[0].getCurrentTotal()) {
                return new JSONResult("对方积分不足!");
            }
            if (integralExchangeMng.getExchangeTimesCurrent() == integralExchangeMng.getExchangeTimesTotal()) {
                return new JSONResult("该二维码(兑换码)已被兑换完!");
            }

            if (integralExchangeMng.getExchangeTimesCurrent() + 1 == integralExchangeMng.getExchangeTimesTotal()) {
                integralExchangeMng.setIsComplete(1);
            }
            integralExchangeMapperExtra.updateByPrimaryKeySync(integralExchangeMng);
            integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeLogId(StringUtil.createId());
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId.setCreateAt(new Date());
            integralExchangeLogIdMapper.insert(integralExchangeLogId);
        }
        // 新增兑换码主人积分记录
        IntegralLog integralLog = new IntegralLog();
        new Thread(()-> {
            integralLog.setIntegralLogId(StringUtil.createId());
            integralLog.setIntegralInfoId(integralInfoMng[0].getIntegralInfoId());
            integralLog.setUserId(integralInfoMng[0].getUserId());
            integralLog.setIntegralLogType(1);
            integralLog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
            integralLog.setBeforeIntegralAmount(integralInfoMng[0].getCurrentTotal());
            integralLog.setRemark("赠予他人");
            integralLog.setCreateAt(new Date());
            integralLog.setOrderId(StringUtil.createId());
            integralLogMapper.insert(integralLog);
            if (integralExchangeMng.getExchangeType() == 2) {
                integralInfoMng[0] = integralInfoMapperExtra.selectByUserId(integralExchangeMng.getExchangeSourceId());
                integralInfoMng[0].setCurrentTotal(-integralExchangeMng.getExchangeAmount());
                integralInfoMng[0].setGotTotal(0);
                integralInfoMng[0].setUpdateAt(new Date());
                integralServiceImpl.updateIntegralInfo(integralInfoMng[0]);
            }
        }).start();

        // 新增兑换人积分记录
        new Thread(()->{
            // 兑换者信息
            integralInfoMng[0] = integralInfoMapperExtra.selectByUserId(userId);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            integralLog.setIntegralLogId(StringUtil.createId());
            integralLog.setIntegralInfoId(integralInfoMng[0].getIntegralInfoId());
            integralLog.setUserId(userId);
            integralLog.setIntegralLogType(3);
            integralLog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
            integralLog.setBeforeIntegralAmount(integralInfoMng[0].getCurrentTotal());
            integralLog.setRemark("被赠予");
            integralLog.setCreateAt(new Date());
            integralLog.setOrderId(StringUtil.createId());
            integralLogMapper.insert(integralLog);

            integralInfoMng[0] = integralInfoMapperExtra.selectByUserId(userId);
            integralInfoMng[0].setCurrentTotal(integralExchangeMng.getExchangeAmount());
            integralInfoMng[0].setGotTotal(integralExchangeMng.getExchangeAmount());
            integralServiceImpl.updateIntegralInfo(integralInfoMng[0]);
        }).start();
        return new JSONResult("恭喜你成功兑换积分!");
    }

    @Override
    public JSONResult giveIntegral(IntegralExchangeDTO integralExchangeDTO) {
        return null;
    }

    @Override
    public JSONResult getMaxDeductionAmount(String commodityId) {
        return new JSONResult(integralDeductionInfoMapperExtra.getMaxDeductionAmount(commodityId));
    }

    @Override
    public JSONResult deduction(IntegralInfoDTO integralInfoDTO) {
        return null;
    }

    @Override
    public JSONResult offerIntegralByPhone(String providerId , String receiverPhone, Integer integralAmount) {
        User receiver = userMapperExtra.findUserByAccount(receiverPhone);

        if(receiver == null) {
            return new JSONResult("此电话号码不存在");
        }

        IntegralInfo providerInfo = integralInfoMapperExtra.selectByUserId(providerId);

        if (providerInfo.getCurrentTotal() < integralAmount) {
            return new JSONResult("用户您的账号的积分余额不足");
        }

        IntegralInfo receiverInfo = null;
        int affectRow = 0;
        Date preDate = new Date();
        Date outDate;
        do {
            receiverInfo = integralInfoMapperExtra.selectByUserId(receiver.getUserId());
            providerInfo.setCurrentTotal(providerInfo.getCurrentTotal() - integralAmount);
            receiverInfo.setCurrentTotal(receiverInfo.getCurrentTotal() + integralAmount);
            receiverInfo.setGotTotal(receiverInfo.getGotTotal() + integralAmount);
            if (integralInfoMapperExtra.updateByPrimaryKeySync(providerInfo) == 1 && integralInfoMapperExtra.updateByPrimaryKeySync(receiverInfo) == 1) {
                affectRow = 1;
            } else {
                affectRow = 0;
            }

            outDate = new Date();
            if (outDate.getTime() - preDate.getTime() > 3000) {
                return new JSONResult("响应超时请重试!");
            }
        } while (affectRow == 0);

        // 赠送人的积分记录
        IntegralLog integralLog = new IntegralLog();
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(providerInfo.getIntegralInfoId());
        integralLog.setUserId(providerInfo.getUserId());
        integralLog.setIntegralLogType(2);
        integralLog.setIntegralAmount(integralAmount);
        integralLog.setBeforeIntegralAmount(providerInfo.getCurrentTotal());
        integralLog.setRemark("手机号赠送");
        integralLog.setCreateAt(new Date());
        integralLog.setUpdateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);

        // 新增被赠送人积分记录
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(receiverInfo.getIntegralInfoId());
        integralLog.setUserId(receiver.getUserId());
        integralLog.setIntegralLogType(3);
        integralLog.setIntegralAmount(integralAmount);
        integralLog.setBeforeIntegralAmount(receiverInfo.getCurrentTotal());
        integralLog.setRemark("被赠予");
        integralLog.setCreateAt(new Date());
        integralLog.setUpdateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);

        return new JSONResult("恭喜你赠送积分成功!");
    }

    @Override
    public JSONResult createExchangeCode(IntegralExchangeDTO integralExchange) {
        IntegralInfo userIntegralInfo = integralInfoMapperExtra.selectByUserId(integralExchange.getExchangeSourceId());
        if (integralExchange.getExchangeType() == 2 && userIntegralInfo.getCurrentTotal() < integralExchange.getExchangeAmount() * integralExchange.getExchangeTimesTotal()) {
            return new JSONResult("你的积分不足");
        }

        if (integralExchange.getFailureTime().compareTo(new Date()) < 0) {
            return new JSONResult("失效时间比当前时间早");
        }

        integralExchange.setIntegralExchange(StringUtil.createId());
        integralExchange.setExchangeTimesCurrent(0);
        integralExchange.setIsComplete(0);
        integralExchange.setCreateAt(new Date());
        integralExchange.setUpdateAt(new Date());

        try {
            integralExchange.setExchangeCode(RSAUtils.cipherToEXcode(RSAUtils.publicEncrypt(integralExchange.getIntegralExchange(),RSAUtils.getPublicKey(RSAUtils.publicKey))));
            int n = integralExchangeMapperExtra.insert(integralExchange);
            if (n != 0) {
                return new JSONResult(integralExchange.getExchangeCode());
            } else {
                return new JSONResult("生成兑换码失败");
            }

        } catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误，请与管理人联络");
        } catch (InvalidKeySpecException e) {
            return new JSONResult("密钥不合法");
        }

    }

    @Override
    public PageInfo<IntegralInfoDTO> selectIntegralInfoList(IntegralInfoDTO integralInfoDTO, PageDTO pageDTO) {
        if (integralInfoDTO.getOrderBy() != null && !integralInfoDTO.getOrderBy().equals("")) {
            if (integralInfoDTO.getSort() != null && integralInfoDTO.getSort().equals("1")) {
                PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), "a." + integralInfoDTO.getOrderBy() + " DESC");
            }
            if (integralInfoDTO.getSort() != null && integralInfoDTO.getSort().equals("0")) {
                PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), "a." + integralInfoDTO.getOrderBy() + " ASC");
            }
        } else {
            PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        }

        List<IntegralInfoDTO> integralInfoList = integralInfoMapperExtra.selectIntegralInfoList(integralInfoDTO);
        return new PageInfo<>(integralInfoList);
    }

    @Override
    public PageInfo<IntegralDistributionDTO> getIntegralDistributionDetails(String userAccount, IntegralDistributionDTO integralDistributionDTO) {
        PageHelper.startPage(integralDistributionDTO.getCurrentPage(), integralDistributionDTO.getPageSize(), true);
        List<IntegralDistributionDTO> integralDistributionDetails = integralExchangeMapperExtra.getIntegralDistributionDetails(integralDistributionDTO);
        return new PageInfo(integralDistributionDetails);
    }

    @Override
    public JSONResult subsidyIntegralByPhone(List<String> receiverPhones, Integer integralAmount) {
        for (String receiverPhone: receiverPhones) {
            User receiver = userMapperExtra.findUserByAccount(receiverPhone);

            if(receiver == null) {
                return new JSONResult(receiverPhone + "电话号码的用户不存在");
            }

            IntegralInfo receiverInfo;
            int affectRow = 0;
            Date preDate = new Date();
            Date outDate;
            do {
                receiverInfo = integralInfoMapperExtra.selectByUserId(receiver.getUserId());
                receiverInfo.setCurrentTotal(receiverInfo.getCurrentTotal() + integralAmount);
                receiverInfo.setGotTotal(receiverInfo.getGotTotal() + integralAmount);
                affectRow = integralInfoMapperExtra.updateByPrimaryKeySync(receiverInfo);
                outDate = new Date();
                if (outDate.getTime() - preDate.getTime() > 3000) {
                    return new JSONResult("响应超时请重试!");
                }
            } while (affectRow == 0);

            IntegralLog integralLog = new IntegralLog();
            integralLog.setIntegralLogId(StringUtil.createId());
            integralLog.setIntegralInfoId(receiverInfo.getIntegralInfoId());
            integralLog.setUserId(receiver.getUserId());
            integralLog.setIntegralLogType(7);
            integralLog.setIntegralAmount(integralAmount);
            integralLog.setBeforeIntegralAmount(receiverInfo.getCurrentTotal());
            integralLog.setRemark("手机号补贴");
            integralLog.setCreateAt(new Date());
            integralLog.setUpdateAt(new Date());
            integralLog.setOrderId(StringUtil.createId());
            integralLogMapper.insert(integralLog);
        }
        return new JSONResult("补贴成功");
    }

    public JSONResult getIntegralRate() {
        return new JSONResult(dictMapperExtra.selectDictByName("integral_rate"));
    }

    public JSONResult updateIntegralRate(DictInputDTO dictInputDTO) {
        int n = dictMapperExtra.updateDict(dictInputDTO);
        if (n == 1) {
            return new JSONResult("修改积分比率成功!");
        }
        else {
            return new JSONResult("修改积分比率失败!");
        }
    }

    // 查询log信息
    @Override
    public IntegralLogDTO getIntegralInfo(String userId) {
        return integralInfoMapperExtra.getIntegralInfo(userId);
    }

    @Override
    public IntegralInfoDTO getGotTotal(String userId) {
        return integralInfoMapperExtra.getGotTotal(userId);
    }

    @Override
    public JSONResult insertIntegralValue(IntegralManageDTO integralManageDTO) {
        integralManageDTO.setId(StringUtil.createId());
        if (integralManageMapper.insertSelective(integralManageDTO) == 1) {
            return new JSONResult("新增成功!");
        }
        else {
            return new JSONResult("新增失败!");
        }
    }

    @Override
    public JSONResult updateIntegralValue(IntegralManageDTO integralManageDTO) {
        if (integralManageMapper.updateByPrimaryKeySelective(integralManageDTO) == 1) {
            return new JSONResult("修改成功!");
        }
        else {
            return new JSONResult("修改失败!");
        }
    }

    @Override
    public JSONResult deleteIntegralValue(IntegralManageDTO integralManageDTO) {
        if (integralManageMapper.deleteByPrimaryKey(integralManageDTO.getId()) == 1) {
            return new JSONResult("删除成功!");
        }
        else {
            return new JSONResult("删除失败!");
        }
    }

    @Override
    public PageInfo<IntegralManageDTO> getIntegralValueList(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IntegralManageDTO> integralInfoList = integralManageMapper.getIntegralValueList();
        return new PageInfo<>(integralInfoList);
    }

    @Override
    public List<User> fuzzyQueryUserPhone(String phone) {
        return userMapperExtra.selectUserByAccount(phone);
    }

    @Override
    public PageInfo<IntegralExchangeLogIdDTO> getExchangeLogDetails(PageDTO pageDTO, IntegralExchangeLogIdDTO integralExchangeLogIdDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IntegralExchangeLogIdDTO> integralExchangeLogIdDTOList =  integralExchangeLogIdMapperExtra.getExchangeLogDetails(integralExchangeLogIdDTO);
        return new PageInfo<IntegralExchangeLogIdDTO>(integralExchangeLogIdDTOList);
    }

    @Override
    public JSONResult insertMaxDeductionAmount(IntegralDeductionInfo integralDeductionInfo) {

        if (integralDeductionInfoMapperExtra.selectByCommodityIdAndCommodityAttrId(integralDeductionInfo) != null) {
            return new JSONResult("新增失败，此商品的抵扣额度已存在");
        }

        integralDeductionInfo.setIntegralDeductionInfoId(StringUtil.createId());
        integralDeductionInfo.setCreateAt(new Date());
        integralDeductionInfo.setUpdateAt(new Date());

        if (integralDeductionInfoMapper.insert(integralDeductionInfo) == 1) {
            return new JSONResult("新增成功");
        } else {
            return new JSONResult("新增失败");
        }
    }

    @Override
    public JSONResult updateMaxDeductionAmount(IntegralDeductionInfo integralDeductionInfo) {
        IntegralDeductionInfo oldIntegralDeductionInfo = integralDeductionInfoMapper.selectByPrimaryKey(integralDeductionInfo.getIntegralDeductionInfoId());
        if (oldIntegralDeductionInfo == null) {
            return new JSONResult("更新失败，原商品已经不存在");
        }

        integralDeductionInfo.setCreateAt(oldIntegralDeductionInfo.getCreateAt());
        integralDeductionInfo.setUpdateAt(new Date());

        oldIntegralDeductionInfo = integralDeductionInfoMapperExtra.selectByCommodityIdAndCommodityAttrId(integralDeductionInfo);
        if (oldIntegralDeductionInfo != null && oldIntegralDeductionInfo.getIntegralDeductionInfoId().equals(integralDeductionInfo.getIntegralDeductionInfoId())) {
            if (integralDeductionInfoMapper.updateByPrimaryKey(integralDeductionInfo) == 1) {
                return new JSONResult("更新成功");
            } else {
                return new JSONResult("更新失败");
            }
        } else {
            if (oldIntegralDeductionInfo != null) {
                return new JSONResult("更新失败，此商品的抵扣额度已存在");
            } else {
                if (integralDeductionInfoMapper.updateByPrimaryKey(integralDeductionInfo) == 1) {
                    return new JSONResult("更新成功");
                } else {
                    return new JSONResult("更新失败");
                }
            }
        }
    }

    @Override
    public JSONResult getCommodityByType(String type, String commodityId) {
        if(type.equals("1")) {
            return new JSONResult(weChatCommodityMapperExtra.selectAllCommodityTitle());
        }
        if(type.equals("2")) {
            return new JSONResult(directChargingCommodityMapperExtra.selectAllCommodityTitle("1", null));
        }
        if(type.equals("3")) {
            return new JSONResult(directChargingCommodityMapperExtra.selectAllCommodityTitle("2", "3"));
        }
        if(type.equals("4")) {
            return new JSONResult(equityPaymentCommodityMapperExtra.selectAllCommodityTitle());
        }
        if (commodityId != null || !commodityId.equals("")) {
            WxAttributeDTO wxAttributeDTO = new WxAttributeDTO();
            wxAttributeDTO.setCommodityId(commodityId);
            return new JSONResult(weChatCommodityMapperExtra.selectAllWxCommodityDetails(commodityId));
        }
        else {
            return new JSONResult("请求无效", 500);
        }
    }

    @Override
    public JSONResult getCommodityPrice(IntegralDeductionInfo integralDeductionInfo) {
        if (integralDeductionInfo.getDeductionType() == 1) {
            return new JSONResult(weChatCommodityMapperExtra.getCommodityPrice(integralDeductionInfo));
        }
        if (integralDeductionInfo.getDeductionType() == 2 || integralDeductionInfo.getDeductionType() == 3) {
            return new JSONResult(directChargingCommodityMapperExtra.getCommodityPriceByCommodityId(integralDeductionInfo.getCommodityId()));
        }
        if (integralDeductionInfo.getDeductionType() == 4) {
            return new JSONResult(equityPaymentCommodityMapperExtra.selectCommodityByGoodsId(integralDeductionInfo.getCommodityId()).getSellingPrice());
        }
        else {
            return new JSONResult("请求无效", 500);
        }

    }

    @Override
    public PageInfo<IntegralDeductionInfoDTO> getCommodityByPage(IntegralDeductionInfoDTO integralDeductionInfoDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IntegralDeductionInfoDTO> integralExchangeLogIdDTOList =  integralDeductionInfoMapperExtra.selectByCommodityType(integralDeductionInfoDTO);
        return new PageInfo<>(integralExchangeLogIdDTOList);
    }

    @Override
    public PageInfo<IntegralPurchaseRecordDTO> getIntegralPurchaseList(IntegralPurchaseRecordDTO integralPurchaseRecordDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IntegralPurchaseRecordDTO> integralPurchaseList = integralPurchaseRecordMapperExtra.getIntegralPurchaseList(integralPurchaseRecordDTO);
        return new PageInfo<>(integralPurchaseList);
    }

    /**
     * 同步修改积分info表
     * @param integralInfo
     */
    public synchronized void updateIntegralInfo(IntegralInfo integralInfo) {
        IntegralInfo integralInfoModel = integralInfoMapperExtra.selectByUserId(integralInfo.getUserId());
        integralInfoModel.setCurrentTotal(integralInfoModel.getCurrentTotal() + integralInfo.getCurrentTotal());
        integralInfoModel.setGotTotal(integralInfoModel.getGotTotal() + integralInfo.getGotTotal());
        integralInfoMapperExtra.updateByPrimaryKeySync(integralInfoModel);
    }

}

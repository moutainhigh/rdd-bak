package com.cqut.czb.bn.service.impl.integral;

import com.cqut.czb.bn.dao.mapper.integral.*;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralDeductionInfoMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralInfoMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralLogMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegrallogMapperExtra;
import com.cqut.czb.bn.entity.dto.integral.IntegralDetailsDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchangeLogId;
import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;
import com.cqut.czb.bn.entity.entity.integral.IntegralLog;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.RSA.RSAUtils;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    IntegrallogMapperExtra integrallogMapperExtra;

    @Autowired
    IntegralDeductionInfoMapperExtra integralDeductionInfoMapperExtra;

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
    UserMapperExtra userMapperExtra;

    public JSONResult getCurrentTotalIntegral(String userId) {
        return new JSONResult(integralInfoMapperExtra.selectByUserId(userId));
    }

    @Override
    public List<IntegralDetailsDTO> getIntegralDetail(String userId) {
        List<IntegralLogDTO> integralLogDTOList = integrallogMapperExtra.getIntegralDetailsList(userId);
        List<IntegralExchange> integralExchangeList = integralExchangeMapperExtra.getExchangeList(userId);
        List<IntegralDetailsDTO> integralDetailsDTOS = new ArrayList<>();

        for (int i = 0; i < integralLogDTOList.size(); i++) {
            IntegralDetailsDTO integralDetailsDTO = new IntegralDetailsDTO();
            IntegralLogDTO integralLogDTO = integralLogDTOList.get(i);
            if (integralLogDTO.getIntegralLogType() != 1) {
                integralDetailsDTO.setIntegralAmount(integralLogDTO.getIntegralAmount());
                integralDetailsDTO.setIntegralLogType(integralLogDTO.getIntegralLogType());
                integralDetailsDTO.setUserId(userId);
                integralDetailsDTO.setRemark(integralLogDTO.getRemark());
                integralDetailsDTO.setCreateAt(integralLogDTO.getCreateAt());
                integralDetailsDTO.setUpdateAt(integralLogDTO.getUpdateAt());
                integralDetailsDTOS.add(integralDetailsDTO);
            }
        }

        for (int i = 0; i < integralExchangeList.size(); i++) {
            IntegralDetailsDTO integralDetailsDTO = new IntegralDetailsDTO();
            IntegralExchange integralExchange = integralExchangeList.get(i);
            integralDetailsDTO.setIntegralAmount(integralExchange.getExchangeAmount() * integralExchange.getExchangeTimesCurrent());
            integralDetailsDTO.setIntegralLogType(1);
            integralDetailsDTO.setUserId(userId);
            integralDetailsDTO.setRemark("兑换码赠送");
            integralDetailsDTO.setIntegralExchange(integralExchange.getIntegralExchange());
            integralDetailsDTO.setExchangeType(integralExchange.getExchangeType());
            integralDetailsDTO.setCreateAt(integralExchange.getCreateAt());
            integralDetailsDTO.setUpdateAt(integralExchange.getUpdateAt());
            integralDetailsDTOS.add(integralDetailsDTO);
        }

        integralDetailsDTOS.sort(new Comparator<IntegralDetailsDTO>() {
            @Override
            public int compare(IntegralDetailsDTO o1, IntegralDetailsDTO o2) {
                return o2.getCreateAt().compareTo(o1.getCreateAt());
            }
        });

        return integralDetailsDTOS;
    }

    @Override
    public List<IntegralDetailsDTO> getOfferIntegralDetail(String userId) {
        List<IntegralLogDTO> integralLogDTOList = integrallogMapperExtra.getIntegralDetailsList(userId);
        List<IntegralExchange> integralExchangeList = integralExchangeMapperExtra.getExchangeList(userId);
        List<IntegralDetailsDTO> integralDetailsDTOS = new ArrayList<>();

        for (int i = 0; i < integralLogDTOList.size(); i++) {
            IntegralDetailsDTO integralDetailsDTO = new IntegralDetailsDTO();
            IntegralLogDTO integralLogDTO = integralLogDTOList.get(i);
            if (integralLogDTO.getIntegralLogType() == 3) {
                integralDetailsDTO.setIntegralAmount(integralLogDTO.getIntegralAmount());
                integralDetailsDTO.setIntegralLogType(integralLogDTO.getIntegralLogType());
                integralDetailsDTO.setUserId(userId);
                integralDetailsDTO.setRemark(integralLogDTO.getRemark());
                integralDetailsDTO.setCreateAt(integralLogDTO.getCreateAt());
                integralDetailsDTO.setUpdateAt(integralLogDTO.getUpdateAt());
                integralDetailsDTOS.add(integralDetailsDTO);
            }
        }

        for (int i = 0; i < integralExchangeList.size(); i++) {
            IntegralDetailsDTO integralDetailsDTO = new IntegralDetailsDTO();
            IntegralExchange integralExchange = integralExchangeList.get(i);
            integralDetailsDTO.setIntegralAmount(integralExchange.getExchangeAmount() * integralExchange.getExchangeTimesCurrent());
            integralDetailsDTO.setIntegralLogType(1);
            integralDetailsDTO.setUserId(userId);
            integralDetailsDTO.setRemark("兑换码赠送");
            integralDetailsDTO.setIntegralExchange(integralExchange.getIntegralExchange());
            integralDetailsDTO.setExchangeType(integralExchange.getExchangeType());
            integralDetailsDTO.setCreateAt(integralExchange.getCreateAt());
            integralDetailsDTO.setUpdateAt(integralExchange.getUpdateAt());
            integralDetailsDTOS.add(integralDetailsDTO);
        }

        integralDetailsDTOS.sort(new Comparator<IntegralDetailsDTO>() {
            @Override
            public int compare(IntegralDetailsDTO o1, IntegralDetailsDTO o2) {
                return o2.getCreateAt().compareTo(o1.getCreateAt());
            }
        });

        return integralDetailsDTOS;
    }

    @Override
    public JSONResult getExchangeDetails(String integralExchangeId) {
        IntegralExchange exchangeDetails = integralExchangeMapper.selectByPrimaryKey(integralExchangeId);
        try {
            exchangeDetails.setExchangeCode(RSAUtils.cipherToEXcode(RSAUtils.publicEncrypt(exchangeDetails.getExchangeCode(), RSAUtils.getPublicKey(RSAUtils.publicKey))));
        }
        catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误，请与管理人联络", 500);
        } catch (InvalidKeySpecException e) {
            return new JSONResult("密钥不合法", 500);
        }

        return new JSONResult(exchangeDetails);
    }

    @Override
    public JSONResult purchaseIntegral(IntegralInfoDTO integralInfoDTO) {
        return null;
    }

    @Override
    public JSONResult exchangeIntegral(IntegralExchangeDTO integralExchangeDTO, String userId) {
        // 兑换积分 用户兑换别人的二维码需要(获取变更前积分数值)对积分信息表修改, 积分记录表新增, 积分兑换表修改, 新增积分兑换明细表

        // 兑换者信息
        IntegralInfo integralInfoGiven = integralInfoMapperExtra.selectByUserId(userId);

        // 主人兑换码信息
        IntegralExchange integralExchangeMng;

        // 兑换码主人的信息
        IntegralInfo integralInfoMng;

        int affectRow = 0;
        int whileTimes = 0;
        do {
            integralExchangeMng = integralExchangeMapperExtra.selectByIntegralExchange(integralExchangeDTO);
            if (integralExchangeMng == null) {
                return new JSONResult("兑换码不存在!", 500);
            }
            if (integralExchangeMng.getFailureTime().before(new Date())) {
                return new JSONResult("兑换码已过期!", 500);
            }
            // 积分兑换明细信息
            IntegralExchangeLogId integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId = integralExchangeLogIdMapperExtra.selectByIntegralExchange(integralExchangeLogId);

            if (integralExchangeLogId != null) {
                return new JSONResult("你已经兑换过该积分!", 500);
            }
            integralInfoMng = integralInfoMapperExtra.selectByUserId(integralExchangeMng.getExchangeSourceId());
            if (integralExchangeMng.getExchangeAmount() > integralInfoMng.getCurrentTotal()) {
                return new JSONResult("对方积分不足!", 500);
            }
            if (integralExchangeMng.getExchangeTimesCurrent() == integralExchangeMng.getExchangeTimesTotal()) {
                return new JSONResult("该二维码(兑换码)已被兑换完!", 500);
            }

            // 兑换积分 更新积分兑换表
            integralExchangeMng.setOldUpdateAt(integralExchangeMng.getUpdateAt());
            integralExchangeMng.setUpdateAt(new Date());

            if (integralExchangeMng.getExchangeTimesCurrent() + 1 == integralExchangeMng.getExchangeTimesTotal()) {
                integralExchangeMng.setIsComplete(1);
            }
            affectRow = integralExchangeMapperExtra.updateByPrimaryKeySync(integralExchangeMng);

            integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeLogId(StringUtil.createId());
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId.setCreateAt(new Date());
            integralExchangeLogId.setUpdateAt(new Date());
            integralExchangeLogIdMapper.insert(integralExchangeLogId);
            // 控制循环次数 以免占用CPU资源
            whileTimes++;
            if (whileTimes == 10) {
                return new JSONResult("网络超时请重试!", 500);
            }
        } while(affectRow == 0);

        // 新增兑换码主人积分记录
        IntegralLog integralLog = new IntegralLog();
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(integralInfoMng.getIntegralInfoId());
        integralLog.setUserId(integralInfoMng.getUserId());
        integralLog.setIntegralLogType(1);
        integralLog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
        integralLog.setBeforeIntegralAmount(integralInfoMng.getCurrentTotal());
        integralLog.setRemark("赠予他人");
        integralLog.setCreateAt(new Date());
        integralLog.setUpdateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);
        integralInfoMng.setCurrentTotal(integralInfoMng.getCurrentTotal() - integralExchangeMng.getExchangeAmount());
        integralInfoMng.setUpdateAt(new Date());
        integralInfoMapper.updateByPrimaryKey(integralInfoMng);

        // 新增兑换人积分记录
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(integralInfoGiven.getIntegralInfoId());
        integralLog.setUserId(userId);
        integralLog.setIntegralLogType(3);
        integralLog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
        integralLog.setBeforeIntegralAmount(integralInfoGiven.getCurrentTotal());
        integralLog.setRemark("被赠予");
        integralLog.setCreateAt(new Date());
        integralLog.setUpdateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);
        integralInfoGiven.setCurrentTotal(integralInfoGiven.getCurrentTotal() + integralExchangeMng.getExchangeAmount());
        integralInfoGiven.setGotTotal(integralInfoGiven.getGotTotal() + integralExchangeMng.getExchangeAmount());
        integralInfoGiven.setUpdateAt(new Date());
        integralInfoMapper.updateByPrimaryKey(integralInfoGiven);

        return new JSONResult("恭喜你成功兑换积分!", 200);
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
            return new JSONResult("此电话号码不存在", 500);
        }

        IntegralInfo providerInfo = integralInfoMapperExtra.selectByUserId(providerId);
        IntegralInfo receiverInfo = integralInfoMapperExtra.selectByUserId(receiver.getUserId());
        // 赠送人的积分记录
        IntegralLog integralLog = new IntegralLog();
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(providerInfo.getIntegralInfoId());
        integralLog.setUserId(providerInfo.getUserId());
        integralLog.setIntegralLogType(2);
        integralLog.setIntegralAmount(integralAmount);
        integralLog.setBeforeIntegralAmount(providerInfo.getCurrentTotal());
        integralLog.setRemark("赠予他人");
        integralLog.setCreateAt(new Date());
        integralLog.setUpdateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);
        providerInfo.setCurrentTotal(providerInfo.getCurrentTotal() - integralAmount);
        providerInfo.setUpdateAt(new Date());
        integralInfoMapper.updateByPrimaryKey(providerInfo);

        // 新增兑换人积分记录
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
        receiverInfo.setCurrentTotal(receiverInfo.getCurrentTotal() + integralAmount);
        receiverInfo.setGotTotal(receiverInfo.getGotTotal() + integralAmount);
        receiverInfo.setUpdateAt(new Date());
        integralInfoMapper.updateByPrimaryKey(receiverInfo);

        return new JSONResult("恭喜你赠送积分成功!", 200);
    }

    @Override
    public JSONResult createExchangeCode(IntegralExchange integralExchange) {
        IntegralInfo userIntegralInfo = integralInfoMapperExtra.selectByUserId(integralExchange.getExchangeSourceId());
        if (userIntegralInfo.getCurrentTotal() < integralExchange.getExchangeAmount() * integralExchange.getExchangeTimesTotal()) {
            return new JSONResult("你的积分不足", 500);
        }

        if (integralExchange.getFailureTime().compareTo(new Date()) < 0) {
            return new JSONResult("失效时间比当前时间早", 500);
        }

        integralExchange.setIntegralExchange(StringUtil.createId());
        integralExchange.setExchangeType(2);
        integralExchange.setExchangeTimesCurrent(0);
        integralExchange.setIsComplete(0);
        integralExchange.setCreateAt(new Date());
        integralExchange.setUpdateAt(new Date());
        integralExchange.setExchangeCode(integralExchange.getIntegralExchange());
        integralExchangeMapper.insert(integralExchange);
        try {
            return new JSONResult(RSAUtils.cipherToEXcode(RSAUtils.publicEncrypt(integralExchange.getExchangeCode(),RSAUtils.getPublicKey(RSAUtils.publicKey))), 200);
        } catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误，请与管理人联络", 500);
        } catch (InvalidKeySpecException e) {
            return new JSONResult("密钥不合法", 500);
        }
    }

    @Override
    public List<IntegralInfoDTO> selectIntegralInfoList(String userAccount) {

        return integralInfoMapperExtra.selectIntegralInfoList(userAccount);
    }
}

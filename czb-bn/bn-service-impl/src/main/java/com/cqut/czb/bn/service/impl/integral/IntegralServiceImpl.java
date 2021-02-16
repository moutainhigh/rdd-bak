package com.cqut.czb.bn.service.impl.integral;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.*;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralDeductionInfoMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralInfoMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralLogMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegrallogMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatFileUtil;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralDetailsDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchangeLogId;
import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;
import com.cqut.czb.bn.entity.entity.integral.IntegralLog;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.RSA.RSAUtils;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

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

    @Autowired
    private BusinessProcessService refuelingCard;

    @Autowired
    DictMapperExtra dictMapperExtra;

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
            if (integralLogDTO.getIntegralLogType() == 3 || integralLogDTO.getIntegralLogType() == 2) {
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
        Date preDate = new Date();
        Date outDate;
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

            if (integralExchangeMng.getExchangeTimesCurrent() + 1 == integralExchangeMng.getExchangeTimesTotal()) {
                integralExchangeMng.setIsComplete(1);
            }
            affectRow = integralExchangeMapperExtra.updateByPrimaryKeySync(integralExchangeMng);

            integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeLogId(StringUtil.createId());
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId.setCreateAt(new Date());
            integralExchangeLogIdMapper.insert(integralExchangeLogId);

            outDate = new Date();
            // 控制循环时间 以免占用CPU资源
            if (outDate.getTime() - preDate.getTime() > 3000) {
                return new JSONResult("响应超时请重试!", 500);
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
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);
        if (integralExchangeMng.getExchangeType() == 2) {
            preDate = new Date();
            integralInfoMng.setCurrentTotal(integralInfoMng.getCurrentTotal() - integralExchangeMng.getExchangeAmount());
            affectRow = integralInfoMapperExtra.updateByPrimaryKeySync(integralInfoMng);
            while (affectRow == 0) {
                integralInfoMng = integralInfoMapperExtra.selectByUserId(integralExchangeMng.getExchangeSourceId());
                integralInfoMng.setCurrentTotal(integralInfoMng.getCurrentTotal() - integralExchangeMng.getExchangeAmount());
                integralInfoMng.setUpdateAt(new Date());
                affectRow = integralInfoMapperExtra.updateByPrimaryKeySync(integralInfoMng);
                outDate = new Date();
                if (outDate.getTime() - preDate.getTime() > 3000) {
                    return new JSONResult("响应超时请重试!", 500);
                }
            }
        }

        // 新增兑换人积分记录
        integralLog.setIntegralLogId(StringUtil.createId());
        integralLog.setIntegralInfoId(integralInfoGiven.getIntegralInfoId());
        integralLog.setUserId(userId);
        integralLog.setIntegralLogType(3);
        integralLog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
        integralLog.setBeforeIntegralAmount(integralInfoGiven.getCurrentTotal());
        integralLog.setRemark("被赠予");
        integralLog.setCreateAt(new Date());
        integralLog.setOrderId(StringUtil.createId());
        integralLogMapper.insert(integralLog);
        integralInfoGiven.setCurrentTotal(integralInfoGiven.getCurrentTotal() + integralExchangeMng.getExchangeAmount());
        integralInfoGiven.setGotTotal(integralInfoGiven.getGotTotal() + integralExchangeMng.getExchangeAmount());
        affectRow = integralInfoMapperExtra.updateByPrimaryKeySync(integralInfoGiven);
        preDate = new Date();
        while (affectRow == 0) {
            integralInfoGiven = integralInfoMapperExtra.selectByUserId(userId);
            integralInfoGiven.setCurrentTotal(integralInfoGiven.getCurrentTotal() + integralExchangeMng.getExchangeAmount());
            integralInfoGiven.setGotTotal(integralInfoGiven.getGotTotal() + integralExchangeMng.getExchangeAmount());
            affectRow = integralInfoMapperExtra.updateByPrimaryKeySync(integralInfoGiven);
            outDate = new Date();
            if (outDate.getTime() - preDate.getTime() > 3000) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new JSONResult("响应超时请重试!", 500);
            }
        }

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

        if (providerInfo.getCurrentTotal() < integralAmount) {
            return new JSONResult("用户您的账号的积分余额不足", 500);
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
                return new JSONResult("响应超时请重试!", 500);
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

        return new JSONResult("恭喜你赠送积分成功!", 200);
    }

    @Override
    public JSONResult createExchangeCode(IntegralExchangeDTO integralExchange) {
        IntegralInfo userIntegralInfo = integralInfoMapperExtra.selectByUserId(integralExchange.getExchangeSourceId());

        if (integralExchange.getFailureTime().compareTo(new Date()) < 0) {
            return new JSONResult("失效时间比当前时间早", 500);
        }

        if (integralExchange.getExchangeType() == 1) {
            integralExchange.setExchangeTimesTotal(integralInfoMapperExtra.getUserAmount());
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
                return new JSONResult(integralExchange.getExchangeCode(), 200);
            } else {
                return new JSONResult("生成兑换码失败", 500);
            }

        } catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误，请与管理人联络", 500);
        } catch (InvalidKeySpecException e) {
            return new JSONResult("密钥不合法", 500);
        }

    }

    @Override
    public PageInfo<IntegralInfoDTO> selectIntegralInfoList(String userAccount, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IntegralInfoDTO> integralInfoList = integralInfoMapperExtra.selectIntegralInfoList(userAccount);
        return new PageInfo<>(integralInfoList);
    }

    @Override
    public JSONResult subsidyIntegralByPhone(String receiverPhone, Integer integralAmount) {
        User receiver = userMapperExtra.findUserByAccount(receiverPhone);

        if(receiver == null) {
            return new JSONResult("此电话号码不存在", 500);
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
                return new JSONResult("响应超时请重试!", 500);
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
        return new JSONResult("补贴成功", 200);
    }

    public JSONResult getIntegralRate() {
        return new JSONResult(dictMapperExtra.selectDictByName("integral_rate"));
    }

    public JSONResult updateIntegralRate(String rate) {
        Dict dict = dictMapperExtra.selectDictByName("integral_rate");
        DictInputDTO dictInputDTO = new DictInputDTO();
        dictInputDTO.setDictId(dict.getDictId());
        dictInputDTO.setName(dict.getName());
        dictInputDTO.setContent(rate);
        return new JSONResult(dictMapperExtra.updateDict(dictInputDTO));
    }

    /**
     * 微信购买积分
     * @param request
     * @param consumptionType
     * @return
     */
    @Override
    public String wechatBuyIntegral(HttpServletRequest request, String consumptionType) {
        try {
            ServletInputStream in = request.getInputStream();
            String resxml = WeChatFileUtil.inputStream2String(in);
            Map<String, Object> restmap = WeChatUtils.xml2Map(resxml);
            if ("SUCCESS".equals(restmap.get("result_code"))) {
                // 订单支付成功 业务处理
                if (checkSign(restmap)) {
                    // 进行业务处理
                    Object[] param = { restmap };
                    Map<String, Integer> result = refuelingCard.WeChatPayBack(param,consumptionType);
                    if (result.get("success") == 1) {
                        return getSuccess();
                    } else {
                        return AlipayConfig.response_fail;
                    }
                } else {
                    return AlipayConfig.response_fail;
                }
            } else {
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getSuccess();
        }
    }

    // 微信异步通知成功
    public String getSuccess() {
        SortedMap<String, Object> respMap = new TreeMap<>();
        respMap = new TreeMap<String, Object>();
        respMap.put("return_code", "SUCCESS"); // 响应给微信服务器
        respMap.put("return_msg", "OK");
        String resXml = WeChatUtils.map2xml(respMap);
        return resXml;
    }

    // 验证签名（微信）
    public boolean checkSign(Map<String, Object> restmap) {
        String sign = (String) restmap.get("sign"); // 返回的签名
        restmap.remove("sign");
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : restmap.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        //爱动key
        String signNow = WeChatUtils.createSign("UTF-8", sortedMap);
        //人多多key
        String signNowRdd = WeChatUtils.createRddSign("UTF-8", sortedMap);
        if (sign.equals(signNow)||sign.equals(signNowRdd)) {
            return true;
        } else {
            return false;
        }
    }

    // 查询log信息
    @Override
    public IntegralLogDTO getIntegralInfo(String userId) {
        return integralInfoMapperExtra.getIntegralInfo(userId);
    }
}

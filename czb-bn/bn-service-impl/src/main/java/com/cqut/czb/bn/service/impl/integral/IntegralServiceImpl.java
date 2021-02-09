package com.cqut.czb.bn.service.impl.integral;

import com.cqut.czb.bn.dao.mapper.integral.*;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralIogDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchangeLogId;
import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;
import com.cqut.czb.bn.entity.entity.integral.IntegralIog;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    IntegralIogMapper integralIogMapper;

    @Autowired
    IntegralExchangeMapper integralExchangeMapper;

    @Autowired
    IntegralExchangeMapperExtra integralExchangeMapperExtra;

    @Autowired
    IntegralExchangeLogIdMapper integralExchangeLogIdMapper;

    @Autowired
    IntegralExchangeLogIdMapperExtra integralExchangeLogIdMapperExtra;

    @Override
    public List<IntegralIogDTO> getIntegralDetail(String userId) {
        return integrallogMapperExtra.list(userId);
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
            integralExchangeMng = integralExchangeMapperExtra.selectByExchangeCode(integralExchangeDTO.getExchangeCode());

            // 积分兑换明细信息
            IntegralExchangeLogId integralExchangeLogId = new IntegralExchangeLogId();
            integralExchangeLogId.setIntegralExchangeId(integralExchangeMng.getIntegralExchange());
            integralExchangeLogId.setExchangeUserId(userId);
            integralExchangeLogId = integralExchangeLogIdMapperExtra.selectByIntegralExchange(integralExchangeLogId);

            if (integralExchangeLogId != null) {
                return new JSONResult("你已经兑换过该积分!", 500);
            }

            integralInfoMng = integralInfoMapperExtra.selectByUserId(integralExchangeMng.getExchangeSourceId());
            if (integralInfoMng == null) {
                return new JSONResult("没有该兑换码信息!", 500);
            }
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
        IntegralIog integralIog = new IntegralIog();
        integralIog.setIntegralLogId(StringUtil.createId());
        integralIog.setIntegralInfoId(integralInfoMng.getIntegralInfoId());
        integralIog.setUserId(integralInfoMng.getUserId());
        integralIog.setIntegralLogType(1);
        integralIog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
        integralIog.setBeforeIntegralAmount(integralInfoMng.getCurrentTotal());
        integralIog.setRemark("赠予他人");
        integralIog.setCreateAt(new Date());
        integralIog.setUpdateAt(new Date());
        integralIog.setOrderId(StringUtil.createId());
        integralIogMapper.insert(integralIog);
        integralInfoMng.setCurrentTotal(integralInfoMng.getCurrentTotal() - integralExchangeMng.getExchangeAmount());
        integralInfoMng.setUpdateAt(new Date());
        integralInfoMapper.updateByPrimaryKey(integralInfoMng);

        // 新增兑换人积分记录
        integralIog.setIntegralLogId(StringUtil.createId());
        integralIog.setIntegralInfoId(integralInfoGiven.getIntegralInfoId());
        integralIog.setUserId(userId);
        integralIog.setIntegralLogType(2);
        integralIog.setIntegralAmount(integralExchangeMng.getExchangeAmount());
        integralIog.setBeforeIntegralAmount(integralInfoGiven.getCurrentTotal());
        integralIog.setRemark("被赠予");
        integralIog.setCreateAt(new Date());
        integralIog.setUpdateAt(new Date());
        integralIog.setOrderId(StringUtil.createId());
        integralIogMapper.insert(integralIog);
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
}

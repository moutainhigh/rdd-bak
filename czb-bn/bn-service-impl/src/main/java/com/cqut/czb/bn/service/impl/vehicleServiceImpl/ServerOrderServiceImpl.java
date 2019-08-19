package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.MyWalletMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.TuiKuanDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.entity.entity.vehicleService.VehicleCleanOrder;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.ServerOrderService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service
public class ServerOrderServiceImpl implements ServerOrderService {
    @Autowired
    VehicleCleanOrderMapperExtra mapperExtra;

    @Autowired
    VehicleCleanOrderMapper mapper;

    @Autowired
    private MyWalletMapperExtra myWalletMapper;

    @Override
    public JSONResult distribute(VehicleOrderManageDTO manageDTO) {
        // 设置查询骑手的条件实体
        CleanRider rider = new CleanRider();
        rider.setRiderName(manageDTO.getRiderName());
        rider.setContactNumber(manageDTO.getRiderPhone());

        // 查询骑手
        CleanRider cleanRider =mapperExtra.getCleanRider(rider);
        // 判断骑手是否存在
        if (null != cleanRider) {
            // 判断骑手的状态
            if(cleanRider.getStatus() == 1) {
                return new JSONResult("骑手正在路上", 500);
            } else if (cleanRider.getStatus() == 2) {
                return new JSONResult("骑手正在服务", 500);
            } else {
                // 修改订单的实体
                VehicleCleanOrder cleanOrder = new VehicleCleanOrder();
                cleanOrder.setServerOrderId(manageDTO.getServerOrderId());
                cleanOrder.setRiderId(cleanRider.getRiderId());
                Byte byteStatus = 1;
                cleanOrder.setProcessStatus(byteStatus);
                // 分配骑手
                if (mapper.updateByPrimaryKeySelective(cleanOrder) > 0) {
                    // 分配骑手成功后，改变骑手状态
                    if (mapperExtra.updateRiderStatus(cleanRider.getRiderId(), "1") > 0) {
                        return new JSONResult("分配骑手成功", 200);
                    } else {
                        return new JSONResult("分配骑手成功,但改变骑手状态失败", 200);
                    }
                } else {
                    return new JSONResult("分配骑手失败", 200);
                }
            }
        } else {
            return new JSONResult("没有这个骑手", 500);
        }
    }

    @Override
    public JSONResult complete(VehicleCleanOrderDTO cleanOrderDTO) {
        VehicleCleanOrder cleanOrder = new VehicleCleanOrder();
        cleanOrder.setServerOrderId(cleanOrderDTO.getServerOrderId());
        Byte status = 2;
        cleanOrder.setProcessStatus(status);
        cleanOrder.setRiderId("");
        if (mapper.updateByPrimaryKeySelective(cleanOrder) > 0) {
            mapperExtra.updateRiderStatus(cleanOrderDTO.getRiderId(), "0");
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 200);
        }
    }

    @Override
    public JSONResult change() {
        return null;
    }

    /**
     * 退款
     * @param tuiKuanDTO
     * @return
     */
    @Override
    public JSONResult tuiKuan(TuiKuanDTO tuiKuanDTO) {

        VehicleCleanOrder cleanOrderDTO = new VehicleCleanOrder();
        cleanOrderDTO.setServerOrderId(tuiKuanDTO.getServerOrderId());
        Byte status = 4;
        cleanOrderDTO.setProcessStatus(status);
        cleanOrderDTO.setRiderId("");
        mapper.updateByPrimaryKeySelective(cleanOrderDTO);
        if (tuiKuanDTO.getRiderId() != null && !tuiKuanDTO.getRiderId().equals("")) {
            mapperExtra.updateRiderStatus(tuiKuanDTO.getRiderId(), "0");
        }

        BalanceAndInfoIdDTO balanceAndInfoId = myWalletMapper.getUserAllIncome(tuiKuanDTO.getUserId());

        BigDecimal bigDecimal = (new BigDecimal(tuiKuanDTO.getAmountMoney().toString())).subtract(new BigDecimal(tuiKuanDTO.getMoney().toString()));

        // 设置退款记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(balanceAndInfoId.getInfoId());
        incomeLog.setAmount(bigDecimal.doubleValue());
        incomeLog.setBeforeChangeIncome(balanceAndInfoId.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setRemark("洗车订单退款");
        incomeLog.setType(4);
        incomeLog.setSourceId(tuiKuanDTO.getServerOrderId());// 来源为平台本身洗车订单单号

        // 插入提现记录成功，则改变余额
        if(myWalletMapper.insertIncomeLog(incomeLog) > 0) {
            if (myWalletMapper.increaseOtherMoney(balanceAndInfoId.getInfoId(), bigDecimal.toString()) > 0) {
                return new JSONResult("退款成功", 200);
            } else {
                return new JSONResult("退款失败", 500);
            }
        } else {
            return new JSONResult("退款失败", 500);
        }
    }

    @Override
    public JSONResult search(VehicleOrderManageDTO orderManageDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<VehicleOrderManageDTO> orderList = mapperExtra.search(orderManageDTO);
        PageInfo<VehicleOrderManageDTO> orderInfoList = new PageInfo<>(orderList);

        return new JSONResult("查询成功", 200, orderInfoList);
    }
}

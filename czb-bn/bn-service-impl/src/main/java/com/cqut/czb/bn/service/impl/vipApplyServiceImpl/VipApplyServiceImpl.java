package com.cqut.czb.bn.service.impl.vipApplyServiceImpl;

import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapperExtra;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.service.vipApplicationService.VipApplyService;
import com.cqut.czb.bn.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipApplyServiceImpl implements VipApplyService {
    @Autowired
    WeChatVipApplyMapperExtra vipApplyMapper;

    @Autowired
    UserMapperExtra userMapperExtra;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public PageInfo<VIPApply> getvip(VIPApply record) {
        PageHelper.startPage(record.getCurrentPage(), record.getPageSize());

        if ("" == record.getVipAccount() || record.getVipAccount() == null) {
            record.setVipAccount(null);
        }

        List<VIPApply> vipApplies = vipApplyMapper.selectVipApply(record);
        return new PageInfo<VIPApply>(vipApplies);
    }

    @Override
    public boolean updateVip(VIPApply vipApplication) {
        if(vipApplyMapper.updateVip(vipApplication)){
            UserDTO user = userMapperExtra.findUserDTOById(vipApplication.getVipId());
            if(redisUtil.hasKey(user.getUserAccount())) {
                redisUtil.remove(user.getUserAccount());
                redisUtil.put(user.getUserAccount(), user);
            }
            return true;
        }
        else {
            return false;
        }
    }
}

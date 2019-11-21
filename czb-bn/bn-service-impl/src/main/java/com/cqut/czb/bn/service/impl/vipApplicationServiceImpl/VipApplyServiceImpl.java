package com.cqut.czb.bn.service.impl.vipApplicationServiceImpl;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapperExtra;
import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.service.vipApplicationService.VipApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipApplyServiceImpl implements VipApplyService {
    @Autowired
    WeChatVipApplyMapperExtra vipApplyMapper;

    @Override
    public PageInfo<VIPApply> getvip(VIPApply record) {
        PageHelper.startPage(record.getCurrentPage(), record.getPageSize());
        List<VIPApply> vipApplies = vipApplyMapper.selectVipApply(record);

        return new PageInfo<VIPApply>(vipApplies);



//        UserDTO user = userMapperExtra.findUserDTOById(userInputDTO.getUserId());
//        if(redisUtil.hasKey(user.getUserAccount())) {
//            redisUtil.remove(user.getUserAccount());
//            redisUtil.put(user.getUserAccount(), user);
//        }
    }
}

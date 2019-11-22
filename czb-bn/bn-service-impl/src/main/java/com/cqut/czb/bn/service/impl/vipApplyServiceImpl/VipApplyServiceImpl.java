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

@Service
public class VipApplyServiceImpl implements VipApplyService {
    @Autowired
    WeChatVipApplyMapperExtra vipApplyMapper;

    @Autowired
    UserMapperExtra userMapperExtra;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public PageInfo<VIPApply> selectVipApply(VIPApply vipApply) {
        PageHelper.startPage(vipApply.getCurrentPage(), vipApply.getPageSize());
        return new PageInfo<>(vipApplyMapper.selectVipApply(vipApply));
    }

    @Override
    public boolean updateVipApply(VIPApply vipApply) {
        if(vipApplyMapper.updateVipApply(vipApply)){
            UserDTO user = userMapperExtra.findUserDTOById(vipApply.getVipId());
            if(redisUtil.hasKey(user.getUserAccount())) {
                redisUtil.remove(user.getUserAccount());
                redisUtil.put(user.getUserAccount(), user);
            }
            return true;
        }
            return false;
    }
}

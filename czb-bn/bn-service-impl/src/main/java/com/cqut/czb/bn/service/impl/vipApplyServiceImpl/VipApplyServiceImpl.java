package com.cqut.czb.bn.service.impl.vipApplyServiceImpl;

import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapperExtra;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;
import com.cqut.czb.bn.service.vipApplicationService.VipApplyService;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class VipApplyServiceImpl implements VipApplyService {
    @Autowired
    WeChatVipApplyMapperExtra vipApplyMapper;

    @Autowired
    WeChatVipApplyMapper weChatVipApplyMapper;

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

    @Override
    public Boolean applyWCPVip(WeChatVipApply weChatVipApply, Principal principal) {
        if(principal == null){
            return false;
        }
        try{
            User user = (User) redisUtil.get(principal.getName());
            UserDTO userDTO = userMapperExtra.findUserDTOById(user.getUserId());
            if (userDTO==null || userDTO.getIsVip()==1){
                return false;
            }
            weChatVipApply.setRecordId(StringUtil.createId());
            weChatVipApply.setStatus(1);
            weChatVipApply.setUserId(user.getUserId());
            weChatVipApplyMapper.insertSelective(weChatVipApply);
            vipApplyMapper.updateUserVip(user.getUserId());
            if(redisUtil.hasKey(user.getUserAccount())) {
                redisUtil.remove(user.getUserAccount());
                redisUtil.put(user.getUserAccount(), user);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

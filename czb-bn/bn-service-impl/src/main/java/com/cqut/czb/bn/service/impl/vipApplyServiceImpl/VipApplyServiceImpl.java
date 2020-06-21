package com.cqut.czb.bn.service.impl.vipApplyServiceImpl;

import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatVipApplyMapperExtra;
import com.cqut.czb.bn.entity.dto.WxVipMoenyDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;
import com.cqut.czb.bn.entity.global.JSONResult;
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
            UserDTO user = (UserDTO) redisUtil.get(principal.getName());
            UserDTO userDTO = userMapperExtra.findUserDTOById(user.getUserId());
            if (userDTO==null || userDTO.getIsVip()==1){
                return false;
            }
            weChatVipApply.setRecordId(StringUtil.createId());
            weChatVipApply.setStatus(1);
            weChatVipApply.setUserId(user.getUserId());
            weChatVipApply.setCreateAt(new Date());
            weChatVipApply.setUpdateAt(new Date());
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

    @Override
    public Double getVipMoney() {
        Double money = Double.parseDouble(vipApplyMapper.getVipMoney());
        return money;
    }

    @Override
    public WxVipMoenyDTO getWxVIP() {
        WxVipMoenyDTO wxVipMoenyDTO = new WxVipMoenyDTO();
        wxVipMoenyDTO.setVIPMoney(weChatVipApplyMapper.getWxVIPMoeny());
        wxVipMoenyDTO.setWXFY1(weChatVipApplyMapper.getWxVIPFY1());
        wxVipMoenyDTO.setWXFY2(weChatVipApplyMapper.getWxVIPFY2());
        return wxVipMoenyDTO;
    }

    @Override
    public JSONResult changeWxVIP(WxVipMoenyDTO wxVipMoenyDTO) {
        boolean result1 = weChatVipApplyMapper.changeWxVIPMoeny(wxVipMoenyDTO.getVIPMoney());
        boolean result2 = weChatVipApplyMapper.changeWxVIPFY1(wxVipMoenyDTO.getWXFY1());
        boolean result3 = weChatVipApplyMapper.changeWxVIPFY2(wxVipMoenyDTO.getWXFY2());
        if (result1&&result2&&result3){
            return new JSONResult("变更成功",200,true);
        }else {
            return new JSONResult("变更失败",200,false);

        }
    }
}

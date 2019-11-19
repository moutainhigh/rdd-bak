package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.GeneralPartnerUserMapperExtra;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.UserIncomeStatisticDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GeneralPartnerUser;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.HumanAmountStatistic;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.partnerAndOperateCenter.GeneralPartnerUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: GeneralPartnerUserServiceImpl
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description: 普通合伙人用户管理接口实现类
 * @version: v1.0
 */
@Service
public class GeneralPartnerUserServiceImpl implements GeneralPartnerUserService {

    @Autowired
    private GeneralPartnerUserMapperExtra mapper;

    /**
     * 获取普通合伙人推广用户信息
     * @param user
     * @param pageDTO
     * @return
     */
    @Override
    public JSONResult getGeneralPartnerUserTableData(User user, GeneralPartnerUserPageDTO pageDTO) {
        pageDTO.setUserId(user.getUserId());
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize(),true);
        List<GeneralPartnerUser> partnerUserDTO = mapper.getTableData(pageDTO);
        System.out.println(partnerUserDTO);

        PageInfo<GeneralPartnerUser> pageInfo = new PageInfo<>(partnerUserDTO);

        return new JSONResult("查询成功",200,pageInfo);
    }


    /**
     * 获取普通合伙人旗下的普通用户的收益
     * @param userIncomeStatisticDTO
     * @return
     */
    @Override
    public JSONResult getIncomeStatistic(UserIncomeStatisticDTO userIncomeStatisticDTO) {
        HumanAmountStatistic statistic = mapper.getIncomeStatistic(userIncomeStatisticDTO);
        return new JSONResult("获取收益成功",200,statistic);
    }


    /**
     * 获取普通合伙人旗下的普通用户推荐人数
     * @param userId
     * @return
     */
    @Override
    public JSONResult getPromoterStatistic(String userId) {
        Date currentDate = new Date();
        HumanAmountStatistic statistic = mapper.getPromoterStatistic(userId);
        return new JSONResult("获取推荐人数成功",200,statistic);
    }
}

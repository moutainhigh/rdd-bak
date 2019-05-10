package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.appAnnouncementDTO;
import com.cqut.czb.bn.entity.entity.*;

import java.util.List;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/20
 * 作用：广告表管理
 */
public interface AppHomePageService {

    /**
     * 获取广告内容表
     * @return
     */
    List<appAnnouncementDTO> selectAnnouncement();

    /**
     * 获取油卡销售地域配置表
     * @return
     */
    List<PetrolSaleConfig> selectPetrolSaleConfig();

    /**
     * 获取服务套餐表
     * @return
     */
    List<ServicePlan> selectServicePlan();

    /**
     * 获取油卡专区的信息
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone();

    /**
     * 获取未售出的油卡
     * @return
     */
    boolean selectAllPetrol();

    /**
     * 获取首页的所有路由
     * @param appRouter
     * @return
     */
    List<AppRouter> selectHomePageRouters(AppRouter appRouter);

}

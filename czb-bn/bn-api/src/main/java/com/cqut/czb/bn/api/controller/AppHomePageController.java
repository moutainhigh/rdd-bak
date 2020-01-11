package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.entity.dto.IOSHomeTabDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import io.swagger.models.Model;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：陈德强
 * 作用：手机app公告管理，对应公告表：announcement
 * 创建时间：2019/4/20
 */
@RestController
@RequestMapping("/api/AppHomePage")
public class AppHomePageController {

    @Autowired
    AppHomePageService appHomePageService;

    @Autowired
    IDictService iDictService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RiderService riderService;

    /**
     * app广告展示
     * @return
     */
    @RequestMapping(value = "/selectAnnouncement",method = RequestMethod.GET)
    public JSONResult selectAnnouncement(@RequestParam("locationCode") String locationCode){
        if(locationCode==null&&locationCode.equals("")) {
            locationCode = "homePage";
        }
        return new JSONResult(appHomePageService.selectAnnouncement(locationCode));
    }

    /**
     * 控制IOS的Home展示
     */
    @GetMapping("/getHomeTab")
    public JSONResult getHomeTab(){
        List<IOSHomeTabDTO> list = new ArrayList();
        IOSHomeTabDTO iosHomeTabDTO1 = new IOSHomeTabDTO();
        iosHomeTabDTO1.setTabName("首页");
        iosHomeTabDTO1.setImage("首页灰");
        iosHomeTabDTO1.setSelectedImage("首页");
        iosHomeTabDTO1.setIdentifierName("homepage");
        IOSHomeTabDTO iosHomeTabDTO2 = new IOSHomeTabDTO();
        iosHomeTabDTO2.setTabName("养车专区");
        iosHomeTabDTO2.setImage("洗车灰");
        iosHomeTabDTO2.setSelectedImage("洗车");
        iosHomeTabDTO2.setIdentifierName("WashHome");
        IOSHomeTabDTO iosHomeTabDTO3 = new IOSHomeTabDTO();
        iosHomeTabDTO3.setTabName("消息中心");
        iosHomeTabDTO3.setImage("消息中心灰");
        iosHomeTabDTO3.setSelectedImage("消息中心");
        iosHomeTabDTO3.setIdentifierName("MessageNav");
        IOSHomeTabDTO iosHomeTabDTO4 = new IOSHomeTabDTO();
        iosHomeTabDTO4.setTabName("个人中心");
        iosHomeTabDTO4.setImage("个人中心灰");
        iosHomeTabDTO4.setSelectedImage("个人中心");
        iosHomeTabDTO4.setIdentifierName("personNav");
        list.add(iosHomeTabDTO1);
        list.add(iosHomeTabDTO2);
        list.add(iosHomeTabDTO3);
        list.add(iosHomeTabDTO4);
        return new JSONResult(list);
    }

    /**
     * app油价播报
     * @return
     */
    @RequestMapping(value = "/selectPetrolPriceReport",method = RequestMethod.GET)
    public JSONResult selectPetrolPriceReport(@RequestParam(name="area") String area){
        return new JSONResult(appHomePageService.selectPetrolPriceReport(area));
    }

    /**
     * app企业套餐，对应服务套餐表
     * @return
     */
    @RequestMapping(value = "/selectServicePlan",method = RequestMethod.GET)
    public JSONResult selectServicePlan(){
        return new JSONResult(appHomePageService.selectServicePlan());
    }

    /**
     * app油卡专区，查找出对应油卡表未售出的油卡，以及获取保存未售出油卡 0国通，1中石油，2中石化
     * @return
     */
    @RequestMapping(value = "/selectPetrolZone",method = RequestMethod.GET)
    public JSONResult selectPetrol(Principal principal, @RequestParam(name="area") String area) {
//        Principal principal, @RequestParam(name="area") String area
        User user = (User) redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("2509068153843109");
//        if (user.getUserAccount().equals("15870596710") || user.getUserAccount().equals("15520024205")) {
//            area = "重庆市";
//        }；
//        String area="重庆市";
        if(area==null)
            return new JSONResult("无法获取当前位置", ResponseCodeConstants.FAILURE);
        return new JSONResult(appHomePageService.getPetrolZone(user,area));
    }

    /**
     * 首页菜单路由获取
     */
    @RequestMapping(value ="/selectHomePageRouters",method = RequestMethod.GET)
    public JSONResult selectHomePageRouters( AppRouterDTO appRouterDTO){
        return new JSONResult(appHomePageService.selectHomePageRouters(appRouterDTO));
    }

    /**
     * 油卡信息获取
     */
    @RequestMapping(value ="/selectPetrolInfoDTO",method = RequestMethod.GET)
    public JSONResult selectPetrolInfoDTO(){
        return new JSONResult(appHomePageService.selectPetrolInfoDTO());
    }

    /**
     * 获取油卡开放地区
     */
    @RequestMapping(value ="/selectAllArea",method = RequestMethod.GET)
    public JSONResult selectAllArea(){
        return new JSONResult(appHomePageService.selectArea());
    }

    /**
     * APP油卡库存获取
     */
    @GetMapping("/getPetrolStock")
    public JSONResult getPetrolStock(@Param("area")String area){
        return new JSONResult(appHomePageService.getPetrolStock(area));
    }

    @GetMapping("/isBuyPetrol")
    public JSONResult isBuyPetrol(Principal principal){
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(appHomePageService.isBuyPetrol(user));
    }

}

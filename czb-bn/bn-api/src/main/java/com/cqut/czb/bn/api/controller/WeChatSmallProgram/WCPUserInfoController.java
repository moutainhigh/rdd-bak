package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WCPTabbarInfo;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 20:07
 */
@RestController
@RequestMapping("/api/WCPUserInfo")
public class WCPUserInfoController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WCPUserInfoService wcpUserInfoService;

    @GetMapping("/getWCPUserCode")
    public JSONResult getWCPUserCode(Principal principal){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpUserInfoService.getRecommendQRCode(user));
    }

    @GetMapping("/getTabBarInfo")
    public JSONResult getTabBarInfo(Principal principal){
        try {
            UserDTO user = (UserDTO) redisUtils.get(principal.getName());
            return new JSONResult(wcpUserInfoService.getTabBarInfo(user.getUserId()));
        }catch (Exception e){
            List<WCPTabbarInfo> tabbars = new ArrayList<>();
            WCPTabbarInfo tabbarHomePage = new WCPTabbarInfo();
            tabbarHomePage.setUrl("homePage");
            tabbarHomePage.setImgNormal("/static/homePage.png");
            tabbarHomePage.setImgClick("/static/homePageNo.png");
            tabbarHomePage.setText("首页");

            WCPTabbarInfo tabbarMine = new WCPTabbarInfo();
            tabbarMine.setUrl("mine");
            tabbarMine.setImgNormal("/static/mine_cilck.png");
            tabbarMine.setImgClick("/static/mine.png");
            tabbarMine.setText("我的");
            tabbars.add(tabbarHomePage);
            tabbars.add(tabbarMine);
            return new JSONResult(tabbars);
        }
    }

    @GetMapping("/getWCPCommodityCode")
    public JSONResult getWCPCommodityQrCode(Principal principal, String commodityId){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpUserInfoService.getCommodityQrCode(user.getUserId(), commodityId));
    }
}
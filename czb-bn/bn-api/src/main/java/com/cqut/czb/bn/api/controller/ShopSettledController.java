package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityUserInfoCollectionDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.CommodityUserInfoCollection;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ShopSettledService;
import com.cqut.czb.bn.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/shopSettled")
@RestController
public class ShopSettledController {

    @Autowired
    ShopSettledService shopSettledService;

    @Autowired
    RedisUtils redisUtil;

    /**
     * 服务商信息获取
     * @param shopDTO
     * @param principal
     * @return
     */
    @GetMapping("/getShopInfo")
    public JSONResult getShopInfo(ShopDTO shopDTO, Principal principal){
        User user = (User) redisUtil.get(principal.getName());
        return new JSONResult(shopSettledService.getShopInfo(shopDTO,user));
    }

    /**
     * 服务商编辑
     * @param shopDTO
     * @return
     */
    @PostMapping("/updateShopInfo")
    public JSONResult updateShopInfo(ShopDTO shopDTO){
        return new JSONResult(shopSettledService.updateShopInfo(shopDTO));
    }

    /**
     * 我（服务商）的商品信息
     * @param commodityDTO
     * @param principal
     * @param pageDTO
     * @return
     */
    @GetMapping("/getCommodity")
    public JSONResult getCommodity(CommodityDTO commodityDTO,Principal principal,PageDTO pageDTO){
        User user = (User) redisUtil.get(principal.getName());
        if (user==null||"".equals(user.getUserId())){
            return new JSONResult();
        }
        return new JSONResult(shopSettledService.getCommodity(commodityDTO,pageDTO,user));
    }

    /**
     * 收集信息插入
     * @param commodityUserInfoCollectionDTO
     * @return
     */
    @PostMapping("/insertCollectionInfo")
    public JSONResult insertInfoCollection(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO){
        return new JSONResult(shopSettledService.insertInfo(commodityUserInfoCollectionDTO));
    }

    /**
     * 收集信息获取
     * @param commodityUserInfoCollectionDTO
     * @return
     */
    @PostMapping("/getCollectionInfo")
    public JSONResult getCollectionInfo(@RequestBody CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO){
        return new JSONResult(shopSettledService.selectInfo(commodityUserInfoCollectionDTO));
    }

    @GetMapping("/getInfoByShop")
    public JSONResult getInfoByShop(CommodityUserInfoCollectionDTO commodityUserInfoCollectionDTO){
        return new JSONResult(shopSettledService.selectInfoByShop(commodityUserInfoCollectionDTO));
    }

    /**
     * 商品新增
     * @param commodityDTO
     * @return
     */
    @PostMapping("/insertCommodity")
    public JSONResult insertCommodity( CommodityDTO commodityDTO,@RequestParam("file") MultipartFile file,Principal principal){
        User user = (User) redisUtil.get(principal.getName());
        if (user==null||"".equals(user.getUserId())||file==null){
            return new JSONResult();
        }
        return new JSONResult(shopSettledService.insertCommodity(commodityDTO,file,user));
    }

    /**
     * 商品信息编辑
     * @param commodityDTO
     * @return
     */
    @PostMapping("updateCommodity")
    public JSONResult updateCommodity(CommodityDTO commodityDTO, MultipartFile file,CommodityUserInfoCollectionDTO commodityUserInfoCollectionDTO){
        return new JSONResult();
    }

    /**
     * 商品信息编辑(无文件)
     * @param commodityDTO
     * @return
     */
    @PostMapping("updateCommodityNoFile")
    public JSONResult updateCommodity(@RequestBody CommodityDTO commodityDTO){
        return new JSONResult(shopSettledService.updateCommodityNoFile(commodityDTO));
    }


}

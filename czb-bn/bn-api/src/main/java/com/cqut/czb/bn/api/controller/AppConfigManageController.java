package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AnnouncementService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.security.Principal;

/** AppConfigManageController app配置管理模块
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/AppConfig")
public class AppConfigManageController {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 公告新增
     */
    @PostMapping("/addAnnouncement")
    // Principal principal
    public JSONResult addAnnouncement( Announcement announcement,@RequestParam("file") MultipartFile file) throws Exception {
//        User user = redisUtils.get(principal.getName());user,
        return new JSONResult(announcementService.addAnnouncement(announcement,file));
    }

    /**
     * 公告删除
     */
    @GetMapping("/deleteAnnouncement")
    public JSONResult deletAnnouncement( @RequestParam("id") String id){
        return new JSONResult(announcementService.deleteAnnouncement(id ));
    }

    /**
     * 公告修改
     */
    @GetMapping("/updateAnnouncement")
    //Principal principal,
    public JSONResult updateAnnouncement(Announcement announcement,MultipartFile file){
//        User user = redisUtils.get(principal.getName());user,
        return new JSONResult(announcementService.updateAnnouncement(announcement,file));
    }

    /**
     * 公告查询
     */
    @GetMapping("/getAnnouncement")
    public JSONResult getAnnouncement(Announcement announcement){
        return new JSONResult(announcementService.getAnnouncement(announcement));
    }

    /**
     * 获取图片路径
     */
    @GetMapping("/getPicPath")
    public JSONResult getPicPath(String id){
        return new JSONResult(announcementService.getFileById(id));
    }























    /**
     * 轮播图新增
     */
    @GetMapping("/addRotationChart")
    public JSONResult addRotationChart(){
        return new JSONResult();
    }

    /**
     * 轮播图删除
     */
    @DeleteMapping("/deleteRotationChart")
    public JSONResult deletRotationChart(){
        return new JSONResult();
    }

    /**
     * 轮播图修改
     */
    @GetMapping("/updateRotationChart")
    public JSONResult updateRotationChart(){
        return new JSONResult();
    }

    /**
     * 轮播图查询
     */
    @GetMapping("/getRotationChart")
    public JSONResult getRotationChart(){
        return new JSONResult();
    }

    /**
     * Advertisement广告新增
     */
    @GetMapping("/addAdvertisement")
    public JSONResult addAdvertisement(){
        return new JSONResult();
    }

    /**
     * Advertisement广告修改
     */
    @GetMapping("/updateAdvertisement")
    public JSONResult updateAdvertisement(){
        return new JSONResult();
    }

    /**
     * Advertisement广告查询
     */
    @GetMapping("/getAdvertisement")
    public JSONResult getAdvertisement(){
        return new JSONResult();
    }

    /**
     * Advertisement广告删除
     */
    @GetMapping("/deleteAdvertisement")
    public JSONResult deleteAdvertisement(){
        return new JSONResult();
    }
}

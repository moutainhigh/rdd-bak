package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.announcement.AnnouncementDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.Announcement;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/** AppConfigManageController app配置管理模块
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/AppConfig")
public class AppConfigManageController {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 公告新增
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/addAnnouncement")
    public JSONResult addAnnouncement( Announcement announcement,Principal principal,@RequestParam("file") MultipartFile file) throws Exception {
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(announcementService.addAnnouncement(announcement,file,user));
    }

    /**
     * 公告删除
     */
    @PermissionCheck(role = "管理员")
    @PostMapping ("/deleteAnnouncement")
    public JSONResult deletAnnouncement(@RequestBody Announcement announcement){
        return new JSONResult(announcementService.deleteAnnouncement(announcement.getAnnouncementId() ));
    }

    /**
     * 公告修改+修改上传图片
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/updateAnnouncementFile")
    //Principal principal,
    public JSONResult updateAnnouncementFile(Announcement announcement,Principal principal,@RequestParam("file")MultipartFile file) throws Exception{
        User user =(User)redisUtils.get(principal.getName());
        return new JSONResult(announcementService.updateAnnouncementFile(announcement,file,user));
    }

    /**
     * 公告修改
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/updateAnnouncement")
    public JSONResult updateAnnouncement( @RequestBody Announcement announcement){
        return new JSONResult(announcementService.updateAnnouncement(announcement));
    }

    /**
     * 公告查询
     */
    @GetMapping("/getAnnouncement")
    public JSONResult getAnnouncement(AnnouncementDTO announcementDTO){
        return new JSONResult(announcementService.getAnnouncement(announcementDTO));
    }

    /**
     * 获取图片路径
     */
    @GetMapping("/getPicPath")
    public JSONResult getPicPath(@RequestParam("id") String id){
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

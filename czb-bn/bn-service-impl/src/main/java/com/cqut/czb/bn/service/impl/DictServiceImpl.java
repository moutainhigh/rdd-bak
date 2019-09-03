package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.LoginInfoMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.AppInfoDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.entity.entity.LoginInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    LoginInfoMapperExtra loginInfoMapperExtra;

//    @Autowired
//    RemotePushService remotePushService;

    @Autowired
    RemotePushMapperExtra remotePushMapperExtra;

    @Autowired
    RemotePushMapper remotePushMapper;

    @Override
    public PageInfo<Dict> selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);
        return new PageInfo<>(dictList);
    }

    @Override
    public List<Dict> selectCustomerServiceStaff() {
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "kf";
        dictInputDTO.setName(name);
        return dictMapperExtra.selectDict(dictInputDTO);
    }

    @Override
    public AppInfoDTO selectAndroidInfo(User user, String version,String DeviceToken) {
        if(DeviceToken != null) {
            dealDeviceToken(user.getUserId(), DeviceToken, 1);
        }
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "android";
        dictInputDTO.setName(name);
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);
        ArrayList<String> url = new ArrayList<>();
        for(Dict dict: dictList) {
            if("android_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("android_description".equals(dict.getName())) {
                appInfoDTO.setDescription(dict.getContent());
            }
            if(dict.getName().startsWith("android_url")) {
                url.add(dict.getContent());
            }
        }
        Random random = new Random();

        if("1.0.8".equals(version) || "1.0.7".equals(version) || "1.0.6".equals(version) ||"1.0.3".equals(version) || "1.0.4".equals(version) || "1.0.5".equals(version) || version == null || version == ""){
            appInfoDTO.setIsUpdate(true);
        }else {
            appInfoDTO.setIsUpdate(false);
        }
        appInfoDTO.setUrl(url.get(random.nextInt(url.size())));
        //记录用户登录信息
        recordLoginInfo(name,user.getUserId(),version);

        return appInfoDTO;
    }

    //记录用户登录信息
    public void recordLoginInfo(String deviceType,String userId,String version){
        //检查是否插入过信息
        int num=0;
        num=loginInfoMapperExtra.selectIsExist(userId,deviceType);
        if(num>0){
            //更改信息
            LoginInfo loginInfo=new LoginInfo();
            loginInfo.setDeviceType(deviceType);//登录设备类型
            loginInfo.setLatitude("");//登录地点经度
            loginInfo.setLongitude("");//登录地点的纬度
            loginInfo.setLoginTime(new Date());//最近登录时间
            loginInfo.setSystemVersion(version);//设备系统版本
            loginInfo.setUserId(userId);//用户id
            loginInfo.setUpdateAt(new Date());
            loginInfoMapperExtra.updateByUserIdSelective(loginInfo);
        }else {
            //插入版本信息
            LoginInfo loginInfo=new LoginInfo();
            loginInfo.setRecordId(StringUtil.createId());
            loginInfo.setDeviceType(deviceType);//登录设备类型
            loginInfo.setLatitude("");//登录地点经度
            loginInfo.setLongitude("");//登录地点的纬度
            loginInfo.setLoginTime(new Date());//最近登录时间
            loginInfo.setSystemVersion(version);//设备系统版本
            loginInfo.setUserId(userId);//用户id
            loginInfo.setUpdateAt(new Date());
            loginInfo.setCreateAt(new Date());
            loginInfoMapperExtra.insertSelective(loginInfo);
        }

    }


    @Override
    public AppInfoDTO selectIOSInfo(User user,String version, String DeviceToken) {

        //处理IOSDeviceToken
        if(DeviceToken != null) {
            dealDeviceToken(user.getUserId(), DeviceToken, 2);
        }
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        DictInputDTO dictInputDTO = new DictInputDTO();
        String name = "ios";
        dictInputDTO.setName(name);
        List<Dict> dictList = dictMapperExtra.selectDict(dictInputDTO);

        ArrayList<String> url = new ArrayList<>();
        for(Dict dict: dictList) {
            if("ios_version".equals(dict.getName())) {
                appInfoDTO.setVersion(dict.getContent());
            }
            if("ios_description".equals(dict.getName())) {
                appInfoDTO.setDescription(dict.getContent());
            }
            if(dict.getName().startsWith("ios_url")) {
                url.add(dict.getContent());
            }
        }
        Random random = new Random();
        appInfoDTO.setUrl(url.get(random.nextInt(url.size())));
        if("1.0.0".equals(version) || "1.0.1".equals(version) || "1.0.3".equals(version) || "1.0.4".equals(version) || "1.0.2".equals(version)){
            appInfoDTO.setIsUpdate(true);
        }else {
            appInfoDTO.setIsUpdate(false);
        }


        //记录用户登录信息
        recordLoginInfo(name,user.getUserId(),version);
        return appInfoDTO;
    }

    public void dealDeviceToken(String userId, String DeviceToken,Integer type){
        RemotePush remotePush = remotePushMapperExtra.selectByUser(userId);
        if ( remotePush != null ){
            if (DeviceToken != null || DeviceToken != ""){
                if ( !DeviceToken.equals(remotePush.getDeviceToken()) ){
                    remotePush.setDeviceToken(DeviceToken);
                    remotePushMapper.updateByPrimaryKey(remotePush);
                }
            }
        }else{
            RemotePush model = new RemotePush();
            model.setDeviceId(StringUtil.createId());
            model.setDeviceToken(DeviceToken);
            if (type==1){
                model.setDeviceType(1);
            }else {
                model.setDeviceType(2);
            }
            model.setUserId(userId);
            model.setCreateAt(new Date());
            model.setUpdateAt(new Date());
            remotePushMapper.insertSelective(model);
        }
    }

    @Override
    public boolean updateDict(DictInputDTO dictInputDTO) {
        return dictMapperExtra.updateDict(dictInputDTO) > 0;
    }

    @Override
    public boolean deleteDict(DictInputDTO dictInputDTO) {
        return dictMapperExtra.deleteDict(dictInputDTO.getDictId()) > 0;
    }

    @Override
    public boolean insertDict(DictInputDTO dictInputDTO) {
        dictInputDTO.setDictId(StringUtil.createId());
        return dictMapperExtra.insertDict(dictInputDTO) > 0;
    }

    @Override
    public Dict getDicByName(DictInputDTO dictInputDTO) {
        return dictMapperExtra.selectDictByName(dictInputDTO.getName()) ;
    }
}

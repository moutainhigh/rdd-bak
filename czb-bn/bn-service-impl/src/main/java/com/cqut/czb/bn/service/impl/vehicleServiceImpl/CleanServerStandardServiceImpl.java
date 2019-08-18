package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerStandardMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.service.vehicleService.CleanServerStandardService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class CleanServerStandardServiceImpl implements CleanServerStandardService {
    @Autowired
    ServerStandardMapper serverStandardMapper;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    @Override
    public JSONResult add(ServerStandard serverStandard, MultipartFile file, User user) {
        String address = "";
        try{
            if (file!=null||!file.isEmpty()) {
                address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
            File file1 = announcementServiceImpl.setFile(file.getOriginalFilename(),address,user.getUserId(),new Date());
            fileMapperExtra.insertSelective(file1);
            serverStandard.setFileId(file1.getFileId());
            serverStandard.setServerId(StringUtil.createId());
            serverStandard.setCreateAt(new Date());
            if(serverStandardMapper.insertSelective(serverStandard) > 0) {
                return new JSONResult("新增洗车服务类型成功",200);
            } else {
                return new JSONResult("新增洗车服务类型失败", 500);
            }
        } catch(Exception e) {
            return new JSONResult("图片上传失败",500);
        }
    }

    @Override
    public JSONResult delete(String id) {
        fileMapperExtra.deleteByPrimaryKey(serverStandardMapper.selectByPrimaryKey(id).getFileId());
        if(serverStandardMapper.deleteByPrimaryKey(id) > 0) {
            return new JSONResult("删除洗车服务类型成功",200);
        } else {
            return new JSONResult("删除洗车服务类型失败", 500);
        }
    }

    @Override
    public JSONResult change(ServerStandard serverStandard, MultipartFile file, User user) {
        try{
            if (file!=null||!file.isEmpty()) {
                File file1 = fileMapperExtra.selectByPrimaryKey(serverStandard.getFileId());
                file1.setSavePath(FileUploadUtil.putObject(file.getOriginalFilename(),file.getInputStream()));
                file1.setFileName(file.getOriginalFilename());
                file1.setUploader(user.getUserId());
                file1.setUpdateAt(new Date());
                fileMapperExtra.updateByPrimaryKeySelective(file1);
            }
            if(serverStandardMapper.updateByPrimaryKeySelective(serverStandard) > 0) {
                return new JSONResult("修改洗车服务类型成功",200);
            } else {
                return new JSONResult("修改洗车服务类型失败", 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult("修改图片失败", 500);
        }
    }

    @Override
    public JSONResult changeWithoutImage(ServerStandard serverStandard) {
        if(serverStandardMapper.updateByPrimaryKeySelective(serverStandard) > 0) {
            return new JSONResult("修改洗车服务类型成功",200);
        } else {
            return new JSONResult("修改洗车服务类型失败", 500);
        }
    }

    @Override
    public JSONResult search(ServerStandard serverStandard, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<ServerStandard> standardPageInfo = serverStandardMapper.search(serverStandard);

        return new JSONResult("查询洗车服务类型成功", 200, new PageInfo<>(standardPageInfo));
    }
}

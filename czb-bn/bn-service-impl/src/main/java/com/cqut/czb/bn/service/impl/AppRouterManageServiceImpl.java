package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.service.AppRouterManageService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class AppRouterManageServiceImpl implements AppRouterManageService {

    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;
    @Autowired
    FileMapperExtra fileMapperExtra;
    @Override
    public PageInfo<AppRouter> getMenuList(AppRouter appRouter,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<AppRouter> appRouters = appRouterMapperExtra.selectByPrimaryKey(appRouter);
        return new PageInfo<>(appRouters);
    }

    @Override
    public Boolean insertMenu(AppRouter appRouter, MultipartFile file) throws Exception{
        String address = "";
        if (file!=null||!file.isEmpty()) {
            address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
        }
        File file1 = announcementServiceImpl.setFile(file.getOriginalFilename(),address,"wo",new Date());
        fileMapperExtra.insertSelective(file1);
        appRouter.setIconPathId(file1.getFileId());
        return appRouterMapperExtra.insertSelective(appRouter);
    }

    @Override
    public Boolean deleteMenu(AppRouter appRouter) {
        fileMapperExtra.deleteByPrimaryKey(appRouter.getIconPathId());
        return appRouterMapperExtra.deleteByPrimaryKey(appRouter.getRouterId());
    }

    @Override
    public Boolean updateMenuNoFile(AppRouter appRouter) {
        return appRouterMapperExtra.updateByPrimaryKeySelective(appRouter);
    }

    @Override
    public Boolean updateMenu(AppRouter appRouter,MultipartFile file) throws Exception{
        File file1 = fileMapperExtra.selectByPrimaryKey(appRouter.getIconPathId());
        file1.setSavePath(FileUploadUtil.putObject(file.getOriginalFilename(),file.getInputStream()));
        file1.setUpdateAt(new Date());
        fileMapperExtra.updateByPrimaryKeySelective(file1);
        return appRouterMapperExtra.updateByPrimaryKeySelective(appRouter);
    }
}

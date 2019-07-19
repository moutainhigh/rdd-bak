package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作用：后台app菜单管理
 */
@Service
public interface AppRouterManageService {

    PageInfo<AppRouter> getMenuList(AppRouter appRouter, PageDTO pageDTO);

    Boolean insertMenu(AppRouter appRouter, MultipartFile file,User user) throws  Exception;

    Boolean deleteMenu(AppRouter appRouter);

    Boolean updateMenu(AppRouter appRouter, MultipartFile file, User user) throws Exception;

    Boolean updateMenuNoFile(AppRouter appRouter);
}

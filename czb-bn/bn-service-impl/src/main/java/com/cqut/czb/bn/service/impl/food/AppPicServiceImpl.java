package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.AppRouterMapperExtra;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.food.AppPicService;
import com.cqut.czb.bn.service.impl.AppHomePageServiceImpl;
import com.cqut.czb.bn.util.springUtil.SpringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class AppPicServiceImpl implements AppPicService{

    @Autowired
    AppHomePageServiceImpl appHomePageServiceImpl;
    @Autowired
    AppRouterMapperExtra appRouterMapperExtra;
    @Autowired
    SpringUtil springUtil;

    @Override
    public List<AppRouterDTO> getPic(AppRouterDTO appRouterDTO, String code, String userId, String area, HashMap<String, T> name) {
        List<AppRouterDTO> list = new ArrayList<>();
        try {
            Object newClass = springUtil.getBean(code+"ServiceImpl");
            AppRouterDTO result = (AppRouterDTO) newClass.getClass().getMethod("getPic",AppRouterDTO.class).invoke(newClass,appRouterDTO);
            if (result==null || result.getSavePath()==null){
                return null;
            }
            list.add(result);

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}

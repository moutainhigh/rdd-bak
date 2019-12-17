package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;

public interface AppPicService {

   List<AppRouterDTO> getPic(AppRouterDTO appRouterDTO, String code, String userId, String area, HashMap<String,T>name);

}

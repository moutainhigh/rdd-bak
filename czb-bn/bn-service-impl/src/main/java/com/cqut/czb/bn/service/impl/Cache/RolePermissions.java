package com.cqut.czb.bn.service.impl.Cache;

import com.cqut.czb.bn.entity.dto.roleApi.RoleApiIdDTO;
import com.cqut.czb.bn.entity.dto.roleApi.RoleApiUrlDTO;
import com.cqut.czb.bn.entity.entity.RoleApi;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RolePermissions {

    private static Map<String, List<RoleApiUrlDTO>> rolePermissionMap = new ConcurrentHashMap<>();

    /**
     * 更新角色权限缓存map
     * @param apiDTOList
     * @return
     */
    public static int updateMap(List<RoleApiIdDTO> apiDTOList){
        rolePermissionMap.clear();
        for(RoleApiIdDTO roleApiDTO:apiDTOList){
            rolePermissionMap.put(roleApiDTO.getRoleId(),roleApiDTO.getApi());
        }
        return rolePermissionMap.size();
    }

    public static List<RoleApiUrlDTO> getCurrentApiByRoleId(String roleId){
        return rolePermissionMap.get(roleId);
    }

    public static boolean checkApi(String roleId,String apiPath){
        if(StringUtil.isNullOrEmpty(apiPath)){
           return false;
        }
        List<RoleApiUrlDTO> roleApiList = getCurrentApiByRoleId(roleId);
        for(RoleApiUrlDTO roleApiUrlDTO:roleApiList){
            if(apiPath.equals(roleApiUrlDTO.getApiUrl())){
                return true;
            }
        }
        return false;
    }

}

package com.cqut.czb.bn.dao.mapper;


import com.cqut.czb.bn.entity.dto.role.ElTreeDTO;
import com.cqut.czb.bn.entity.dto.role.GetRoleDTO;
import com.cqut.czb.bn.entity.dto.role.ResultRoleDTO;
import com.cqut.czb.bn.entity.dto.role.RoleTypeDTO;
import com.cqut.czb.bn.entity.entity.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作者：杨强
 * 时间：2018-04-05
 */
public interface RoleManagementMapper {
    /**
     * 查询、搜索角色
     * @param role
     * @return
     */
    Page<ResultRoleDTO> selectRole(@Param("role") GetRoleDTO role, @Param("dataScope") String dataScope, @Param("id") String id);


    /**
     * 获取自己创建的角色
     */
    Page<ResultRoleDTO> selectRoleCreateBySelf(@Param("id") String id);

    /**
     * 查询一个角色相关信息
     * @param id
     * @return
     */
    ResultRoleDTO getARole(@Param("id") String id);

    /**
     * 插入一个角色
     * @param role
     * @return
     */
    boolean insert(Role role);

    /**
     * 更新一个角色
     * @param role
     * @return
     */
    boolean update(Role role);

    /**
     * 删除一个角色
     * @param id
     * @return
     */
    boolean delete(@Param("id") String id);

    /**
     * 删除多个角色
     * @param ids
     * @return
     */
    boolean deleteRoles(@Param("ids") String[] ids);

    /**
     * 获取资源列表
     * @return
     */
    List<ElTreeDTO> selectSource();

    /**
     * 获取一个角色拥有资源
     * @param id
     * @return
     */
    List<String> selectRoleSource(@Param("id") String id);

    /**
     * 删除现有角色资源
     * @param id
     * @return
     */
    boolean deleteSource(@Param("id") String id);

    /**
     *  设置角色新资源
     * @param resource
     * @param id
     * @return
     */
    boolean setSource(@Param("resource") String[] resource, @Param("id") String id);

    /**
     * 设置数据范围
     * @param dataScope
     * @param id
     * @return
     */
    boolean setDataScope(@Param("dataScope") String dataScope, @Param("id") String id);

    /**
     * 设置自定义数据范围
     * @param dataScope
     * @param id
     * @return
     */
    boolean setCustomDataScope(@Param("dataScope") String[] dataScope, @Param("id") String id);

    /**
     * 删除自定义数据范围
     * @param id
     * @return
     */
    boolean deleteCustomDataScope(@Param("id") String id);

    /**
     * 获取一个角色自定义数据范围列表
     * @param id
     * @return
     */
    List<String> selectCustomDataScope(@Param("id") String id);

    /**
     * 获取一个角色数据范围
     * @param id
     * @return
     */
    List<String> selectDataScope(@Param("id") String id);

    /**
     * 获取角色类型
     */
    List<RoleTypeDTO> selectRoleType();

    boolean insertTest();
}
package com.cqut.czb.bn.dao.mapper.excode;

import com.cqut.czb.bn.entity.entity.exchangeCode.ExchangeCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (ExchangeCode)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-10 22:54:06
 */
public interface ExchangeCodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    ExchangeCode queryById(String code);

    /**
     * 查询指定行数据
     *
     * @param exchangeCode 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ExchangeCode> queryAllByLimit(@Param("query") ExchangeCode exchangeCode, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param exchangeCode 查询条件
     * @return 总行数
     */
    long count(ExchangeCode exchangeCode);

    /**
     * 新增数据
     *
     * @param exchangeCode 实例对象
     * @return 影响行数
     */
    int insert(ExchangeCode exchangeCode);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExchangeCode> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ExchangeCode> entities);


    int insertOrUpdateBatch(@Param("entities") List<ExchangeCode> entities);

    /**
     * 修改数据
     *
     * @param exchangeCode 实例对象
     * @return 影响行数
     */
    int update(ExchangeCode exchangeCode);

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 影响行数
     */
    int deleteById(String code);

}


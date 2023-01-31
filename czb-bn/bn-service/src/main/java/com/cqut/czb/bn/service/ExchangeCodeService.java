package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.exchangeCode.ExchangeCode;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ExchangeCode)表服务接口
 *
 * @author makejava
 * @since 2022-12-10 22:54:09
 */
public interface ExchangeCodeService {

    JSONResult exchangeCode(User user, String codeId);

    void genCode(int num, Double amount);

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    ExchangeCode queryById(String code);

    /**
     * 分页查询
     *
     * @param exchangeCode 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ExchangeCode> queryByPage(ExchangeCode exchangeCode, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param exchangeCode 实例对象
     * @return 实例对象
     */
    ExchangeCode insert(ExchangeCode exchangeCode);

    /**
     * 修改数据
     *
     * @param exchangeCode 实例对象
     * @return 实例对象
     */
    ExchangeCode update(ExchangeCode exchangeCode);

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 是否成功
     */
    boolean deleteById(String code);

}

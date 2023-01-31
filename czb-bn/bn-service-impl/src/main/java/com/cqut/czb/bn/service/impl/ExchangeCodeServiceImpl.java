package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.autoRecharge.UserRechargeMapper;
import com.cqut.czb.bn.dao.mapper.excode.ExchangeCodeDao;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.exchangeCode.ExchangeCode;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ExchangeCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * (ExchangeCode)表服务实现类
 *
 * @author makejava
 * @since 2022-12-10 22:54:10
 */
@Service("exchangeCodeService")
public class ExchangeCodeServiceImpl implements ExchangeCodeService {
    @Resource
    private ExchangeCodeDao exchangeCodeDao;

    @Autowired
    UserRechargeMapper userRechargeMapper;

    @Override
    public JSONResult exchangeCode(User user, String codeId){
        if (user == null || codeId == null){
            return new JSONResult("参数错误", 400);
        }
        ExchangeCode local = queryById(codeId);
        if (local == null){
            return new JSONResult("无效卡密", 400);
        }
        System.out.println("兑换卡密：");
        System.out.println("用户："+user.getUserId());
        System.out.println(local);
        if (local.getIsuse() != 0){
            return new JSONResult("卡密已被使用", 400);
        }
        local.setIsuse(1);
        local.setUserid(user.getUserId());
        local.setUseraccount(user.getUserAccount());
        if (recharge(user.getUserId(), local.getAmount())){
            update(local);
            return new JSONResult("兑换成功", 200);
        }
        return new JSONResult("兑换失败", 400);
    }

    private boolean recharge(String userId, Double amount) {
        BigDecimal beforeBalance = new BigDecimal(String.valueOf(userRechargeMapper.getBalance(userId)));
        BigDecimal afterBalance = null;
        afterBalance = beforeBalance.add(BigDecimal.valueOf(amount));
        System.out.println(afterBalance.doubleValue());
        boolean isBalance = userRechargeMapper.updateRecharge(userId,afterBalance.doubleValue());
        return isBalance;
    }


    @Override
    public void genCode(int num, Double amount){
        ArrayList<ExchangeCode> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ExchangeCode c = new ExchangeCode();
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase(Locale.ROOT);
            code = code.replace('O', 'X').replace('0', 'A');
            c.setCode(code);
            c.setIsuse(0);
            c.setAmount(amount);
            c.setUpdatetime(new Date());
            list.add(c);
        }
        exchangeCodeDao.insertBatch(list);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    @Override
    public ExchangeCode queryById(String code) {
        return this.exchangeCodeDao.queryById(code);
    }

    /**
     * 分页查询
     *
     * @param exchangeCode 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<ExchangeCode> queryByPage(ExchangeCode exchangeCode, PageRequest pageRequest) {
        long total = this.exchangeCodeDao.count(exchangeCode);
        return new PageImpl<>(this.exchangeCodeDao.queryAllByLimit(exchangeCode, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param exchangeCode 实例对象
     * @return 实例对象
     */
    @Override
    public ExchangeCode insert(ExchangeCode exchangeCode) {
        this.exchangeCodeDao.insert(exchangeCode);
        return exchangeCode;
    }

    /**
     * 修改数据
     *
     * @param exchangeCode 实例对象
     * @return 实例对象
     */
    @Override
    public ExchangeCode update(ExchangeCode exchangeCode) {
        this.exchangeCodeDao.update(exchangeCode);
        return this.queryById(exchangeCode.getCode());
    }

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String code) {
        return this.exchangeCodeDao.deleteById(code) > 0;
    }
}

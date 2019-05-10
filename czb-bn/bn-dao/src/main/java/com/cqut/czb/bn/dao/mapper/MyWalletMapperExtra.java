package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.WithDrawLogDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

public interface MyWalletMapperExtra {
    BalanceAndInfoIdDTO getUserAllIncome(@Param("userId") String userId);

    int increaseWithdrawed(@Param("infoId") String infoId,@Param("paymentAmount") String paymentAmount);

    int insertIncomeLog(IncomeLogDTO incomeLogDTO);

    String getPsw(@Param("userId") String userId);

    BigDecimal getBalance(@Param("userId") String userId);

    // 获取提现记录
    List<WithDrawLogDTO> getWithdrawLog(@Param("userId")String userId);
}

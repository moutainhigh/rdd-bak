package com.cqut.czb.bn.dao.mapper.withDrawals;

import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsInsertDTO;
import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsSelectDTO;

import java.util.List;

public interface WithDrawalsMapperExtra {
    List<WithdrawalsSelectDTO> getRecode(WithdrawalsSelectDTO withdrawalsSelectDTO);

    String getTotalAmount(WithdrawalsSelectDTO withdrawalsSelectDTO);

    String getTodayTotalAmount(WithdrawalsSelectDTO withdrawalsSelectDTO);

    String getMonthTotalAmount(WithdrawalsSelectDTO withdrawalsSelectDTO);

    int addMoney(WithdrawalsInsertDTO withdrawalsInsertDTO);

    int insertRecode(WithdrawalsInsertDTO withdrawalsInsertDTO);

}

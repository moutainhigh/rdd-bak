package com.cqut.czb.bn.service.WithDrawLog;

import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsInsertDTO;
import com.cqut.czb.bn.entity.dto.withdrawals.WithdrawalsSelectDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface WithDrawalsService {
    JSONResult getRecode(WithdrawalsSelectDTO withdrawalsSelectDTO);

    JSONResult getTotal(WithdrawalsSelectDTO withdrawalsSelectDTO);

    Workbook export(WithdrawalsSelectDTO withdrawalsSelectDTO) throws Exception;

    JSONResult addMoney(WithdrawalsInsertDTO withdrawalsInsertDTO);
}

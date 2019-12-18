package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatBalanceRecord;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatTOWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface WeChatWithdrawMapperExtra {
   List<WeChatWithdrawDTO> selectWithdrawList(WeChatWithdrawDTO weChatWithdrawDTO);


   WeChatBalanceRecord getBalance(@Param("infoId")String infoId);

   int increaseWithdrawed(@Param("infoId") String infoId,@Param("paymentAmount") String paymentAmount);

   int insertIncomeLog(IncomeLogDTO incomeLogDTO);
}

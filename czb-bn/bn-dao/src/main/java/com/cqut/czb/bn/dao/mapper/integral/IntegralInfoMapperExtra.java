package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralInfo;
import com.cqut.czb.bn.entity.entity.integral.IntegralLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegralInfoMapperExtra {
    IntegralInfo selectByUserId(String userId);

    IntegralInfoDTO selectGainAndLossIntegralByUserId(String userId);

    List<IntegralInfoDTO> selectIntegralInfoList(IntegralInfoDTO integralInfoDTO);

    int updateByPrimaryKeySync(IntegralInfo record);

    int updateAll(IntegralLog record);

    int getUserAmount();

    IntegralLogDTO getIntegralInfo(@Param("userId")String userId);

    IntegralInfoDTO getGotTotal(@Param("userId")String userId);
}

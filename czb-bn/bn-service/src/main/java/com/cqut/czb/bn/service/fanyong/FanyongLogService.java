package com.cqut.czb.bn.service.fanyong;

import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface FanyongLogService {
    boolean isContainFanyongLog(String orgId);

    JSONResult getLogData(FanyongLogDto fanyongLogDto);

    Workbook exportFanyongLog(FanyongLogDto fanyongLogDto) throws Exception;

    JSONResult getFanyongTotalAmount(FanyongLogDto fanyongLogDto);
}

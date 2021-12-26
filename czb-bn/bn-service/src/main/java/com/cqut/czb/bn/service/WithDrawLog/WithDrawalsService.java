package com.cqut.czb.bn.service.WithDrawLog;

import com.cqut.czb.bn.entity.dto.fanyong.FanyongLogDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface WithDrawalsService {
    JSONResult getRecode(FanyongLogDto fanyongLogDto);

    JSONResult getTotal(FanyongLogDto fanyongLogDto);

    Workbook export(FanyongLogDto fanyongLogDto) throws Exception;
}

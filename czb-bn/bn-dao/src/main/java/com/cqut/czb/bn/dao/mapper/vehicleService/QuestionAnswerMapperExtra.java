package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.QuestionAnswer.QuestionAnswerDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-29 17:06
 */
public interface QuestionAnswerMapperExtra {
    List<QuestionAnswerDTO> selectQuestionAnswerByType(Integer type);

    QuestionAnswerDTO selectByPrimaryKey(String questionId);

    List<String> selectQuestionLabelListByType(Integer type);

    List<QuestionAnswerDTO> selectSimilarQuestionLabel(@Param("type") Integer type, @Param("questionLabel") String questionLabel);
}
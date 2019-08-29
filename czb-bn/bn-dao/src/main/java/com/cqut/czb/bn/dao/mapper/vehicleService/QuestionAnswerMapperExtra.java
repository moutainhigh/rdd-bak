package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.QuestionAnswer.QuestionAnswerDTO;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-29 17:06
 */
public interface QuestionAnswerMapperExtra {
    List<QuestionAnswerDTO> selectQuestionAnswerByType(Integer type);

    QuestionAnswerDTO selectByPrimaryKey(String questionId);
}
package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.QuestionAnswer.QuestionAnswerDTO;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-29 17:00
 */
public interface QuestionAnswerService {
    List<QuestionAnswerDTO> getQuestionAnswerListByType(Integer type);

    QuestionAnswerDTO getQuestionAnswerById(String questionId);
}
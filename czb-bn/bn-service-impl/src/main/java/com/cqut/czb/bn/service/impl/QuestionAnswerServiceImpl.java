package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.vehicleService.QuestionAnswerMapperExtra;
import com.cqut.czb.bn.entity.dto.QuestionAnswer.QuestionAnswerDTO;
import com.cqut.czb.bn.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-29 17:00
 */
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    QuestionAnswerMapperExtra questionAnswerMapperExtra;

    @Override
    public List<QuestionAnswerDTO> getQuestionAnswerListByType(Integer type) {
        return questionAnswerMapperExtra.selectQuestionAnswerByType(type);
    }

    @Override
    public QuestionAnswerDTO getQuestionAnswerById(String questionId) {
        return questionAnswerMapperExtra.selectByPrimaryKey(questionId);
    }
}
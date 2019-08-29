package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther nihao
 * @create 2019-08-29 16:55
 */
@EnableAsync
@RestController
@RequestMapping("/api/QuestionAnswer")
public class QuestionAnswerController {
    @Autowired
    QuestionAnswerService questionAnswerService;

    @GetMapping("/getQuestionAnswerByType")
    public JSONResult getQuestionAnswerByType(Integer type){
        return new JSONResult(questionAnswerService.getQuestionAnswerListByType(type));
    }
}
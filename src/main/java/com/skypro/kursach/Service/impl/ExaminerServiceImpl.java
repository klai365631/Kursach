package com.skypro.kursach.Service.impl;

import com.skypro.kursach.Service.ExaminerService;
import com.skypro.kursach.Service.QuestionService;
import com.skypro.kursach.exception.IncorrectAmountOfQuestions;
import com.skypro.kursach.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
@Service

public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount>questionService.getAll().size()||amount<=0){
            throw new IncorrectAmountOfQuestions();
        }
        Set<Question> questions = new HashSet<>();
        while (questions.size() < amount) {
            questions.add(questionService.getRandomQuestion());

        }
        return questions;
    }


}

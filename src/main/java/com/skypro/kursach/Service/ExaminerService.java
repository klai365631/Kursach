package com.skypro.kursach.Service;

import com.skypro.kursach.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}

package com.skypro.kursach;

import com.skypro.kursach.Service.QuestionService;
import com.skypro.kursach.Service.impl.JavaQuestionService;
import com.skypro.kursach.exception.AttemptToAddAQuestionThatAlreadyExistsException;
import com.skypro.kursach.exception.AttemptToDeleteAQuestionThatDoesNotExistException;
import com.skypro.kursach.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void afterEach(){
        questionService.getAll().forEach(questionService::remove);
    }


    @Test
    public void positiveAndNegativeTestForAddingAnHuman(){
        assertThat(questionService.getAll()).isEmpty();
        Question expected = add(new Question("q1", "a1"));

        questionService.add("q2", "a2");

        assertThat(questionService.getAll()).hasSize(2);
        assertThat(questionService.getAll()).contains(expected,new Question("q2", "a2"));

        assertThatExceptionOfType(AttemptToAddAQuestionThatAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add(expected));

        assertThatExceptionOfType(AttemptToAddAQuestionThatAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add("q2", "a2"));
    }

    @Test
    public void shouldRemoveAllQuestions(){
        assertThat(questionService.getAll()).isEmpty();

        assertThatExceptionOfType(AttemptToDeleteAQuestionThatDoesNotExistException.class)
                .isThrownBy(() -> questionService.remove(new Question("test", "test")));

        Question expected = add(new Question("q1", "a1"));

        assertThatExceptionOfType(AttemptToDeleteAQuestionThatDoesNotExistException.class)
                .isThrownBy(() -> questionService.remove(new Question("test", "test")));

        questionService.remove(expected);

        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    public void getRandomQuestionTest(){
        for (int i = 1; i <=5 ; i++) {
            add(new Question("q1" + i, "q2" + i));

    }

        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());

    }

    private Question add(Question question){

        int sizeBefore = questionService.getAll().size();

        questionService.add(question);

        assertThat(questionService.getAll()).hasSize(sizeBefore +1);
        assertThat(questionService.getAll()).contains(question);

        return question;
    }




}

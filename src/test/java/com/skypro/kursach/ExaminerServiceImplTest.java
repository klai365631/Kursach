package com.skypro.kursach;

import com.skypro.kursach.Service.QuestionService;
import com.skypro.kursach.Service.impl.ExaminerServiceImpl;
import com.skypro.kursach.Service.impl.JavaQuestionService;
import com.skypro.kursach.exception.IncorrectAmountOfQuestions;
import com.skypro.kursach.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    private static final int SIZE = 5;
    @Mock
    private JavaQuestionService javaQuestionService;
    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private List<Question> questions;


    @BeforeEach
    public void beforeEach() {
        questions = Stream.iterate(1, i -> i + 1)
                .limit(SIZE)
                .map(i -> new Question("q" + 1, "a" + i))
                .collect(Collectors.toList());

        when(javaQuestionService.getAll()).thenReturn(questions);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void getQuestionsNegative(int amount) {
        assertThatExceptionOfType(IncorrectAmountOfQuestions.class)
                .isThrownBy(() -> examinerService.getQuestions(amount));

    }

    @Test
    public void theTestOfQuestionsByTheRandomMethod() {
        when(javaQuestionService.getRandomQuestion()).thenReturn(
                questions.get(0),
                questions.get(2),
                questions.get(0),
                questions.get(4)

        );
        assertThat(examinerService.getQuestions(3)).containsExactly(questions.get(0), questions.get(2), questions.get(4));

    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(SIZE + 1),
                Arguments.of(SIZE + 100)
        );
    }
}


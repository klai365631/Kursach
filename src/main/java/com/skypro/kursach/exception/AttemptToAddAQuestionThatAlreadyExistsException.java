package com.skypro.kursach.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class AttemptToAddAQuestionThatAlreadyExistsException extends RuntimeException{
}

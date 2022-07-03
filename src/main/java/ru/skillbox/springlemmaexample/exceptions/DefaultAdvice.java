package ru.skillbox.springlemmaexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillbox.springlemmaexample.dto.ErrorResponse;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(WordNotFitToDictionaryException.class)
    public ResponseEntity<ErrorResponse> handleException(WordNotFitToDictionaryException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

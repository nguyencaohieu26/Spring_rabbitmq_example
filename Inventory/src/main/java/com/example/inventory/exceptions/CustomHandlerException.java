package com.example.inventory.exceptions;

import com.example.inventory.response.HandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomHandlerException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HandlerResponse handlerNotFoundException(NotFoundException e, WebRequest req){
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.NO_CONTENT.value())
                .withMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HandlerResponse handlerValidationException(BindException ex){
        //Get error for each field
        List<FieldError> errors = ex.getFieldErrors();
        List<ValidationInventoryException> list =new ArrayList<>();
        for(FieldError e:errors){
            //format field error
            list.add(ValidationInventoryException.ValidationInventoryExceptionBuilder.aValidationInventoryException()
                    .withField(e.getField())
                    .withDefaultMessage(e.getDefaultMessage())
                    .withObjectName(e.getObjectName())
                    .withRejectedValue(e.getRejectedValue())
                    .build());
        }
        //format response
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(HttpStatus.BAD_REQUEST.name())
                .addData("errors",list)
                .build();
    }

    @ExceptionHandler(SystemException.class)
    public HandlerResponse handlerSystemException(SystemException ex){
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
    }
}

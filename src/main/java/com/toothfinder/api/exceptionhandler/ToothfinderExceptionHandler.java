package com.toothfinder.api.exceptionhandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ToothfinderExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageToUser = messageSource.getMessage("mensagem.invalida",null,null);
        String mensageToDeveloper= ex.getCause().toString();
        return handleExceptionInternal(ex, new Erro(messageToUser, mensageToDeveloper), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros = listOfErrors(ex.getBindingResult());
        return handleExceptionInternal(ex,erros, headers, HttpStatus.BAD_REQUEST,request);
    }

    private List<Erro> listOfErrors(BindingResult bindingResult){
        List<Erro> errors = new ArrayList<>();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            String messageToUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String messageToDeveloper = fieldError.toString();
            errors.add(new Erro(messageToUser, messageToDeveloper));
        }
        return errors;
    }

    public static class Erro {
        private String messageToUser;
        private String messageToDeveloper;

        public Erro(String messageToUser, String messageToDeveloper) {
            this.messageToUser = messageToUser;
            this.messageToDeveloper = messageToDeveloper;
        }

        public String getMessageToUser() {
            return messageToUser;
        }

        public void setMessageToUser(String messageToUser) {
            this.messageToUser = messageToUser;
        }

        public String getMessageToDeveloper() {
            return messageToDeveloper;
        }

        public void setMessageToDeveloper(String messageToDeveloper) {
            this.messageToDeveloper = messageToDeveloper;
        }
    }
}

package com.toothfinder.api.exceptionhandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

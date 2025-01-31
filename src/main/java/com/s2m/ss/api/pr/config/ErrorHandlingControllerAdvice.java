package com.s2m.ss.api.pr.config;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Header;
import com.s2m.ss.api.pr.entities.SSEnt_InternelError;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BaseRs;

@RestControllerAdvice
class ErrorHandlingControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    SSEnt_BaseRs handelMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        logger.error("Validation error: {}", e.getMessage(), e);
        SSEnt_InternelError errVal = new SSEnt_InternelError();
        SSEnt_Status status = SSEnt_DF.DEFAULT_REJ_STAT;
        SSEnt_Header header = SSEnt_DF.DEFAULT_HEAD;

        Object obj = e.getBindingResult().getTarget();
        if (obj != null) {
            try {
                Field fieldHead = obj.getClass().getField("header");
                header = (SSEnt_Header) fieldHead.get(obj);
                if (header == null || header.getIdMsg() == null) {
                    header = SSEnt_DF.DEFAULT_HEAD;
                }
            } catch (Exception ex) {
                logger.warn("Unable to extract header: {}", ex.getMessage(), ex);
                header = SSEnt_DF.DEFAULT_HEAD;
            }
        }

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errVal = getMsg(fieldError.getDefaultMessage());
            status.assignvalue(errVal);
        }
        return new SSEnt_BaseRs(header, status);
    }

    @ExceptionHandler(Exception.class)
    SSEnt_BaseRs handelUnexpectedTypeException(Exception e, WebRequest request) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);

        SSEnt_InternelError errVal = new SSEnt_InternelError();
        SSEnt_Status status = new SSEnt_Status(getMsg("ST_REJECTED"), 
                                               e.getClass().getSimpleName(),
                                               e.getMessage());
        SSEnt_Header header = SSEnt_DF.DEFAULT_HEAD;

        Throwable cause = e.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) cause;
            errVal = getMsg("EXP_INVALID");
            errVal.setMsgErreur(String.format(errVal.getMsgErreur(), ex.getValue(),
                                              ex.getPath().get(1).getFieldName(),
                                              ex.getTargetType().getSimpleName()));
            status.assignvalue(errVal);
        } else if (cause instanceof JsonMappingException) {
            JsonMappingException ex = (JsonMappingException) cause;
            errVal = getMsg("JSN_INVALID");
            errVal.setMsgErreur(String.format(errVal.getMsgErreur(), ex.getPath().get(0).getFieldName(),
                                              ex.getOriginalMessage()));
            status.assignvalue(errVal);
        }

        return new SSEnt_BaseRs(header, status);
    }

    @ExceptionHandler(Unauthorized.class)
    SSEnt_BaseRs handelUnauthorized(Unauthorized e, WebRequest request) {
        logger.warn("Unauthorized access attempt: {}", e.getMessage());
        SSEnt_Status status = SSEnt_DF.DEFAULT_REJ_STAT;
        SSEnt_Header header = SSEnt_DF.DEFAULT_HEAD;

        return new SSEnt_BaseRs(header, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    SSEnt_BaseRs handelNoHandlerFoundException(NoHandlerFoundException e, WebRequest request) {
        logger.warn("No handler found for request: {}", e.getRequestURL());

        SSEnt_InternelError errVal = getMsg("HDL_INVALID");
        errVal.setMsgErreur(String.format(errVal.getMsgErreur(), e.getRequestURL()));
        SSEnt_Status status = SSEnt_DF.DEFAULT_REJ_STAT;
        status.assignvalue(errVal);

        SSEnt_Header header = SSEnt_DF.DEFAULT_HEAD;
        return new SSEnt_BaseRs(header, status);
    }

    @ExceptionHandler(NullPointerException.class)
    SSEnt_BaseRs handelNullPointerException(NullPointerException e, WebRequest request) {
        logger.error("Null pointer encountered: {}", e.getMessage(), e);

        SSEnt_InternelError errVal = getMsg("INT_ERROR");
        errVal.setMsgErreur(String.format(errVal.getMsgErreur(),
                                          ((ServletWebRequest) request).getRequest().getRequestURI()));

        SSEnt_Status status = SSEnt_DF.DEFAULT_REJ_STAT;
        status.assignvalue(errVal);

        SSEnt_Header header = SSEnt_DF.DEFAULT_HEAD;
        return new SSEnt_BaseRs(header, status);
    }
}

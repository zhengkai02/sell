package com.iwhalecloud.retail.common.config;

import com.iwhalecloud.retail.common.consts.CommonBaseMessageCodeEnum;
import com.iwhalecloud.retail.common.exception.BaseException;
import com.iwhalecloud.retail.common.exception.ErrorMessage;
import com.iwhalecloud.retail.common.exception.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice {
    public static final HttpStatus DEFAULT_HTTP_STATUS;

    protected Map<String, Integer> httpStatusMap;

    public RestResponseEntityHandler() {
        this(new HashMap(0));
    }

    public RestResponseEntityHandler(Map<String, Integer> httpStatusMap) {
        this.httpStatusMap = httpStatusMap;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorMessage> handleMyException(BaseException ex) {
        if (ex != null) {
            if (ex.getType() == 1) {
                this.logger.error(ex);
            }
            else if (ex.getType() == 0) {
                this.logger.warn(ex);
            }
        }

        if (ex == null) {
            throw new NullPointerException();
        }

        ErrorMessage errorMessage = this.getErrorMessage(ex.getCode(), ex.getDesc(), ex.getDesc(), ex.getType(),
            getStack(ex));
        Integer statusCode = this.httpStatusMap.get(ex.getCode());
        HttpStatus httpStatus;
        if (statusCode == null) {
            httpStatus = DEFAULT_HTTP_STATUS;
        }
        else {
            try {
                httpStatus = HttpStatus.valueOf(statusCode);
            }
            catch (IllegalArgumentException var6) {
                httpStatus = DEFAULT_HTTP_STATUS;
            }
        }

        return new ResponseEntity(errorMessage, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleMyException(Exception ex) {
        logger.error("error = [{}]", ex);
        ResponseEntity<ErrorMessage> responseEntity = null;
        // 现在通过fegin调用微服务，fegin上没有显示的申明抛出BaseException，所以调用的时候会抛出UndeclaredThrowableException，
        // 对UndeclaredThrowableException判断，如果是BaseException则走不同的逻辑
        if (UndeclaredThrowableException.class.isInstance(ex)) {
            UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) ex;

            Throwable throwable = undeclaredThrowableException.getUndeclaredThrowable();
            if (BaseException.class.isInstance(throwable)) {
                BaseException baseException = (BaseException) throwable;
                this.logger.error(baseException);
                responseEntity = handleMyException(baseException);
            }
        }

        if (null != responseEntity) {
            return responseEntity;
        }

        this.logger.error(ex);
        ErrorMessage errorMessage = this.getErrorMessage(CommonBaseMessageCodeEnum.SYSTEM_ERROR.getStatus(),
            CommonBaseMessageCodeEnum.SYSTEM_ERROR.getMessage(), ex.getMessage(), 1, getStack(ex));
        HttpStatus httpStatus = DEFAULT_HTTP_STATUS;
        return new ResponseEntity(errorMessage, httpStatus);
    }

    /**
     * @param ex MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            String lineSeparator = System.getProperty("line.separator", "\r\n");
            fieldErrors.forEach(fieldError -> {
                sb.append(fieldError.getField()).append(":")
                .append(fieldError.getDefaultMessage()).append(lineSeparator);
            });
        }
        ErrorMessage errorMessage = this.getErrorMessage(CommonBaseMessageCodeEnum.PARAM_ERROR.getStatus(),
            sb.toString(), sb.toString(), 1, getStack(ex));
        return new ResponseEntity(errorMessage, status);
    }

    public ErrorMessage getErrorMessage(String code, String message, String description, int type, String stack) {
        return new ErrorMessage(code, message, description, type, stack);
    }

    public static String getStack(Exception ex) {
        return ex.getMessage();
    }

    protected String getMessage(Exception ex) {
        String message = ex.getMessage();
        Throwable cause = ex.getCause();
        if (cause != null) {
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
        }

        if (null != cause) {
            message = cause.getMessage();
        }

        return message;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object bodyForMessage = body;
        if (body == null && response instanceof ServletServerHttpResponse) {
            int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
            if (status == HttpStatus.OK.value()) {
                bodyForMessage = new SuccessMessage();
            }
        }

        return bodyForMessage;
    }

    static {
        DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

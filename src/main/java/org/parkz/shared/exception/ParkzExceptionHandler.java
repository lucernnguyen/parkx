package org.parkz.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.postgresql.exception.PostgresqlExceptionHandler;
import org.springframework.fastboot.rest.common.factory.response.IResponseFactory;
import org.springframework.fastboot.rest.common.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ParkzExceptionHandler extends PostgresqlExceptionHandler {

    public ParkzExceptionHandler(IResponseFactory iResponseFactory) {
        super(iResponseFactory);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> handleQSQLException(DataIntegrityViolationException e) {
        log.error("Error sql:", e);
        PSQLException psqlException = (PSQLException) e.getRootCause();
        IErrorCode errorCode = psqlStateCodes.get(psqlException.getSQLState());
        return this.iResponseFactory.error(Objects.requireNonNullElse(errorCode, ErrorCode.SERVER_ERROR));
    }

    @ExceptionHandler({UndeclaredThrowableException.class})
    public ResponseEntity<ErrorResponse> handleUndeclaredThrowableException(UndeclaredThrowableException e) {
        if (e.getCause() instanceof InvalidException invalidException) {
            return customInvalidExceptionHandler(invalidException);
        }
        return exceptionHandler(e);
    }
}

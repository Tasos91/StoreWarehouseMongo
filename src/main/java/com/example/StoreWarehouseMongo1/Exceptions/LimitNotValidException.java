package com.example.StoreWarehouseMongo1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tasos
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LimitNotValidException extends RuntimeException {

    public LimitNotValidException(String exception) {
        super(exception);
    }

}

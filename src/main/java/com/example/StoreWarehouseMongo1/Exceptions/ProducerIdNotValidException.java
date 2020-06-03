package com.example.StoreWarehouseMongo1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tasos
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProducerIdNotValidException extends RuntimeException {

    public ProducerIdNotValidException(String exception) {
        super(exception);
    }

}

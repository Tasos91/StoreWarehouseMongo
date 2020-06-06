/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tasos
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ColorNotEqualWithPrefixException extends RuntimeException {

    public ColorNotEqualWithPrefixException(String exception) {
        super(exception);
    }

}

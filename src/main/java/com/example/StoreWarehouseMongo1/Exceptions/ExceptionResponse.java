/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.Exceptions;

import java.util.Date;

/**
 *
 * @author Tasos
 */
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private int status;

    public ExceptionResponse(Date timestamp, String message, int status, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

}

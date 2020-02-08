package com.example.StoreWarehouseMongo1.controllers;

/**
 *
 * @author Tasos
 */
public class CustomErrorType {

    private String errorMessage;
    private Integer errorCode;

    public CustomErrorType(String errorMessage, Integer errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}

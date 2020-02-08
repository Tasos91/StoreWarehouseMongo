package com.example.StoreWarehouseMongo1.controllers;

/**
 *
 * @author Tasos
 */
public class CustomErrorType {

    private String errorMessage;

    public CustomErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

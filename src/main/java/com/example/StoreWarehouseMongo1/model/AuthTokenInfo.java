/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

/**
 *
 * @author Tasos
 */

public class AuthTokenInfo {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int daysExpired;
    private String tokenScope;

    public void setAccess_token(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setToken_type(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setRefresh_token(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpires_in(int daysExpired) {
        this.daysExpired = daysExpired;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getDaysExpired() {
        return daysExpired;
    }

    public String getTokenScope() {
        return tokenScope;
    }

    public void setScope(String tokenScope) {
        this.tokenScope = tokenScope;
    }

    public String getAccess_token() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

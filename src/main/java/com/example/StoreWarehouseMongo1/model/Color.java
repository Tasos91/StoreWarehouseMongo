/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Tasos
 */
@Document(collection = "colors")
public class Color {

    @Id
    private String id;
    private int yelloQuantity;
    private int whiteQuantity;
    private int blackQuantity;
    private int bronzeQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWhiteQ() {
        return whiteQuantity;
    }

    public void setWhiteQ(Integer whiteQuantity) {
        this.whiteQuantity = whiteQuantity;
    }

    public Integer getBlackQuantity() {
        return blackQuantity;
    }

    public void setBlackQuantity(Integer blackQuantity) {
        this.blackQuantity = blackQuantity;
    }

    public Integer getBronzeQuantity() {
        return bronzeQuantity;
    }

    public void setBronzeQuantity(Integer bronzeQuantity) {
        this.bronzeQuantity = bronzeQuantity;
    }

    public Integer getYelloQuantity() {
        return yelloQuantity;
    }

    public void setYelloQ(Integer colorQuantity) {
        this.yelloQuantity = colorQuantity;
    }

    public Color() {
    }

    public Color(int yelloQuantity, int whiteQuantity, int blackQuantity, int bronzeQuantity) {
        this.yelloQuantity = yelloQuantity;
        this.whiteQuantity = whiteQuantity;
        this.blackQuantity = blackQuantity;
        this.bronzeQuantity = bronzeQuantity;
    }

    public Color(String id, int yelloQuantity, int whiteQuantity, int blackQuantity, int bronzeQuantity) {
        this.id = id;
        this.yelloQuantity = yelloQuantity;
        this.whiteQuantity = whiteQuantity;
        this.blackQuantity = blackQuantity;
        this.bronzeQuantity = bronzeQuantity;
    }

}

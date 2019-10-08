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
@Document(collection="colors")
public class Color {
    
    @Id
    private String id;
    private int yelloQ;
    private int whiteQ;
    private int blackQ;
    private int bronzeQ;

    public Color(int yelloQ, int whiteQ, int blackQ, int bronzeQ) {
        this.yelloQ = yelloQ;
        this.whiteQ = whiteQ;
        this.blackQ = blackQ;
        this.bronzeQ = bronzeQ;
    }

    

    public Integer getWhiteQ() {
        return whiteQ;
    }

    public void setWhiteQ(Integer whiteQ) {
        this.whiteQ = whiteQ;
    }

    public Integer getBlackQ() {
        return blackQ;
    }

    public void setBlackQ(Integer blackQ) {
        this.blackQ = blackQ;
    }

    public Integer getBronzeQ() {
        return bronzeQ;
    }

    public void setBronzeQ(Integer bronzeQ) {
        this.bronzeQ = bronzeQ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYelloQ() {
        return yelloQ;
    }

    public void setYelloQ(Integer colorQ) {
        this.yelloQ = colorQ;
    }
    
    
}

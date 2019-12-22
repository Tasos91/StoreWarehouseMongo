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
public class Category {

    private String babyPins;
    private String pendants;
    private String Necklaces;
    private String Bracelets;
    private String Earrings;
    private String cross;

    public Category(String babyPins, String pendants, String Necklaces, String Bracelets, String Earrings, String cross) {
        this.babyPins = babyPins;
        this.pendants = pendants;
        this.Necklaces = Necklaces;
        this.Bracelets = Bracelets;
        this.Earrings = Earrings;
        this.cross = cross;
    }

    public Category() {
    }

    public Category(String babyPins) {
        this.babyPins = babyPins;

    }

    public String getBabyPins() {
        return babyPins;
    }

    public void setBabyPins(String babyPins) {
        this.babyPins = babyPins;
    }

    public String getPendants() {
        return pendants;
    }

    public void setPendants(String pendants) {
        this.pendants = pendants;
    }

    public String getNecklaces() {
        return Necklaces;
    }

    public void setNecklaces(String Necklaces) {
        this.Necklaces = Necklaces;
    }

    public String getBracelets() {
        return Bracelets;
    }

    public void setBracelets(String Bracelets) {
        this.Bracelets = Bracelets;
    }

    public String getEarrings() {
        return Earrings;
    }

    public void setEarrings(String Earrings) {
        this.Earrings = Earrings;
    }

    public String getCross() {
        return cross;
    }

    public void setCross(String cross) {
        this.cross = cross;
    }
}

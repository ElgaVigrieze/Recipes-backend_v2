package com.example.demo.enums;

public enum Unit {
    KG("kg"),
    PC("gabals"),
    G("grams"),
    L("litrs"),
    ML("mililitrs"),
    PCK("paciņa"),
    CUP("glāze"),
    HDF("sauja"),
    PN("šķipsna"),
    TSP("tējkarote"),
    TBSP("ēdamkarote"),
    D("pēc garšas");

    private final String unit;

    Unit(String unit){
        this.unit = unit;
    }

    public String getUnit(){
        return this.unit;
    }
    }
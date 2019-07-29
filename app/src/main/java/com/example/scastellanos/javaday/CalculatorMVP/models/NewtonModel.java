package com.example.scastellanos.javaday.CalculatorMVP.models;

import com.google.gson.annotations.SerializedName;

public class NewtonModel {

    @SerializedName("operation")
    String operation;

    @SerializedName("expression")
    String expression;

    @SerializedName("result")
    String result;

    public NewtonModel(String opr, String expr, String res){
        operation = opr;
        expression = expr;
        result = res;
    }

    public String getResult(){ return result; }
}

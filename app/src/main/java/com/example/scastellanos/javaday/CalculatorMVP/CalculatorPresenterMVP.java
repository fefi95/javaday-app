package com.example.scastellanos.javaday.CalculatorMVP;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorPresenterMVP implements CalculatorContractMVP.Presenter {

    CalculatorContractMVP.View mView;

    public CalculatorPresenterMVP(CalculatorContractMVP.View view){
        mView = view;
    }

    @Override
    public void calculateResult(String expr) {
        try {
            evalText(expr);
            mView.updateInputText(expr);
        } catch (Exception e) {
            // handle error
        }
    }

    @Override
    public void addTextIfValid(String text, String charToAdd){
        String tempExpr = text.concat(charToAdd);
        if(isValidExpression(tempExpr)){
            mView.updateInputText(tempExpr);
        }
    }

    private Boolean isValidExpression(CharSequence expr){
        // TODO: implement this
        return true;
    }

    private Double evalText(CharSequence text) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        return (Double)engine.eval(text.toString());
    }
}

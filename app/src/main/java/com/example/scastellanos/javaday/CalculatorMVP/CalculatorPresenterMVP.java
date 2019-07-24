package com.example.scastellanos.javaday.CalculatorMVP;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorPresenterMVP implements CalculatorContractMVP.Presenter {

    CalculatorContractMVP.View mView;
    private final String mathRegex = "^([-]?\\d+(\\.\\d*)?)([-+/*]\\d+(\\.\\d*)?)*[-+/*]?";
    private Pattern mMathPtrn = Pattern.compile(mathRegex);

    public CalculatorPresenterMVP(CalculatorContractMVP.View view){
        mView = view;
    }

    @Override
    public void calculateResult(String expr) {
        try {
            Double res = evalExpression(expr);
            mView.updateInputText(res.toString());
        } catch (Exception e) {
            // handle error
        }
    }

    @Override
    public void addTextIfValid(String text, String charToAdd) {
        String tempExpr = text.concat(charToAdd);
        if(isValidExpression(tempExpr)) {
            mView.updateInputText(tempExpr);
        }
    }

    @Override
    public void deleteUpdateText(String s) {
        String text = removeLastCharOptional(s);
        mView.updateInputText(text);
    }

    private Boolean isValidExpression(CharSequence expr) {
        Matcher m = mMathPtrn.matcher(expr);
        return m.matches();
    }

    private Double evalExpression(CharSequence text) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        return (Double)engine.eval(text.toString());
    }

    // TODO: Move to utils
    private String removeLastCharOptional(String s){
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }
}

package com.example.scastellanos.javaday.CalculatorMVP;

import com.example.scastellanos.javaday.ApiService.ApiService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CalculatorPresenterMVP implements CalculatorContractMVP.Presenter {

    CalculatorContractMVP.View mView;
    private final String mathRegex = "^([-]?\\d+(\\.\\d*)?)([-+/*]\\d+(\\.\\d*)?)*[-+/*]?";
    private Pattern mMathPtrn = Pattern.compile(mathRegex);
    private ApiService newtonService = new ApiService();
    private ArrayList<Disposable> disposables = new ArrayList<>();

    public CalculatorPresenterMVP(CalculatorContractMVP.View view){
        mView = view;
    }

    @Override
    public void calculateResult(String expr) {
        Disposable d = newtonService.evalExpression(expr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> mView.updateInputText(res.getResult()),
                        Throwable::printStackTrace);

        disposables.add(d);
    }

    @Override
    public void addTextIfValid(String text, String charToAdd) {
        String tempExpr = text.concat(charToAdd);
        if(isValidExpression(tempExpr)) {
            mView.updateInputText(tempExpr);
        }
    }

    @Override
    public void deleteLastChar(String text) {
        mView.updateInputText(removeLastCharOptional(text));
    }

    @Override
    public void clearDisposables(){
        for (Disposable d: disposables) {
            d.dispose();
        }
    }

    private Boolean isValidExpression(CharSequence expr) {
        Matcher m = mMathPtrn.matcher(expr);
        return m.matches();
    }

    private String removeLastCharOptional(String s){
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }
}

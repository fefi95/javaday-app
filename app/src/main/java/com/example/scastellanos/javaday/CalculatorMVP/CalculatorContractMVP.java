package com.example.scastellanos.javaday.CalculatorMVP;

import android.widget.Button;

import javax.script.ScriptException;

public interface CalculatorContractMVP {
    interface View {
        void updateInputText(CharSequence text);
    }

    interface Presenter {
        void calculateResult(String text);
        void addTextIfValid(String text, String charToAdd);
    }
}

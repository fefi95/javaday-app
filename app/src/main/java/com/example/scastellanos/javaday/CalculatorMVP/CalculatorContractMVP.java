package com.example.scastellanos.javaday.CalculatorMVP;

public interface CalculatorContractMVP {
    interface View {
        void updateInputText(CharSequence text);
    }

    interface Presenter {
        void calculateResult(String text);
        void addTextIfValid(String text, String charToAdd);
    }
}

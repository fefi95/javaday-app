package com.example.scastellanos.javaday.CalculatorMVP;

public interface CalculatorContractMVP {
    interface View {
        void updateInputText(CharSequence text);
    }

    interface Presenter {
        /**
         * Given an expression, calls to the service to calculate the result
         * of the operation then, updates the view
         */
        void calculateResult(String text);
        /**
         * Validates the expression to update the view accordingly
         * @param text: the current text on the view
         * @param charToAdd: the char to add to the text
         */
        void addTextIfValid(String text, String charToAdd);
        /**
         * Deletes the last char of the text, then updates the view
         */
        void deleteLastChar(String s);
        /**
         * Clears all disposable objects
         */
        void clearDisposables();
    }
}

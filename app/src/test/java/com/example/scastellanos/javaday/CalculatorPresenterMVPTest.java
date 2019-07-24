package com.example.scastellanos.javaday;

import com.example.scastellanos.javaday.CalculatorMVP.CalculatorContractMVP;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorPresenterMVP;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CalculatorPresenterMVPTest {

    @Mock
    private CalculatorContractMVP.View activityMVP;

    private CalculatorPresenterMVP presenterMVP;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenterMVP = new CalculatorPresenterMVP(activityMVP);
    }

    // Tests for addTextIfValid function

    @Test
    public void addTextIfValid_valid_simpleExpr() {
        presenterMVP.addTextIfValid("3+", "4");
        verify(activityMVP, times(1)).updateInputText("3+4");
    }

    @Test
    public void addTextIfValid_valid_decimalExpr() {
        presenterMVP.addTextIfValid("3", ".");
        verify(activityMVP, times(1)).updateInputText("3.");
    }

    @Test
    public void addTextIfValid_notValid_exprPlus() {
        presenterMVP.addTextIfValid("3+", "+");
        verify(activityMVP, times(0)).updateInputText("3++");
    }

    @Test
    public void addTextIfValid_notValid_exprMinus() {
        presenterMVP.addTextIfValid("3+", "-");
        verify(activityMVP, times(0)).updateInputText("3+-");
    }

    @Test
    public void addTextIfValid_notValid_exprMul() {
        presenterMVP.addTextIfValid("3+", "*");
        verify(activityMVP, times(0)).updateInputText("3+*");
    }

    @Test
    public void addTextIfValid_notValid_exprDiv() {
        presenterMVP.addTextIfValid("3+", "/");
        verify(activityMVP, times(0)).updateInputText("3+/");
    }

    @Test
    public void addTextIfValid_notValid_exprDot() {
        presenterMVP.addTextIfValid("3+", ".");
        verify(activityMVP, times(0)).updateInputText("3+.");
    }

    @Test
    public void addTextIfValid_notValid_decimalExpr() {
        presenterMVP.addTextIfValid("3.", ".");
        verify(activityMVP, times(0)).updateInputText("3..");
    }

    // Tests for calculateResult function

    @Test
    public void calculateResult_validExpr(){
        presenterMVP.calculateResult("3+4");
        verify(activityMVP, times(1)).updateInputText("7.0");
    }

    @Test
    public void calculateResult_notValidExpr(){
        presenterMVP.calculateResult("3++");
        verify(activityMVP, times(0)).updateInputText("");
    }

    // Tests for deleteUpdateText

    @Test
    public void deleteUpdateText_empty(){
        presenterMVP.deleteUpdateText("");
        verify(activityMVP, times(1)).updateInputText("");
    }

    @Test
    public void deleteUpdateText_oneChar(){
        presenterMVP.deleteUpdateText("3");
        verify(activityMVP, times(1)).updateInputText("");
    }

    @Test
    public void deleteUpdateText_multipleChars(){
        presenterMVP.deleteUpdateText("3+5");
        verify(activityMVP, times(1)).updateInputText("3+");
    }

}
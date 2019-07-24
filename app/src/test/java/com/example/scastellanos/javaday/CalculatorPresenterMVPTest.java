package com.example.scastellanos.javaday;

import com.example.scastellanos.javaday.CalculatorMVP.CalculatorContractMVP;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorPresenterMVP;

import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public void addTextIfValid_validExpr() {
        presenterMVP.addTextIfValid("3+", "4");
        verify(activityMVP, times(1)).updateInputText("3+4");
    }

    @Ignore
    public void addTextIfValid_notValidExpr() {
        presenterMVP.addTextIfValid("3+", "+");
        verify(activityMVP, times(1)).updateInputText("3+");
    }

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
}
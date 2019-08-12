package com.example.scastellanos.javaday;

import com.example.scastellanos.javaday.ApiService.ApiService;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorContractMVP;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorPresenterMVP;
import com.example.scastellanos.javaday.CalculatorMVP.models.NewtonModel;
import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CalculatorPresenterMVPTest {

    @Mock
    private CalculatorContractMVP.View activityMVP;

    private CalculatorPresenterMVP presenterMVP;
    private MockWebServer server;

    @Rule
    public TrampolineSchedulerRule rule = new TrampolineSchedulerRule();

    @Before
    public void startMockServer() throws  Exception {
        server = new MockWebServer();
        server.start();
        ApiService.BASE_URL = server.url("/").toString();

    }

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
    public void calculateResult_validExpr() throws Exception {
        NewtonModel operation = new NewtonModel("simplify", "3+4", "7");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(operation)));

        CalculatorPresenterMVP presenterMVP2 = new CalculatorPresenterMVP(activityMVP);
        presenterMVP2.calculateResult("3+4");

        verify(activityMVP).updateInputText("7");
    }

    @Test
    public void calculateResult_notValidExpr(){
        NewtonModel operation = new NewtonModel("simplify", "3++", "error");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(operation)));

        CalculatorPresenterMVP presenterMVP2 = new CalculatorPresenterMVP(activityMVP);
        presenterMVP2.calculateResult("3++");
        verify(activityMVP, times(0)).updateInputText("");
    }

    // Tests for deleteLastChar

    @Test
    public void deleteLastChar_empty(){
        presenterMVP.deleteLastChar("");
        verify(activityMVP, times(1)).updateInputText("");
    }

    @Test
    public void deleteLastChar_oneChar(){
        presenterMVP.deleteLastChar("3");
        verify(activityMVP, times(1)).updateInputText("");
    }

    @Test
    public void deleteLastChar_multipleChars(){
        presenterMVP.deleteLastChar("3+5");
        verify(activityMVP, times(1)).updateInputText("3+");
    }

}
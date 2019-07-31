package com.example.scastellanos.javaday;

import com.example.scastellanos.javaday.ApiService.ApiService;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorContractMVP;
import com.example.scastellanos.javaday.CalculatorMVP.CalculatorPresenterMVP;
import com.example.scastellanos.javaday.CalculatorMVP.models.NewtonModel;
import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TrampolineSchedulerRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new MyStatement(base);
    }

    public class MyStatement extends Statement {
        private final Statement base;

        @Override
        public void evaluate() throws Throwable {
            try {
                RxJavaPlugins.setComputationSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setSingleSchedulerHandler(__ -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
                base.evaluate();
            } finally {
                RxJavaPlugins.reset();
                RxAndroidPlugins.reset();
            }
        }

        public MyStatement(Statement base) {
            this.base = base;
        }
    }
}

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

//        RxAndroidPlugins.reset();
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
//                __ -> Schedulers.trampoline());
////        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
////                __ -> new TestScheduler());
////        RxJavaPlugins.reset();
//        RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
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

//        Thread.sleep(2000);

        System.out.print("bla");
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
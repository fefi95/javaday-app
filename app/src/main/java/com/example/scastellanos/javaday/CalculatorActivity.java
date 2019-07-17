package com.example.scastellanos.javaday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.button_0) Button btn0;
    @BindView(R.id.button_1) Button btn1;
    @BindView(R.id.button_2) Button btn2;
    @BindView(R.id.button_3) Button btn3;
    @BindView(R.id.button_4) Button btn4;
    @BindView(R.id.button_5) Button btn5;
    @BindView(R.id.button_6) Button btn6;
    @BindView(R.id.button_7) Button btn7;
    @BindView(R.id.button_8) Button btn8;
    @BindView(R.id.button_9) Button btn9;
    @BindView(R.id.button_dot) Button btnDot;
    @BindView(R.id.button_del) Button btnDel;
    @BindView(R.id.button_res) Button btnRes;
    @BindView(R.id.results) EditText eResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6,
            R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_dot, R.id.button_0, R.id.button_plus,
            R.id.button_minus, R.id.button_mul, R.id.button_div })
    public void showNumber(Button v){
        CharSequence expr = eResults.getText().toString().concat(v.getText().toString());
        if(isValidExpression(expr)){
            addTextToInput(v.getText());
        }
    }

    @OnClick(R.id.button_res)
    public void showResult(View v){
        try {
            eResults.setText(evalText(eResults.getText()).toString());
        }
        catch (Exception e) { } // handle error

    }

    private Double evalText(CharSequence text) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        return (Double)engine.eval(text.toString());
    }

    private void addTextToInput(CharSequence text) {
        CharSequence res = eResults.getText().append(text);
        eResults.setText(res);
    }

    private Boolean isValidExpression(CharSequence expr){
        return true;
    }
}

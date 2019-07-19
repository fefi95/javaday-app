package com.example.scastellanos.javaday.CalculatorMVP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scastellanos.javaday.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorActivityMVP extends AppCompatActivity implements CalculatorContractMVP.View {

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
    CalculatorContractMVP.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
        mPresenter = new CalculatorPresenterMVP(this);
    }

    @OnClick({ R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6,
            R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_dot, R.id.button_0, R.id.button_plus,
            R.id.button_minus, R.id.button_mul, R.id.button_div })
    public void onClickSymbol(Button v){
        mPresenter.addTextIfValid(eResults.getText().toString(), v.getText().toString());
    }

    @OnClick(R.id.button_res)
    public void onClickEqual(View v){
        mPresenter.calculateResult(eResults.getText().toString());
    }

    @Override
    public void updateInputText(CharSequence text){
        eResults.setText(text);
    }
}


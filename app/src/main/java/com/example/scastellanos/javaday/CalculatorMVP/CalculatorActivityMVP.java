package com.example.scastellanos.javaday.CalculatorMVP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scastellanos.javaday.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorActivityMVP extends AppCompatActivity implements CalculatorContractMVP.View {

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

    @OnClick(R.id.button_del)
    public void onClickDelete(View v){
        mPresenter.deleteLastChar(eResults.getText().toString());
    }

    @Override
    public void updateInputText(CharSequence text){
        eResults.setText(text);
    }

    public void onDestroy(){
        super.onDestroy();
        mPresenter.clearDisposables();
    }
}


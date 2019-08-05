package com.example.scastellanos.javaday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scastellanos.javaday.ApiService.ApiService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// How the activity looks like without MVP pattern
public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.results) EditText eResults;
    private ArrayList<Disposable> disposables = new ArrayList<>();
    private ApiService newtonService = new ApiService();
    private final String mathRegex = "^([-]?\\d+(\\.\\d*)?)([-+/*]\\d+(\\.\\d*)?)*[-+/*]?";
    private Pattern mMathPtrn = Pattern.compile(mathRegex);

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
    public void onClickSymbol(Button v){
        CharSequence expr = eResults.getText().toString().concat(v.getText().toString());
        if(isValidExpression(expr)){
            addTextToInput(v.getText());
        }
    }

    @OnClick(R.id.button_res)
    public void OnClickEqual(View v){
        evalText(eResults.getText().toString());
    }

    @OnClick(R.id.button_del)
    public void onClickDelete(View v){
        eResults.setText(removeLastCharOptional(eResults.getText().toString()));
    }

    private void evalText(String expr) {
        Disposable d = newtonService.evalExpression(expr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res -> eResults.setText(res.getResult()),
                        Throwable::printStackTrace);

        disposables.add(d);
    }

    private void addTextToInput(CharSequence text) {
        CharSequence res = eResults.getText().append(text);
        eResults.setText(res);
    }

    private Boolean isValidExpression(CharSequence expr){
        Matcher m = mMathPtrn.matcher(expr);
        return m.matches();
    }

    private String removeLastCharOptional(String s){
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }
}

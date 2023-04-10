package com.khuongviettai.caculatorsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private TextView tvMath, tvResult;
    private Button btnC, btnOpen, btnClose, btnDiv, btn7, btn8, btn9, btnMul, btn4, btn5, btn6, btnSub, btn1, btn2, btn3, btnPlus, btn0, btnDot, btnResult, btnBack;

    private String currentMath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvMath = findViewById(R.id.tvMath);
        tvResult = findViewById(R.id.tvResult);
        assignID(btnC, R.id.btnC);
        assignID(btnOpen, R.id.btnOpen);
        assignID(btnClose, R.id.btnClose);
        assignID(btnDiv, R.id.btnDiv);
        assignID(btn0, R.id.btn0);
        assignID(btn1, R.id.btn1);
        assignID(btn2, R.id.btn2);
        assignID(btn3, R.id.btn3);
        assignID(btn4, R.id.btn4);
        assignID(btn5, R.id.btn5);
        assignID(btn6, R.id.btn6);
        assignID(btn7, R.id.btn7);
        assignID(btn8, R.id.btn8);
        assignID(btn9, R.id.btn9);
        assignID(btnMul, R.id.btnMul);
        assignID(btnSub, R.id.btnSub);
        assignID(btnPlus, R.id.btnPlus);
        assignID(btnDot, R.id.btnDot);
        assignID(btnResult, R.id.btnResult);
        assignID(btnBack, R.id.btnBack);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedResult = sharedPreferences.getString("result", "0");
        tvResult.setText(savedResult);
    }

    void assignID(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        if (buttonText.equals("=")) {
            if (currentMath.equals("")) {
                tvResult.setText("0");
                return;
            }
            String finalResult = getResult(currentMath);
            if (!finalResult.equals("Err")) {
                if (currentMath.equals("16052001")) {
                    finalResult = "I love Tai";
                } else {
                    // Lưu lịch sử
                    String history = currentMath + " = " + finalResult;
                    Set<String> historySet = sharedPreferences.getStringSet("history", new HashSet<String>());
                    Set<String> newSet = new HashSet<>(historySet);
                    newSet.add(history);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("history", newSet);
                    editor.putString("result", finalResult);
                    editor.apply();
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                }
                tvResult.setText(finalResult);
            } else {
                tvResult.setText("Err");
            }
            return;
        } else if (buttonText.equals("C")) {
            tvMath.setText("");
            tvResult.setText("0");
            currentMath = "";
            return;

        } else if (buttonText.equals("BACK")) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);

            startActivity(intent);
            return;

        }

        currentMath += buttonText;
        tvMath.setText(currentMath);
    }


    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            if (data.equals("16052001")) {
                finalResult = "I love Tai";
            }
            return finalResult;
        } catch (Exception e
        ) {
            return "Err";
        }
    }

}
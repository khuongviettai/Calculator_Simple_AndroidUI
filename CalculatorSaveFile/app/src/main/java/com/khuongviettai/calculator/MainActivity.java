package com.khuongviettai.calculator;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvMath, tvResult;
    private Button btnC, btnOpen, btnClose, btnDiv, btn7, btn8, btn9, btnMul, btn4, btn5, btn6, btnSub, btn1, btn2, btn3, btnPlus, btn0, btnDot, btnResult, btnBack, btnCA;

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


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fileName = "calculator.txt";
                String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
                String historyData = "";
                try {
                    File file = new File(filePath);
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        historyData += line + "\n";
                    }
                    reader.close();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("historyData", historyData);
                bundle.putString("fileName", fileName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    void assignID(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    private String currentMath = "";

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
                tvResult.setText(finalResult);


                // Save ket qua
                String fileName = "calculator.txt";
                String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
                try {
                    File file = new File(filePath);
                    FileWriter writer = new FileWriter(file, true);
                    if (currentMath.equals("16052001")) {
                       return;
                    }
                    {
                        writer.append(currentMath + " = " + finalResult + "\n");
                    }
                    writer.flush();
                    writer.close();
                    Toast.makeText(this, "Saved ", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error saved", Toast.LENGTH_SHORT).show();
                }

            } else {
                tvResult.setText("Err");
            }
            return;
        }

        if (buttonText.equals("C")) {
            tvMath.setText("");
            tvResult.setText("0");
            currentMath = "";
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
        } catch (Exception e) {
            return "Err";
        }
    }

}
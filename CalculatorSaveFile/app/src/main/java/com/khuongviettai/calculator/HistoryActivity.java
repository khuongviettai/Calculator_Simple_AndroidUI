package com.khuongviettai.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.khuongviettai.calculator.databinding.ActivityHistoryBinding;


public class HistoryActivity extends AppCompatActivity {
    private TextView tvHistoryData;
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar();
            Bundle bundle = getIntent().getExtras();
            String historyData = bundle.getString("historyData");
            String fileName = bundle.getString("fileName");

         tvHistoryData = findViewById(R.id.tvHistoryData);
            tvHistoryData.setText(historyData);

            setTitle("History - " + fileName);


        }

    private void Toolbar() {

        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
package com.example.administrator.customerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.customerview.R;
import com.example.administrator.customerview.view.CustomTitleBar;

public class CustomTitleBarActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_title_bar);
        final CustomTitleBar customTitleBar1 = (CustomTitleBar) findViewById(R.id.custom_title1);
        final CustomTitleBar customTitleBar2 = (CustomTitleBar) findViewById(R.id.custom_title2);
        final CustomTitleBar customTitleBar3 = (CustomTitleBar) findViewById(R.id.custom_title3);
        customTitleBar1.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_left:
                        Toast.makeText(CustomTitleBarActivity.this,"左侧按钮的值是"+customTitleBar1.getBtnLeft().getText().toString(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_right:
                        Toast.makeText(CustomTitleBarActivity.this,"右侧按钮的值是"+customTitleBar1.getBtnRight().getText().toString(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        customTitleBar2.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_left:
                        Toast.makeText(CustomTitleBarActivity.this,"左侧按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_right:
                        Toast.makeText(CustomTitleBarActivity.this,"右侧按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        customTitleBar3.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_left:
                        Toast.makeText(CustomTitleBarActivity.this,"左侧按钮",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_right:
                        Toast.makeText(CustomTitleBarActivity.this,"右侧按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}

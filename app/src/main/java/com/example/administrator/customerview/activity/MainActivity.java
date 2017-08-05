package com.example.administrator.customerview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.customerview.R;
import com.example.administrator.customerview.view.CustomTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.i("MainActivity","结果是："+Long.toHexString(0x100000000L + 0xabcdabcd));
    }

    /**
     * 该方法必须是public的，否则报错，因为找不到
     * @param view
     */
    public void onClickBtn(View view){

        switch (view.getId()){
            case R.id.btn_custom_tv:
                startActivity(new Intent(this, CustomTextViewActivity.class));
                break;
            case R.id.btn_custom_circle:
                startActivity(new Intent(this,PercentCircleViewActivity.class));
                break;
            case R.id.btn_custom_view_group:
                startActivity(new Intent(this,CustomTitleBarActivity.class));
                break;
            case R.id.btn_custom_tag:
                startActivity(new Intent(this,TagViewActivity.class));
                break;
        }
    }
}

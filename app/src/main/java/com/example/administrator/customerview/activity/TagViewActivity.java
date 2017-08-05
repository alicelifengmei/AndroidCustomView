package com.example.administrator.customerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.customerview.R;
import com.example.administrator.customerview.view.TagView;

public class TagViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_view);
        TagView tagView = (TagView) findViewById(R.id.tag_view);
        final String[] tagStrs = new String[]{"标签","业务能力强","有责任心","上进","超长常超超长超长超长超长标签","厉害了吧","不知道一行能有几个","那就试试吧","可以",
                "还行","不错吧","恩，是不错"};
        for(int i = 0; i< tagStrs.length; i++){
            TextView tvCategoryName = new TextView(this);//这个view也可以在xml文件中定义
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int)getResources().getDimension(R.dimen.px_60));
            lp.setMargins((int)getResources().getDimension(R.dimen.px_10), (int)getResources().getDimension(R.dimen.px_10), (int)getResources().getDimension(R.dimen.px_10), (int)getResources().getDimension(R.dimen.px_10));
            tvCategoryName.setLayoutParams(lp);
            tvCategoryName.setBackgroundResource(R.drawable.shape_circular_bead);
            tvCategoryName.setPadding((int)getResources().getDimension(R.dimen.px_14),0,(int)getResources().getDimension(R.dimen.px_14),0);
            tvCategoryName.setGravity(Gravity.CENTER_VERTICAL);
            tvCategoryName.setTextSize(16);//单位是sp，看源码
            tvCategoryName.setTextColor(getResources().getColor(R.color.color_666666));
            tvCategoryName.setText(tagStrs[i]);
            final int finalI = i;
            tvCategoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TagViewActivity.this,"标签的值是："+tagStrs[finalI],Toast.LENGTH_SHORT).show();
                }
            });
            tagView.addView(tvCategoryName);
        }
    }
}

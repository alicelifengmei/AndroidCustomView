package com.example.administrator.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.customerview.R;

/**
 * Created by lifengmei on 2017/7/27.
 * 自定义组合控件，titlebar，左侧（可图片可文字可点击），右侧（可图片可文字可点击），中间title（图片或文字不可点击）
 */

public class CustomTitleBar extends RelativeLayout {

    private Button btnLeft;
    private Button btnRight;
    private TextView tvTitle;

    public CustomTitleBar(Context context) {
        this(context, null);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_custom_title_bar,this,true);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        //左侧和右侧，有文字就显示文字，没有文字有图片就显示图片，否则都不展示；中间有文字就显示文字，否则有图片就显示图片，没有展示空
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTitleBar);
        if(typedArray != null){
            //左侧按钮
            String leftText = typedArray.getString(R.styleable.CustomTitleBar_leftText);
            int leftResource = typedArray.getResourceId(R.styleable.CustomTitleBar_leftImg,-1);
            int leftTextColor = typedArray.getColor(R.styleable.CustomTitleBar_leftTextColor, Color.BLACK);
            if(!TextUtils.isEmpty(leftText) && leftResource != -1){//图片和文字都有,这种实现方式不太好，最好能有一个图片控件，一个文字空间实现出来才好看
                btnLeft.setText(leftText);
                btnLeft.setTextColor(leftTextColor);
                btnLeft.setTextSize(10);
                Drawable drawable = getResources().getDrawable(leftResource);
                drawable.setBounds(0, 0, 50, 50);//注意必须有这一步方可实现
                btnLeft.setCompoundDrawables(null,drawable,null,null);
                btnLeft.setCompoundDrawablePadding(2);//设置图片和text之间的间距,为何这一行并没有什么卵用呢
            }else if(!TextUtils.isEmpty(leftText)){
                btnLeft.setText(leftText);
                btnLeft.setTextColor(leftTextColor);
            }else if(leftResource != -1){//作图需要注意规范，大小为90px*90px
                btnLeft.setBackgroundResource(leftResource);
            }
            //右侧按钮
            String rightText = typedArray.getString(R.styleable.CustomTitleBar_rightText);
            int rightImg = typedArray.getResourceId(R.styleable.CustomTitleBar_rightImg,-1);
            int rightTextColor = typedArray.getColor(R.styleable.CustomTitleBar_rightTextColor,Color.BLACK);
            if(!TextUtils.isEmpty(rightText) && rightImg != -1){
                btnRight.setText(rightText);
                btnRight.setTextColor(rightTextColor);
                btnRight.setTextSize(10);
                Drawable drawable = getResources().getDrawable(rightImg);
                drawable.setBounds(0, 0, 50, 50);//注意必须有这一步方可实现
                btnRight.setCompoundDrawables(null,drawable,null,null);
                btnRight.setCompoundDrawablePadding(2);
            }else if(!TextUtils.isEmpty(rightText)){
                btnRight.setText(rightText);
                btnRight.setTextColor(rightTextColor);
            }else if(rightImg != -1){
                btnRight.setBackgroundResource(rightImg);
            }
            //中间title
            String titleText = typedArray.getString(R.styleable.CustomTitleBar_titleText);
            if(!TextUtils.isEmpty(titleText)){
                int titleTextColor = typedArray.getColor(R.styleable.CustomTitleBar_titleTextColor,Color.BLACK);
                tvTitle.setText(titleText);
                tvTitle.setTextColor(titleTextColor);
            }else{
                int titleImg = typedArray.getResourceId(R.styleable.CustomTitleBar_titleImg,-1);
                if(titleImg != -1){
                    tvTitle.setBackgroundResource(titleImg);
                }
            }
            typedArray.recycle();
        }
    }

    /***
     * 设置左右按钮的点击事件
     * @param onclickListener
     */
    public void setOnclick(OnClickListener onclickListener){
        if(onclickListener != null){
            btnLeft.setOnClickListener(onclickListener);
            btnRight.setOnClickListener(onclickListener);
        }
    }


    public Button getBtnLeft() {
        return btnLeft;
    }

    public Button getBtnRight() {
        return btnRight;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

}

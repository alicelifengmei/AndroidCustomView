package com.example.administrator.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.customerview.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by lifengmei on 2017/7/20.
 * 自定义的TextView
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    private String TAG = "CustomTextView";

    private String textContent;
    private int textColor;
    private int textSize;

    private Rect mRect;
    private Paint mPaint;

    public CustomTextView(Context context) {
        this(context, null);//TODO 注意此处不是super了！
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);//TODO 注意此处不是super了！
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView,defStyleAttr,0);
        if(typedArray != null){

            int n = typedArray.getIndexCount();
            for(int i = 0;i < n; i++){//遍历所有在xml文件中设置的属性，并取值，便于之后拿在xml中设置的值绘制
                int attr = typedArray.getIndex(i);
                switch (attr){
                    case R.styleable.CustomTextView_textContent:
                        textContent = typedArray.getString(attr);
                        break;
                    case R.styleable.CustomTextView_textColor:
                        textColor = typedArray.getColor(attr, Color.BLACK);
                        break;
                    case R.styleable.CustomTextView_textSize:
                        textSize = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                        break;
                }
            }
            typedArray.recycle();//回收以防影响下一次
        }

        //初始化画笔并设置大小
        mPaint = new Paint();
        mRect = new Rect();
        //获取画笔最小的矩形
//        mPaint.setTextSize(textSize);
//        mPaint.getTextBounds(textContent,0,textContent.length(), mRect);
        this.setOnClickListener(new OnClickListener() {//添加自定义点击事件，点击切换随机数据
            @Override
            public void onClick(View v) {
                textContent = randomText();
                postInvalidate();//该方法只会从ondraw开始重复之心，不会再执行onMeasure了
            }
        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        //以下代码是为了解决wrapcontent的时候不准确bug
        if(widthMode == MeasureSpec.EXACTLY){//1,EXACTLY:给了固定的准确值或者match_parent
           //2,AT_MOST:表示子布局限制在一个最大值内，一般为wrap_content
            // 3,UNSPECIFIED:表示子布局想要多大就多大，很少使用
            width = widthSize;
        }else{
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(textContent,0,textContent.length(),mRect);
            float textWidth = mRect.width();
            int desired = (int)(getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
//            Log.i(TAG,"textWidth="+textWidth+",getPaddingLeft="+getPaddingLeft()+",getPaddingRight="+getPaddingRight()+",width="+desired);
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(textContent,0,textContent.length(),mRect);
            float textHeight = mRect.height();
            int desired = (int)(getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
//            Log.i(TAG,"textHeight="+textHeight+",getPaddingTop="+getPaddingTop()+",getPaddingBottom="+getPaddingBottom()+",height="+desired);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
//        Log.i(TAG,"getMeasuredWidth="+getMeasuredWidth()+",getMeasuredHeight="+getMeasuredHeight());

        //画字
        mPaint.setColor(textColor);
        mPaint.getTextBounds(textContent,0,textContent.length(),mRect);
        mPaint.setAntiAlias(true);//Paint的抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//Canvas的抗锯齿
        canvas.drawText(textContent,getWidth()/2 - mRect.width()/2, getHeight()/2 + mRect.height()/2, mPaint);
//        Log.i(TAG,"getWidth()/2="+getWidth()/2+",mRect.width()/2="+mRect.width()/2+",getHeight()/2="+getHeight()/2+",mRect.height()/2="+mRect.height()/2+"tsxtSize="+textSize);
    }

    /**
     * 生成一个随机数
     * @return
     */
    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while(set.size() < 4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for(Integer i : set){
            sb.append(""+i);
        }
        return sb.toString();
    }
}

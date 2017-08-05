package com.example.administrator.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.customerview.R;

/**
 * Created by lifengmei on 2017/7/25.
 * 自定义百分比的圆，可以在xml文件中设置百分比是多少，圆的颜色和比例的颜色，还有圆在控件中的摆放位置（如左上右下中）
 */

public class PercentCircleView extends View {
    private Paint mPaint;
    private RectF mRectF;//跟Rect类似，有什么区别，这个需要研究一下

    private static final int CENTER=0;
    private static final int LEFT=1;
    private static final int TOP=2;
    private static final int RIGHT=3;
    private static final int BOTTOM=4;
    private int gravity = CENTER;
    private float radius;
    private int progress;
    private int progressColor;
    private int bgColor;

    private float centerX = 0;
    private float centerY = 0;

    public PercentCircleView(Context context) {
        this(context,null);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentCircleView,defStyleAttr,0);
        if(typedArray != null){
            gravity = typedArray.getInt(R.styleable.PercentCircleView_gravity,CENTER);
            radius = typedArray.getDimension(R.styleable.PercentCircleView_radius,0);
            progress = typedArray.getInt(R.styleable.PercentCircleView_progress,0);
            progressColor = typedArray.getColor(R.styleable.PercentCircleView_progressColor,Color.BLUE);
            bgColor = typedArray.getColor(R.styleable.PercentCircleView_backgroundColor,Color.YELLOW);
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//paint 抗锯齿
//        在画图的时候，图片如果旋转或缩放之后，总是会出现那些华丽的锯齿。其实Android自带了解决方式。
//        方法一：给Paint加上抗锯齿标志。然后将Paint对象作为参数传给canvas的绘制方法。
//        paint.setAntiAlias(true);
//        方法二：给Canvas加上抗锯齿标志。
//        有些地方不能用paint的，就直接给canvas加抗锯齿，更方便。
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        //以下为解决wrap_content不好用的问题
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width ;
        int height;
        if(widthMode == MeasureSpec.EXACTLY){//有具体的值就用设置的具体的大小值
            width = widthSize;
        }else{//没有就将宽度设置为控件要占多少宽度，这里即为直径加上两边的padding值
            width = (int)(getPaddingLeft() + radius * 2 + getPaddingRight());
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height = (int)(getPaddingTop() + radius * 2 + getPaddingBottom());
        }

        //这一步将真实需要的计算好的宽和高塞进去保存下来，便于之后的layout和draw工作
        setMeasuredDimension(width,height);
        centerX = width/2;
        centerY = height/2;
        switch (gravity){
            case LEFT:
                centerX = radius + getPaddingLeft();
                break;
            case TOP:
                centerY = radius + getPaddingTop();
                break;
            case RIGHT:
                centerX = width - radius - getPaddingRight();
                break;
            case BOTTOM:
                centerY = height - radius - getPaddingBottom();
                break;
        }
        float left = centerX - radius;
        float top = centerY - radius;
        float right = centerX + radius;
        float bottom = centerY + radius;
        mRectF.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        mPaint.setColor(bgColor);
//        float radius = width/4;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//画布抗锯齿
        canvas.drawCircle(centerX,centerY,radius,mPaint);

        //画百分比的圆三角
        mPaint.setColor(progressColor);

        // ---这里其实就是圆形所在的正方形里，画圆弧的话会以这个界定好的矩形的中心为中心，也就是这个圆形的中心
//        mRectF.set(width/2 - radius, height/2 -radius, width/2+radius, height/2 + radius);//用于定义圆弧的形状和大小的界限？
        double percent = progress*1.0/100;
        float angle = (int)(percent * 360);
        Log.i("PercentCircleView","progressColor是"+progressColor+"angle是"+angle);
        canvas.drawArc(mRectF,270,angle,true,mPaint);//drawArc方法是画圆弧，开始位置如果是0度表示从右侧水平位置开始了，270度表示从竖直那个位置开始。true表示包括圆心，就是那两条从圆心的辐射线，false就表示单单一段弧线,
        //参数1：:指定圆弧的外轮廓矩形区域。圆弧的大小和范围，实验后认为该圆弧会以这个范围的中心为圆心
        //参数2：圆弧起始角度，单位为度。圆弧开始的角度地方
        //参数3：圆弧扫过的角度，顺时针方向，单位为度。圆弧持续的大小角度
        //参数4：是否经过圆心，经过圆心就是扇形（当然paint设置成stroke时是空心扇形），不经过圆心时，比如paint设置为描边STROKE时是一个弧线，paint设置为FILL填充时是一条弧加上两点连直线的填充，相当于扇形去掉三角形
        //参数5：画笔，画笔的属性可以自己设置，设置style啊color啊抗锯齿啊等
        //canvas.drawText(text, x, y, mPaint);画文字
        //canvas.drawRect(left,top,right,bottom,mPaint);画矩形
        //canvas.drawArc(mRectF,startAngle,angle,true,mPaint); 画圆
        //canvas.drawOval(mRectF, paint);  画内切圆？或椭圆
        //canvas.drawPath(mPath, paint);   绘制路径，参见实现的图片裁剪框
        //绘制相关的博客文档：http://www.cnblogs.com/hwgt/p/5416866.html
    }
}

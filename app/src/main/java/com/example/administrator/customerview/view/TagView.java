package com.example.administrator.customerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifengmei on 2017/7/28.
 * android自定义的标签view
 * 实现思路：
 * 1，在onMeasure中测量包含了所有子view的该控件的大小。
 * 2，在onLayout中计算出每个子view标签的左上右下坐标，摆放各个子view标签
 *
 * 此处没有考虑父view的padding值
 */

public class TagView extends ViewGroup {
    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();//获取子view个数，如果这一行子view的宽度和大于控件设置的宽度，则换行，高度加上；
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//设置的宽
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content下的宽和高
        int width = 0;
        int height = 0;

        int lineWidth = 0;//新一行子view的的宽的和
        int lineHeight = 0;//新一行的高，取所有子view中最高的

        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);//此处measure了子view
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            //子view实际的宽度
            int childWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            //子view实际的高度
            int childHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            if(lineWidth + childWidth < widthSize){//子view未满一行
                lineWidth += childWidth;
                lineHeight = lineHeight>childHeight ? lineWidth:childHeight;
            }else{//子view满了一行
                width = Math.max(lineWidth, width);//计算的控件的宽和子view的宽谁更大取谁作为控件的宽
                lineWidth = childWidth;
                height += lineHeight;//所有行高叠加在一起
                lineHeight = childHeight;//重新开始新行，行高为第一个子view的高
            }
            //如果是最后一个view，将高度叠加，宽度取最大
            if(childCount-1==i){
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }

        setMeasuredDimension((widthMode==MeasureSpec.EXACTLY) ? widthSize : width, (heightMode==MeasureSpec.EXACTLY) ? heightSize : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        List<List<View>> allLineViews = new ArrayList<>();
        List<Integer> lineHeights = new ArrayList<>();
        lineHeights.add(0);
        //计算子view的左上右下，然后layout摆放一下
        //计算每一行都有哪些view，记住每一行的高，便于计算每个view的左上右下
        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        for(int i = 0; i<childCount; i++){
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;//为什么只能用getMeasuredWith而不能用getWidth。getMeasuredWidth（）的值是measure阶段结束之后得到的view的原始的值
            // getMeasuredWidth()获取的是view原始的大小，也就是这个view在XML文件中配置或者是代码中设置的大小。getWidth（）获取的是这个view最终显示的大小，这个大小有可能等于原始的大小也有可能不等于原始大小。
            //此时子view们还没有绘制出来，只能得到其测量宽高度getMeasuredWidth
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            if(lineWidth + childWidth < getWidth()){//未满一行，加到一行view中，宽度叠加，高度取最高的。题外话：此时parent的宽度已经确定了，要用实际的宽度getwidth
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
                lineViews.add(childView);
            }else{//满一行，记下高度，view的list记下来并从头开始
                lineHeights.add(lineHeight);
                allLineViews.add(lineViews);
                lineViews = new ArrayList<>();//此处一定要重新指向新的对象，否则lineviews中只有最后的一个view，这里一定要初始化
                lineViews.add(childView);
                lineWidth = childWidth;
                lineHeight = childHeight;
            }
        }
        //最后一行的也要记下来 //这样会有问题吗?当最后一行恰好满了一行呢，此时将会多绘制一行重复的标签---不会出现这种问题，判断中最后总有一个childview没有计算进去，值赋给了lineWidth和lineHeight
        lineHeights.add(lineHeight);
        allLineViews.add(lineViews);
        //接下来计算每一个view的左上右下，然后layout
        //遍历每一行
        int allLineTop = 0;
        for(int j = 0 ; j< allLineViews.size(); j++){
            List<View> childViews = allLineViews.get(j);
            int linetop = lineHeights.get(j);
            allLineTop += linetop;
            int lineLeft = 0;
            for(int x = 0 ; x < childViews.size(); x++){
                View child = childViews.get(x);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                int left = lineLeft + mlp.leftMargin;
                int top = allLineTop + mlp.topMargin;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
//                Log.i("TagView","left:"+left+",top:"+top+",right:"+right+",bottom:"+bottom);
                child.layout(left,top,right,bottom);

                lineLeft = right + mlp.rightMargin;
            }

        }

    }
}

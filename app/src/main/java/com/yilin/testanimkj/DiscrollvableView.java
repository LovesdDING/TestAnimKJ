package com.yilin.testanimkj;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/9/28.
 */

public class DiscrollvableView extends FrameLayout implements DiscrollvableInterface{

      //自定义枚举类型 移动的方向
    private static final int TRANSLATION_FROM_TOP = 0x01 ;  //0000 0001
    private static final int TRANSLATION_FROM_BOTTOM = 0x02 ;  // 0000 0010
    private static final int TRANSLATION_FROM_LEFT = 0x04 ; // 0000 0100
    private static final int TRANSLATION_FROM_RIGHT = 0x08 ;// 0000 1000
    
    // 自定义的属性 一些接收的变量
    private int mDiscrollveFromBgColor ;  //背景颜色变化开始值
    private int mDiscrollveToBgColor ;   //背景颜色变化结束值
    private boolean mDiscrollveAlpha ;//是否需要透明度动画
    private int mDiscrollveTranslation  ; //平移值
    private boolean mDiscrollveScaleX ; //是否需要x轴方向缩放
    private boolean mDiscrollveScaleY ; //是否需要y轴方向缩放
    private int mWidth ; //本View的宽度
    private int mHeight ;  //本View的高度
    //颜色估值器  在颜色变化的过程中 需要不断的去估值颜色
    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator() ;

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public void setmDisCrollveTranslation(int mDisCrollveTranslation) {
        this.mDiscrollveTranslation = mDisCrollveTranslation;
    }

    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }

    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }

    public DiscrollvableView(@NonNull Context context) {
        super(context);
    }

    public DiscrollvableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w ;
        mHeight = h ;
        onResetDiscrollve() ;
    }

    @Override
    public void onDiscrollve(float ratio) {
        // 控件控制自身的动画属性
        if(mDiscrollveAlpha){
            setAlpha(ratio);
        }
        if(mDiscrollveScaleX){
            setScaleX(ratio);
        }
        if(mDiscrollveScaleY){
            setScaleY(ratio);
        }

        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight*(1-ratio));
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)){
            setTranslationY(-mHeight*(1-ratio));
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth*(1-ratio));  //-width-->0(代表原来的位置)
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth*(1-ratio));
        }


        // 颜色渐变动画
        if(mDiscrollveFromBgColor!=-1 && mDiscrollveToBgColor !=-1){
            setBackgroundColor((Integer) sArgbEvaluator.evaluate(ratio,mDiscrollveFromBgColor,mDiscrollveToBgColor));

        }


    }

    /**
     * 控制自身的动画属性  恢复
     */
    @Override
    public void onResetDiscrollve() {
        if(mDiscrollveAlpha){
            setAlpha(0);
        }
        if(mDiscrollveScaleX){
            setScaleX(0);
        }

        if(mDiscrollveScaleY){
            setScaleY(0);
        }

        //mDiscrollveTranslation  有很多种枚举的情况
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight); //mHeight-->0(代表原来的位置)
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)){
            setTranslationY(-mHeight); //-mHeight-->0(代表原来的位置)
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth);  //-width-->0(代表原来的位置)
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth); //width-->0(代表原来的位置)
        }
    }





    private boolean isDiscrollveTranslationFrom(int translationMask){
        if(mDiscrollveTranslation == -1){
            return false ;
        }
        //fromleft|fromBottom & fromBottom = fromBottom
        return (mDiscrollveTranslation & translationMask) == translationMask ;
    }
}

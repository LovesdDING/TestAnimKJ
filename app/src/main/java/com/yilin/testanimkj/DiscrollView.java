package com.yilin.testanimkj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 自定义scrollView
 * Created by Administrator on 2017/9/25.
 */

public class DiscrollView extends ScrollView {

    private DiscrollViewContent mContent ;


    public DiscrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View content = getChildAt(0) ;
        mContent = (DiscrollViewContent) content;
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View first = mContent.getChildAt(0) ;
        first.getLayoutParams().height = getHeight() ;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        
        int scrollViewHeight = getHeight() ;
        //监听滑动--接口-- 控制DIscrollViewContent的属性
        for (int i = 0; i < mContent.getChildCount(); i++) { //遍历MyLinearlayout 里面所有的子控件（myViewGroup）
            View child = mContent.getChildAt(i) ;
            if(!(child instanceof  DiscrollvableInterface)){
                continue;
            }

            DiscrollvableInterface discrollvableInterface = (DiscrollvableInterface) child;
            //1.child 离scrollView顶部的高度
            int discrollViewTop = child.getTop() ;
            int discrollvableHeight = child.getHeight() ;

            //2. 得到scrollView滑出去的高度
            //3. 得到child离屏幕顶部的高度
            int discrollvableAbsoluteTop = discrollViewTop - t ;
            //什么时候执行动画  当child滑进屏幕的时候
            if(discrollvableAbsoluteTop<= scrollViewHeight){
                int visibleGap = scrollViewHeight - discrollvableAbsoluteTop ;
                //确保ratio 是在0-1 超过了1 也设置为1
                discrollvableInterface.onDiscrollve(clamp(visibleGap/(float)discrollvableHeight,1f,0f));
            }else{ //否则恢复到原来的位置
                discrollvableInterface.onResetDiscrollve();
            }


        }
        
    }


    public static float clamp(float value,float max,float min){
        return Math.max(Math.min(value,max),min) ;
    }


}

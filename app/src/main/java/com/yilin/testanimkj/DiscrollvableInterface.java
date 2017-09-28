package com.yilin.testanimkj;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface DiscrollvableInterface {
    /**
     * 当滑动的时候 调用该方法 用来控制里面的控件执行相应的动画
     *
     */
    public void onDiscrollve(float ratio) ;

    /**
     * 重置View的属性
     */
    public void onResetDiscrollve() ;
}

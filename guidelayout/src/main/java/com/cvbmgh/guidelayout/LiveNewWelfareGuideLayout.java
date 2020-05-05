package com.cvbmgh.guidelayout;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author : lhl
 * date : 2020/5/5/005
 * desc : 直播间新人福利任务指引
 */
public class LiveNewWelfareGuideLayout extends FrameLayout {

    private Paint mPaint;

    public LiveNewWelfareGuideLayout(@NonNull Context context) {
        this(context, null);
    }

    private LiveNewWelfareGuideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private LiveNewWelfareGuideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}

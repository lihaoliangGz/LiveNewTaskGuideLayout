package com.cvbmgh.guidelayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * author : lhl
 * date : 2020/5/5/005
 * desc : 直播间新人福利任务指引
 */
public class LiveNewWelfareGuideLayout extends FrameLayout {
    public static final int DEFAULT_BACKGROUND_COLOR = 0xb2000000;
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

    private Paint mPaint;
    private int round; //高亮区域的圆角大小
    private RectF rectF1; //高亮区域1
    private RectF rectF2; //高亮区域2
    private View guideView1; //指引1
    private View guideView2; //指引2
    private int offset; //图片的手指头的偏移量
    private int padding; //高亮相对view的padding
    private int rectF1LeftMargin; //高亮区域1与父布局左边的距离
    private int rectF2LeftMargin; //高亮区域2与父布局左边的距离
    private float downX, downY;
    private int touchSlop;
    private Point point1;  //任务1的手指头的坐标
    private Point point2;  //任务2的手指头的坐标

    private int currentGuide = 1; //当前显示的指引

    private onGuideLayoutFinishListener onGuideLayoutFinishListener;

    public void setOnGuideLayoutFinishListener(LiveNewWelfareGuideLayout.onGuideLayoutFinishListener onGuideLayoutFinishListener) {
        this.onGuideLayoutFinishListener = onGuideLayoutFinishListener;
    }

    public LiveNewWelfareGuideLayout(@NonNull Context context, View parent, View view1, View view2,
                                     View viewFollow, int view1LeftMargin, int view22LeftMargin) {
        super(context);
        init(parent, view1, view2, viewFollow, view1LeftMargin, view22LeftMargin);
    }

    private LiveNewWelfareGuideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    private LiveNewWelfareGuideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     */
    private void init(View parent, View view1, View view2, View viewFollow, int view1LeftMargin, int view2LeftMargin) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(xfermode);

        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        //mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        setBackgroundColor(DEFAULT_BACKGROUND_COLOR);

        //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
        setWillNotDraw(false);

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        round = dp2px(4);
        offset = dp2px(4);
        padding = dp2px(3);
        rectF1LeftMargin = view1LeftMargin - padding;
        rectF2LeftMargin = view2LeftMargin - padding;

        //计算高亮区域1和手指头的坐标
        rectF1 = new RectF();
        Rect view1Rect = getLocationInView(parent, view1);
        rectF1.left = view1Rect.left - padding;
        rectF1.top = view1Rect.top - padding;
        rectF1.right = view1Rect.right + padding;
        rectF1.bottom = view1Rect.bottom + padding;
        point1 = new Point();
        Rect viewFollowRect = getLocationInView(parent, viewFollow);
        point1.x = viewFollowRect.left + viewFollowRect.width() / 2 + offset;
        point1.y = viewFollowRect.bottom - dp2px(3);

        //计算高亮区域2和手指头的坐标
        rectF2 = new RectF();
        Rect view2Rect = getLocationInView(parent, view2);
        rectF2.left = view2Rect.left - padding;
        rectF2.top = view2Rect.top - padding;
        rectF2.right = view2Rect.right + padding;
        rectF2.bottom = view2Rect.bottom + padding;
        point2 = new Point();
        point2.x = view2Rect.left + view2Rect.width() / 2 + offset;
        point2.y = view2Rect.top + dp2px(8);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "点击任何地方", Toast.LENGTH_SHORT).show();
                if (currentGuide == 1) {
                    if (guideView1 != null && guideView2 != null) {
                        guideView1.setVisibility(INVISIBLE);
                        guideView2.setVisibility(VISIBLE);
                        currentGuide = 2;
                        requestLayout();
                    }
                } else if (currentGuide == 2) {
                    if (onGuideLayoutFinishListener != null) {
                        onGuideLayoutFinishListener.onGuideLayoutFinish();
                    }
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addGuideView();
    }

    private void addGuideView() {
        guideView1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_live_task1_guide, this, false);
        TextView tvGotIt1 = guideView1.findViewById(R.id.tvGotIt1);
        tvGotIt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "知道了1", Toast.LENGTH_SHORT).show();
                guideView1.setVisibility(INVISIBLE);
                guideView2.setVisibility(VISIBLE);
                currentGuide = 2;
                requestLayout();
            }
        });
        addView(guideView1);
        guideView2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_live_task2_guide, this, false);
        TextView tvGotIt2 = guideView2.findViewById(R.id.tvGotIt2);
        tvGotIt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "知道了2", Toast.LENGTH_SHORT).show();
                if (onGuideLayoutFinishListener != null) {
                    onGuideLayoutFinishListener.onGuideLayoutFinish();
                }
            }
        });
        addView(guideView2);
        guideView2.setVisibility(INVISIBLE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        FrameLayout.LayoutParams guideView1Lp = (FrameLayout.LayoutParams) guideView1.getLayoutParams();
        int guideView1LeftMargin = point1.x - guideView1.getMeasuredWidth() / 2;
        if (guideView1LeftMargin < rectF1LeftMargin) {
            guideView1LeftMargin = rectF1LeftMargin;
        }
        guideView1Lp.leftMargin = guideView1LeftMargin;
        guideView1Lp.topMargin = point1.y;
        guideView1.requestLayout();

        FrameLayout.LayoutParams guideView2Lp = (FrameLayout.LayoutParams) guideView2.getLayoutParams();
        int guideView2LeftMargin = point2.x - guideView2.getMeasuredWidth() / 2;
        if (guideView2LeftMargin < rectF2LeftMargin) {
            guideView2LeftMargin = rectF2LeftMargin;
        }
        guideView2Lp.leftMargin = guideView2LeftMargin;
        guideView2Lp.topMargin = point2.y - guideView2.getMeasuredHeight();
        guideView2.requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHighLights(canvas);

    }

    private void drawHighLights(Canvas canvas) {
        if (currentGuide == 1) {
            canvas.drawRoundRect(rectF1, round, round, mPaint);
        } else if (currentGuide == 2) {
            canvas.drawRoundRect(rectF2, round, round, mPaint);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = event.getX();
//                downY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                float upX = event.getX();
//                float upY = event.getY();
//                if (Math.abs(upX - downX) < touchSlop && Math.abs(upY - downY) < touchSlop) {
////                    List<HighLight> highLights = guidePage.getHighLights();
////                    for (HighLight highLight : highLights) {
////                        RectF rectF = highLight.getRectF((ViewGroup) getParent());
////                        if (rectF.contains(upX, upY)) {
////                            notifyClickListener(highLight);
////                            return true;
////                        }
////                    }
//                    performClick();
//                }
//                break;
//
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * dp转px
     */
    public int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        if (scale <= 0) {
            return (int) dpValue;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    public interface onGuideLayoutFinishListener {
        void onGuideLayoutFinish();
    }

    public static Rect getLocationInView(View parent, View child) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("parent and child can not be null .");
        }

        View decorView = null;
        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }

        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;

        if (child == parent) {
            child.getHitRect(result);
            return result;
        }
        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!tmp.getClass().equals(FRAGMENT_CON)) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
            if (tmp == null) {
                throw new IllegalArgumentException("the view is not showing in the window!");
            }
            //fix ScrollView中无法获取正确的位置
            if (tmp.getParent() instanceof ScrollView) {
                ScrollView scrollView = (ScrollView) tmp.getParent();
                int scrollY = scrollView.getScrollY();
                result.top -= scrollY;
            }
            if (tmp.getParent() instanceof HorizontalScrollView) {
                HorizontalScrollView horizontalScrollView = (HorizontalScrollView) tmp.getParent();
                int scrollX = horizontalScrollView.getScrollX();
                result.left -= scrollX;
            }

            //added by isanwenyu@163.com fix bug #21 the wrong rect user will received in ViewPager
            if (tmp.getParent() != null && (tmp.getParent() instanceof ViewPager)) {
                tmp = (View) tmp.getParent();
            }
        }
        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }
}

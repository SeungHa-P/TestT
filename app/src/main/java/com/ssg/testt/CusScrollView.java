package com.ssg.testt;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

public class CusScrollView extends ScrollView implements ViewTreeObserver.OnGlobalLayoutListener {


    private boolean anicheck = false;

    private int tTemp = 0;

    public CusScrollView(Context context) {
        super(context, null, 0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public CusScrollView(Context context, AttributeSet attr) {
        super(context, attr, 0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public CusScrollView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    private float dencity;
    private boolean scrollnext = true;


    private boolean headimg = false;
    private float mHeaderInitPosition = 0f;
    private boolean mlsHeaderSticky = false;
    private boolean flagHeader = false;

    private float itemLinePosition = 0f;

    private int scrollline;
    private Animation fadein = AnimationUtils.loadAnimation(getContext(), R.anim.ani_fade_in);


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        gd.onTouchEvent(ev);
        return true;


    }


    GestureDetector gd = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {

            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int a = getScrollY();
            if (distanceY > 0) {
                scrollnext = true;

            } else {
                scrollnext = false;
            }
            setScrollY(a + (int) distanceY+1);




            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //  fling((int)velocityY*-1/4);

            fling((int) velocityY * -1 / 2);
            Log.d("flingScroll", "" + getScrollY());
            return true;
        }
    });


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("scrollposition", " t : " + t + "\n oldt : " + oldt);





        int scrolly = t;
        if (scrolly > mHeaderInitPosition) {
            stickHeader(scrolly - mHeaderInitPosition);
            header.setTranslationZ(2f);
        } else {
            freeHeader();

        }

        if (headimg) {
//            header.setBackgroundColor(Color.parseColor("#80000000"));
            header.setBackgroundResource(R.drawable.header_back2);
            if (headertitle != null) {
                if (!flagHeader) {
                    headertitle.setVisibility(View.VISIBLE);
                    headertitle.setText("랑방 (향수) 랑방잔느 오 드 퍼퓸 50ml");
                    headertitle.startAnimation(fadein);
                    flagHeader = true;
                }

            }


            if (HeadConst != null) {
                if (t >= itemDetailConst.getTop()) {
                    headertitle.setVisibility(View.GONE);
                    Choice.setVisibility(View.VISIBLE);


                } else {
                    headertitle.setVisibility(View.VISIBLE);
                    Choice.setVisibility(View.GONE);
                }
            }


        } else {
            header.setBackgroundColor(Color.parseColor("#ffffff"));
            if (headertitle != null) {
                headertitle.setVisibility(View.GONE);
                flagHeader = false;
            }
        }





        if(Choice.getVisibility() == View.VISIBLE) {
            if (t >= itemDetailConst.getTop() && t < logoConst.getTop()) {
                chageHeadText(0);
            } else if (t >= logoConst.getTop() && t < qnaConst.getTop()) {
                chageHeadText(1);
            } else if (t >= qnaConst.getTop() && t < qnaConst.getBottom()) {
                chageHeadText(2);
            } else if (t >= qnaConst.getBottom()) {
                chageHeadText(3);
            }
        }
        ViewGroup.LayoutParams params = item.getLayoutParams();

        if (scrollnext) {

            Log.d("Updown", "Down");
            Log.d("itemScal", "Height : " + item.getHeight() + "   " + 50 * dencity + "\nWidth : " + item.getWidth());

            //=========================================================================
            if (scrolly >= mHeaderInitPosition && item.getHeight() >= 50 * dencity && item.getWidth() >= 50 * dencity) {
                setScrollY((int) mHeaderInitPosition);
                params.height = (int) (item.getHeight() - 5 * dencity);
                item.setLayoutParams(params);
            } else {

            }
            if (!(item.getHeight() >= 50 * dencity && item.getWidth() >= 50 * dencity)) {
                if (item.getTranslationX() <= 550) {
                    headimg = true;
                    item.setTranslationX(item.getTranslationX() + 15 * dencity);
                    Log.d("trans", "" + item.getTranslationX());
                } else {
                    headimg = true;
                }

            }
            //=========================================================================


        } else {
            if (scrolly > itemLinePosition) {
                stickHeader(scrolly - mHeaderInitPosition);
            } else {
                freeHeader();

            }
            Log.d("Updown", "Up");

            //=========================================================================
            if (scrolly < itemLinePosition-20 && item.getHeight() <= 1360 && item.getWidth() <= 1360) {
                headimg = false;
                header.setTranslationZ(0f);
                if (item.getTranslationX() > 0) {
                    item.setTranslationX(item.getTranslationX() - 15 * dencity);
                } else {
                    setScrollY((int) mHeaderInitPosition);
                    params.height = (int) (item.getHeight() + 5 * dencity);
                    item.setLayoutParams(params);
                }
            }
            //=========================================================================

        }


    }

    public void stickListener(View view) {
        Log.d("LoggerHead", "stickListener");
    }

    public void freeListener(View view) {
        Log.d("LoggerHead", "freeListener");
    }

    private void stickHeader(float position) {
        if (header != null) {
            header.setTranslationY(position);
            callStickListener();
        }
        if (item != null) {
//            item.setTranslationY(position);
//
//            callStickListener();
        }
    }


    private void callStickListener() {
        if (!mlsHeaderSticky) {
            if (header != null) {
                stickListener(header);
            } else {
                return;
            }
            if (item != null) {
                stickListener(item);
            } else {
                return;
            }

            mlsHeaderSticky = true;
        }
    }

    private void freeHeader() {
        if (header != null) {
            header.setTranslationY(0f);
            if (scrollnext) {
                header.setTranslationZ(1f);
            } else {
                header.setTranslationZ(0f);
            }
            callFreeListener();
        }
//        if (item != null) {
//            item.setTranslationY(0f);
//            item.setTranslationZ(100f);
//            callStickListener();
//        }
    }

    private void callFreeListener() {
        if (mlsHeaderSticky) {
            if (header != null) {
                freeListener(header);
            } else {
                return;
            }
            if (item != null) {
                freeListener(item);
            } else {
                return;
            }


            mlsHeaderSticky = false;
        }
    }


    @Override
    public void onGlobalLayout() {
        if (header != null) {
            mHeaderInitPosition = header.getTop();
            if (itemline != null) {
                itemLinePosition = itemline.getTop();
            }
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private View topConst;

    private View header;
    private View item;
    private View itemline;
    private TextView headertitle;

    private TextView headDetail;
    private TextView headReview;
    private TextView headQna;
    private TextView headAlpha;
    private View Choice;
    private View HeadConst;

    private View itemDetailConst;
    private View logoConst;

    private View qnaConst;


    public TextView getHeadAlpha() {
        return headAlpha;
    }

    public void setHeadAlpha(TextView headAlpha) {
        this.headAlpha = headAlpha;
    }

    public View getItemDetailConst() {
        return itemDetailConst;
    }

    public void setItemDetailConst(View itemDetailConst) {
        this.itemDetailConst = itemDetailConst;
    }

    public View getLogoConst() {
        return logoConst;
    }

    public void setLogoConst(View logoConst) {
        this.logoConst = logoConst;
    }

    public View getQnaConst() {
        return qnaConst;
    }

    public void setQnaConst(View qnaConst) {
        this.qnaConst = qnaConst;
    }

    public View getHeadConst() {
        return HeadConst;
    }

    public void setHeadConst(View headConst) {
        HeadConst = headConst;
    }

    public View getHeadDetail() {
        return headDetail;
    }

    public void setHeadDetail(TextView headDetail) {
        this.headDetail = headDetail;
    }

    public View getHeadReview() {
        return headReview;
    }

    public void setHeadReview(TextView headReview) {
        this.headReview = headReview;
    }

    public View getHeadQna() {
        return headQna;
    }

    public void setHeadQna(TextView headQna) {
        this.headQna = headQna;
    }

    public View getChoice() {
        return Choice;
    }

    public void setChoice(View choice) {
        Choice = choice;
    }

    public View getTopConst() {
        return topConst;
    }

    public void setTopConst(View topConst) {
        this.topConst = topConst;
    }

    public View getItemline() {
        return itemline;
    }

    public void setItemline(View itemline) {
        this.itemline = itemline;
    }

    public View getItem() {
        return item;
    }

    public void setItem(View item) {
        this.item = item;


    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
    }

    public View getHeader() {
        return header;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    public void setHeader(View header) {
        this.header = header;


    }


    public float getDencity() {
        return dencity;
    }

    public void setDencity(float dencity) {
        this.dencity = dencity;
    }

    public TextView getHeadertitle() {
        return headertitle;
    }

    public void setHeadertitle(TextView headertitle) {
        this.headertitle = headertitle;
    }

    private void chageHeadText(int a) {
        if(a ==0){
            headDetail.setTextColor(Color.RED);
            headAlpha.setTextColor(Color.BLACK);
            headQna.setTextColor(Color.BLACK);
            headReview.setTextColor(Color.BLACK);
        }else if(a == 1){
            headDetail.setTextColor(Color.BLACK);
            headAlpha.setTextColor(Color.BLACK);
            headQna.setTextColor(Color.BLACK);
            headReview.setTextColor(Color.RED);
        }else if(a==2){
            headDetail.setTextColor(Color.BLACK);
            headAlpha.setTextColor(Color.BLACK);
            headQna.setTextColor(Color.RED);
            headReview.setTextColor(Color.BLACK);
        }else if(a == 3){
            headDetail.setTextColor(Color.BLACK);
            headAlpha.setTextColor(Color.RED);
            headQna.setTextColor(Color.BLACK);
            headReview.setTextColor(Color.BLACK);
        }

    }
}


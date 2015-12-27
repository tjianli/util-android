package appcoo.util.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class GestureHelper implements OnGestureListener {
    private GestureDetector mGestureDetector;
    private int mScreenWidth;
    private OnFlingListener mOnFlingListener;

    public GestureHelper(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;

        mGestureDetector = new GestureDetector(context, this);
    }

    public void setOnFlingListener(OnFlingListener listener) {
        mOnFlingListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 触发条件 ：
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
        final int FLING_MIN_DISTANCE = (int) (mScreenWidth / 4.0f), FLING_MIN_VELOCITY = 100;
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            mOnFlingListener.OnFlingLeft();
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY && Math.abs(e2.getY() - e1.getY()) < 100) {
            mOnFlingListener.OnFlingRight();
        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public static abstract class OnFlingListener {
        public abstract void OnFlingLeft();

        public abstract void OnFlingRight();
    }
}
package appcoo.util.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements OnScaleGestureListener,
OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener

{
private static final String TAG = ZoomImageView.class.getSimpleName();
public static final float SCALE_MAX = 4.0f;
private static final float SCALE_MID = 2.0f;

/**
* 鍒濆鍖栨椂鐨勭缉鏀炬瘮渚嬶紝濡傛灉鍥剧墖瀹芥垨楂樺ぇ浜庡睆骞曪紝姝ゅ�灏嗗皬浜�
*/
private float initScale = 1.0f;
private boolean once = true;

/**
* 鐢ㄤ簬瀛樻斁鐭╅樀鐨�涓�
*/
private final float[] matrixValues = new float[9];

/**
* 缂╂斁鐨勬墜鍔挎娴�
*/
private ScaleGestureDetector mScaleGestureDetector = null;
private final Matrix mScaleMatrix = new Matrix();

/**
* 鐢ㄤ簬鍙屽嚮妫�祴
*/
private GestureDetector mGestureDetector;
private boolean isAutoScale;

private int mTouchSlop;

private float mLastX;
private float mLastY;

private boolean isCanDrag;
private int lastPointerCount;

private boolean isCheckTopAndBottom = true;
private boolean isCheckLeftAndRight = true;

public ZoomImageView(Context context)
{
this(context, null);
}

public ZoomImageView(Context context, AttributeSet attrs)
{
super(context, attrs);
super.setScaleType(ScaleType.MATRIX);
mGestureDetector = new GestureDetector(context,
		new SimpleOnGestureListener()
		{
			@Override
			public boolean onDoubleTap(MotionEvent e)
			{
				if (isAutoScale == true)
					return true;

				float x = e.getX();
				float y = e.getY();
				Log.e("DoubleTap", getScale() + " , " + initScale);
				if (getScale() < SCALE_MID)
				{
					ZoomImageView.this.postDelayed(
							new AutoScaleRunnable(SCALE_MID, x, y), 16);
					isAutoScale = true;
				} else if (getScale() >= SCALE_MID
						&& getScale() < SCALE_MAX)
				{
					ZoomImageView.this.postDelayed(
							new AutoScaleRunnable(SCALE_MAX, x, y), 16);
					isAutoScale = true;
				} else
				{
					ZoomImageView.this.postDelayed(
							new AutoScaleRunnable(initScale, x, y), 16);
					isAutoScale = true;
				}

				return true;
			}
		});
mScaleGestureDetector = new ScaleGestureDetector(context, this);
this.setOnTouchListener(this);
}

/**
* 鑷姩缂╂斁鐨勪换鍔�
* 
* @author zhy
* 
*/
private class AutoScaleRunnable implements Runnable
{
static final float BIGGER = 1.07f;
static final float SMALLER = 0.93f;
private float mTargetScale;
private float tmpScale;

/**
 * 缂╂斁鐨勪腑蹇�
 */
private float x;
private float y;

/**
 * 浼犲叆鐩爣缂╂斁鍊硷紝鏍规嵁鐩爣鍊间笌褰撳墠鍊硷紝鍒ゆ柇搴旇鏀惧ぇ杩樻槸缂╁皬
 * 
 * @param targetScale
 */
public AutoScaleRunnable(float targetScale, float x, float y)
{
	this.mTargetScale = targetScale;
	this.x = x;
	this.y = y;
	if (getScale() < mTargetScale)
	{
		tmpScale = BIGGER;
	} else
	{
		tmpScale = SMALLER;
	}

}

@Override
public void run()
{
	// 杩涜缂╂斁
	mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
	checkBorderAndCenterWhenScale();
	setImageMatrix(mScaleMatrix);

	final float currentScale = getScale();
	// 濡傛灉鍊煎湪鍚堟硶鑼冨洿鍐咃紝缁х画缂╂斁
	if (((tmpScale > 1f) && (currentScale < mTargetScale))
			|| ((tmpScale < 1f) && (mTargetScale < currentScale)))
	{
		ZoomImageView.this.postDelayed(this, 16);
	} else
	// 璁剧疆涓虹洰鏍囩殑缂╂斁姣斾緥
	{
		final float deltaScale = mTargetScale / currentScale;
		mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
		checkBorderAndCenterWhenScale();
		setImageMatrix(mScaleMatrix);
		isAutoScale = false;
	}

}
}

@SuppressLint("NewApi")
@Override
public boolean onScale(ScaleGestureDetector detector)
{
float scale = getScale();
float scaleFactor = detector.getScaleFactor();

if (getDrawable() == null)
	return true;

/**
 * 缂╂斁鐨勮寖鍥存帶鍒�
 */
if ((scale < SCALE_MAX && scaleFactor > 1.0f)
		|| (scale > initScale && scaleFactor < 1.0f))
{
	/**
	 * 鏈�ぇ鍊兼渶灏忓�鍒ゆ柇
	 */
	if (scaleFactor * scale < initScale)
	{
		scaleFactor = initScale / scale;
	}
	if (scaleFactor * scale > SCALE_MAX)
	{
		scaleFactor = SCALE_MAX / scale;
	}
	/**
	 * 璁剧疆缂╂斁姣斾緥
	 */
	mScaleMatrix.postScale(scaleFactor, scaleFactor,
			detector.getFocusX(), detector.getFocusY());
	checkBorderAndCenterWhenScale();
	setImageMatrix(mScaleMatrix);
}
return true;

}

/**
* 鍦ㄧ缉鏀炬椂锛岃繘琛屽浘鐗囨樉绀鸿寖鍥寸殑鎺у埗
*/
private void checkBorderAndCenterWhenScale()
{

RectF rect = getMatrixRectF();
float deltaX = 0;
float deltaY = 0;

int width = getWidth();
int height = getHeight();

// 濡傛灉瀹芥垨楂樺ぇ浜庡睆骞曪紝鍒欐帶鍒惰寖鍥�
if (rect.width() >= width)
{
	if (rect.left > 0)
	{
		deltaX = -rect.left;
	}
	if (rect.right < width)
	{
		deltaX = width - rect.right;
	}
}
if (rect.height() >= height)
{
	if (rect.top > 0)
	{
		deltaY = -rect.top;
	}
	if (rect.bottom < height)
	{
		deltaY = height - rect.bottom;
	}
}
// 濡傛灉瀹芥垨楂樺皬浜庡睆骞曪紝鍒欒鍏跺眳涓�
if (rect.width() < width)
{
	deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
}
if (rect.height() < height)
{
	deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
}
Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

mScaleMatrix.postTranslate(deltaX, deltaY);

}

/**
* 鏍规嵁褰撳墠鍥剧墖鐨凪atrix鑾峰緱鍥剧墖鐨勮寖鍥�
* 
* @return
*/
private RectF getMatrixRectF()
{
Matrix matrix = mScaleMatrix;
RectF rect = new RectF();
Drawable d = getDrawable();
if (null != d)
{
	rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
	matrix.mapRect(rect);
}
return rect;
}

@Override
public boolean onScaleBegin(ScaleGestureDetector detector)
{
return true;
}

@Override
public void onScaleEnd(ScaleGestureDetector detector)
{
}

@Override
public boolean onTouch(View v, MotionEvent event)
{

if (mGestureDetector.onTouchEvent(event))
	return true;
mScaleGestureDetector.onTouchEvent(event);

float x = 0, y = 0;
// 鎷垮埌瑙︽懜鐐圭殑涓暟
final int pointerCount = event.getPointerCount();
// 寰楀埌澶氫釜瑙︽懜鐐圭殑x涓巠鍧囧�
for (int i = 0; i < pointerCount; i++)
{
	x += event.getX(i);
	y += event.getY(i);
}
x = x / pointerCount;
y = y / pointerCount;

/**
 * 姣忓綋瑙︽懜鐐瑰彂鐢熷彉鍖栨椂锛岄噸缃甿LasX , mLastY
 */
if (pointerCount != lastPointerCount)
{
	isCanDrag = false;
	mLastX = x;
	mLastY = y;
}

lastPointerCount = pointerCount;
RectF rectF = getMatrixRectF();
switch (event.getAction())
{
case MotionEvent.ACTION_DOWN:
	if (rectF.width() > getWidth() || rectF.height() > getHeight())
	{
		getParent().requestDisallowInterceptTouchEvent(true);
	}
	break;
case MotionEvent.ACTION_MOVE:
	if (rectF.width() > getWidth() || rectF.height() > getHeight())
	{
		getParent().requestDisallowInterceptTouchEvent(true);
	}
	Log.e(TAG, "ACTION_MOVE");
	float dx = x - mLastX;
	float dy = y - mLastY;

	if (!isCanDrag)
	{
		isCanDrag = isCanDrag(dx, dy);
	}
	if (isCanDrag)
	{

		if (getDrawable() != null)
		{
			// if (getMatrixRectF().left == 0 && dx > 0)
			// {
			// getParent().requestDisallowInterceptTouchEvent(false);
			// }
			//
			// if (getMatrixRectF().right == getWidth() && dx < 0)
			// {
			// getParent().requestDisallowInterceptTouchEvent(false);
			// }
			isCheckLeftAndRight = isCheckTopAndBottom = true;
			// 濡傛灉瀹藉害灏忎簬灞忓箷瀹藉害锛屽垯绂佹宸﹀彸绉诲姩
			if (rectF.width() < getWidth())
			{
				dx = 0;
				isCheckLeftAndRight = false;
			}
			// 濡傛灉楂樺害灏忛洦灞忓箷楂樺害锛屽垯绂佹涓婁笅绉诲姩
			if (rectF.height() < getHeight())
			{
				dy = 0;
				isCheckTopAndBottom = false;
			}
			

			mScaleMatrix.postTranslate(dx, dy);
			checkMatrixBounds();
			setImageMatrix(mScaleMatrix);
		}
	}
	mLastX = x;
	mLastY = y;
	break;

case MotionEvent.ACTION_UP:
case MotionEvent.ACTION_CANCEL:
	Log.e(TAG, "ACTION_UP");
	lastPointerCount = 0;
	break;
}

return true;
}

/**
* 鑾峰緱褰撳墠鐨勭缉鏀炬瘮渚�
* 
* @return
*/
public final float getScale()
{
mScaleMatrix.getValues(matrixValues);
return matrixValues[Matrix.MSCALE_X];
}

@Override
protected void onAttachedToWindow()
{
super.onAttachedToWindow();
getViewTreeObserver().addOnGlobalLayoutListener(this);
}

@SuppressWarnings("deprecation")
@Override
protected void onDetachedFromWindow()
{
super.onDetachedFromWindow();
getViewTreeObserver().removeGlobalOnLayoutListener(this);
}

@Override
public void onGlobalLayout()
{
if (once)
{
	Drawable d = getDrawable();
	if (d == null)
		return;
	Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
	int width = getWidth();
	int height = getHeight();
	// 鎷垮埌鍥剧墖鐨勫鍜岄珮
	int dw = d.getIntrinsicWidth();
	int dh = d.getIntrinsicHeight();
	float scale = 1.0f;
	// 濡傛灉鍥剧墖鐨勫鎴栬�楂樺ぇ浜庡睆骞曪紝鍒欑缉鏀捐嚦灞忓箷鐨勫鎴栬�楂�
	if (dw > width && dh <= height)
	{
		scale = width * 1.0f / dw;
	}
	if (dh > height && dw <= width)
	{
		scale = height * 1.0f / dh;
	}
	// 濡傛灉瀹藉拰楂橀兘澶т簬灞忓箷锛屽垯璁╁叾鎸夋寜姣斾緥閫傚簲灞忓箷澶у皬
	if (dw > width && dh > height)
	{
		scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
	}
	initScale = scale;

	Log.e(TAG, "initScale = " + initScale);
	mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
	mScaleMatrix.postScale(scale, scale, getWidth() / 2,
			getHeight() / 2);
	// 鍥剧墖绉诲姩鑷冲睆骞曚腑蹇�
	setImageMatrix(mScaleMatrix);
	once = false;
}

}

/**
* 绉诲姩鏃讹紝杩涜杈圭晫鍒ゆ柇锛屼富瑕佸垽鏂鎴栭珮澶т簬灞忓箷鐨�
*/
private void checkMatrixBounds()
{
RectF rect = getMatrixRectF();

float deltaX = 0, deltaY = 0;
final float viewWidth = getWidth();
final float viewHeight = getHeight();
// 鍒ゆ柇绉诲姩鎴栫缉鏀惧悗锛屽浘鐗囨樉绀烘槸鍚﹁秴鍑哄睆骞曡竟鐣�
if (rect.top > 0 && isCheckTopAndBottom)
{
	deltaY = -rect.top;
}
if (rect.bottom < viewHeight && isCheckTopAndBottom)
{
	deltaY = viewHeight - rect.bottom;
}
if (rect.left > 0 && isCheckLeftAndRight)
{
	deltaX = -rect.left;
}
if (rect.right < viewWidth && isCheckLeftAndRight)
{
	deltaX = viewWidth - rect.right;
}
mScaleMatrix.postTranslate(deltaX, deltaY);
}

/**
* 鏄惁鏄帹鍔ㄨ涓�
* 
* @param dx
* @param dy
* @return
*/
private boolean isCanDrag(float dx, float dy)
{
return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
}

}


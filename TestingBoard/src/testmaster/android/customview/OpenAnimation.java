package testmaster.android.customview;

import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public class OpenAnimation extends TranslateAnimation implements AnimationListener {

	private LinearLayout mainLayout;
	int panelWidth;

	public OpenAnimation(LinearLayout layout, int width, int fromXType,
			float fromXValue, int toXType, float toXValue, int fromYType,
			float fromYValue, int toYType, float toYValue) {

		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
				toYType, toYValue);

		// init
		mainLayout = layout;
		panelWidth = width;
		setDuration(250);
		setFillAfter(false);
		setInterpolator(new AccelerateDecelerateInterpolator());
		setAnimationListener(this);
		mainLayout.startAnimation(this);
	}

	public void onAnimationEnd(Animation arg0) {

		LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
		params.leftMargin = panelWidth;
		params.gravity = Gravity.START;
		mainLayout.clearAnimation();
		mainLayout.setLayoutParams(params);
		mainLayout.requestLayout();

	}

	public void onAnimationRepeat(Animation arg0) {

	}

	public void onAnimationStart(Animation arg0) {

	}

}

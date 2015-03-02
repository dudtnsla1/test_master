package testmaster.android.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.widget.Toast;

public class CustomViewPager extends ViewPager {

	private boolean enable = true;
	Context context;
	
	public CustomViewPager(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			if (enable) {
				return super.onTouchEvent(event);
			}
		} catch (Exception e) {
	        e.printStackTrace();
		}
		Toast.makeText(context, "설정이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(android.view.MotionEvent arg0) {
		if (enable)
			return super.onInterceptTouchEvent(arg0);
		return false;
	}
	
	public void setEnable() {
		enable = true;
	}
	
	public void setDisable() {
		enable = false;
	}

	public boolean isPagingEnale() {
		// TODO Auto-generated method stub
		return enable;
	}

}

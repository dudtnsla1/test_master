package testmaster.android.page;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity.ContentLayoutIds;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	private LayoutInflater mLayoutInflater;
	private int count;
	private int []layoutIds;
	FunctionContext functionContext;
	private int settingChangeIndex;
	private int prevItem = 1;
	private ContentLayoutIds content;

	Context context;
	
	public void updatePreference() {
		functionContext.updatePreference();
	}
	
	public void updateChart() {
		functionContext.updateChart();
	}

	public ViewPagerAdapter(Context context, ContentLayoutIds content){
		super();
		this.context = context;
		this.content = content;
		mLayoutInflater = LayoutInflater.from(context);
		this.layoutIds = content.getPageIds();
		this.count = layoutIds.length;
		settingChangeIndex = content.getSettingChangeIndex();
		functionContext = FunctionContextFactory.createContext(context, content);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		int currentItem = ((ViewPager)container).getCurrentItem();

		if (currentItem == settingChangeIndex)
			functionContext.saveSettingData();
			
		if( prevItem != currentItem) {
			functionContext.pageChanged(currentItem);
			if (prevItem == settingChangeIndex && currentItem == settingChangeIndex + 1) {
				SettingPacket packet = functionContext.settingChanged();
				packet.sendPacket(packet.getPacket());
			}
		}
		prevItem = currentItem;

		super.finishUpdate(container);
	}

	@Override
	public Object instantiateItem(View pager, int index) {

		View view = null;
		view = mLayoutInflater.inflate(layoutIds[index], null);
		((ViewPager)pager).addView(view, 0);
		return view; 
	}
	
	public void destroy() {
		functionContext.destroy();
	}

	@Override
	public void destroyItem(View pager, int position, Object view) { 
		((ViewPager)pager).removeView((View)view);
	}
}
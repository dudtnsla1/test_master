package testmaster.android.resource;

import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

public class LoadedImage {
	private static Drawable mainHomeBluetoothBackground;
	private static Drawable mainHomeStm32Image;	

	private static Point displaySize = new Point();
	private static int displayWidth;
	private static int displayHeight;
	
	public static Drawable getImage(int id) {
		switch (id) {
		case R.drawable.image_bluetooth_connect:
			return mainHomeBluetoothBackground;
		}
		return null;
	}

	public static void loadImage(Activity activity) {
		
		Display display = ((WindowManager)activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		display.getSize(displaySize);
		displayWidth = displaySize.x;
		displayHeight = displaySize.y;
		
		mainHomeBluetoothBackground = new BitmapDrawable(activity.getResources(),
				resizeBitmapSizeofDisplay(R.drawable.image_bluetooth_connect, activity));
	}

	private static Bitmap resizeBitmapSizeofDisplay(int resId, Activity activity) {
		return resizeBitmapFromResource(activity.getResources(),
				resId,
				displayWidth, 
				displayHeight);
	}

	private static Bitmap resizeBitmapFromResource(Resources res,   
			int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calcInImgSize(options, reqWidth, reqHeight);		 
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calcInImgSize( 
			BitmapFactory.Options options, int reqdWidth, int reqdHeight ) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampSize= 1;		 

		if (height > reqdHeight || width > reqdWidth) {

			if (width > height) {
				inSampSize= Math.round((float)height / (float)reqdHeight);
			} else {
				inSampSize= Math.round((float)width / (float)reqdWidth);
			}
		}
		return inSampSize;
	}
}
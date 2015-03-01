package testmaster.android.page;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferenceUtil {
	public static void putString(Context context, String key, String value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key,  value);
		editor.commit();
	}
	public static String getString(Context context, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(key, null);
	}

	public static void putStringSet(Context context, String key, Set<String> value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putStringSet(key,  value);
		editor.commit();
	}
	public static Set<String> getStringSet(Context context, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getStringSet(key, null);
	}
	
	public static void putInt(Context context, String key, int value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key,  value);
		editor.commit();
	}
	public static int getInt(Context context, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getInt(key, -1);
	}
	
	public static void putLong(Context context, String key, long value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key,  value);
		editor.commit();
	}
	public static long getLong(Context context, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getLong(key, -1);
	}
	public static long removePer(Context context, String key){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getLong(key, -1);
	}	
}


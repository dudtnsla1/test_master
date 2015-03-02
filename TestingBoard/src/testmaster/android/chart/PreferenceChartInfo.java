package testmaster.android.chart;

import testmaster.android.database.DbOpenProxy;

public interface PreferenceChartInfo {
	public final String []KEYS_ADC = new String[] {"adc_dataset1", "adc_dataset2", "adc_dataset3"};
	public final String []KEYS_MOTOR = new String[] {"motor_dataset1", "motor_dataset2", "motor_dataset3"};
	public final String KEY_X_MAX = "max_x";
	public final String KEY_Y_MAX = "max_y";
	public static final int KIND_ADC = DbOpenProxy.KIND_ADC;
	public static final int KIND_MOTOR = DbOpenProxy.KIND_MOTOR;
}

package testmaster.android.chart;

import testmaster.android.database.DbOpenHelper;

public interface PreferenceChartInfo {
	public final String []KEYS_ADC = new String[] {"adc_dataset1", "adc_dataset2", "adc_dataset3"};
	public final String []KEYS_MOTOR = new String[] {"motor_dataset1", "motor_dataset2", "motor_dataset3"};
	public static final int KIND_ADC = DbOpenHelper.KIND_ADC;
	public static final int KIND_MOTOR = DbOpenHelper.KIND_MOTOR;
}

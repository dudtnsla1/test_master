package testmaster.android.testingboard;

import testmaster.android.chart.PreferenceChartInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.inputmethod.EditorInfo;

public class FunctionPreferenceActivity extends PreferenceActivity implements PreferenceChartInfo {
	public static final String EXTRA_KIND = "kind";
	public static final String EXTRA_ORDER = "order";
	public static final String ADC_DATA_DEP = "adc_dep";
	public static final String MOTOR_DATA_DEP = "motor_dep";
	public static final String INIT_DATABASE_PREFERENCE = "init_database";
	Preference []adcSettingPrefer = new Preference[3], motorSettingPrefer= new Preference[3];
	Preference initDatabasePreference;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public class MyPreferenceFragment extends PreferenceFragment implements OnPreferenceClickListener{
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.function_preference);
            initDatabasePreference = findPreference(INIT_DATABASE_PREFERENCE);
            initDatabasePreference.setOnPreferenceClickListener(this);
            for (int i = 0; i < KEYS_ADC.length; i++) {
	            adcSettingPrefer[i] = findPreference(KEYS_ADC[i]);
	            adcSettingPrefer[i].setOnPreferenceClickListener(this);
            }
            for (int i = 0; i < KEYS_MOTOR.length; i++) {
            	motorSettingPrefer[i] = findPreference(KEYS_MOTOR[i]);
            	motorSettingPrefer[i].setOnPreferenceClickListener(this);
            }
        }
        
        private void initialPreference() {
        	SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(FunctionPreferenceActivity.this);
        	Editor editor = preference.edit();
        	for (int i = 0; i < KEYS_ADC.length; i++)
        		editor.putInt(KEYS_ADC[i], -1);
        	for (int i = 0; i < KEYS_MOTOR.length; i++)
        		editor.putInt(KEYS_MOTOR[i], -1);
        	editor.apply();
        	
        			
        }

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(FunctionPreferenceActivity.this, ListActivity.class);
			if (preference.getDependency().equals(ADC_DATA_DEP)) {
				intent.putExtra("kind", ADC_DATA_DEP);
			} else if (preference.getDependency().equals(MOTOR_DATA_DEP)) {
				intent.putExtra("kind", MOTOR_DATA_DEP);
			} else if (preference.getKey().equals(INIT_DATABASE_PREFERENCE)) {
				initialPreference();
				return false;
			}
			intent.putExtra("order", preference.getOrder());
			startActivity(intent);
			return false;
		}
    }
}

package testmaster.android.page;

import testmaster.android.testingboard.MainFunctionActivity.ContentLayoutIds;
import android.content.Context;

public abstract class FunctionContextFactory{
	
	private FunctionContextFactory() {
		
	}
	
	public static FunctionContext createContext(Context context, ContentLayoutIds content) {
		if (content == ContentLayoutIds.ADC)
			return (FunctionContext) new FunctionADCContext(context);
		else if (content == ContentLayoutIds.PWM)
			return (FunctionContext) new FunctionPWMContext(context);
		else if (content == ContentLayoutIds.USART)
			return (FunctionContext) new FunctionUSARTContext(context);
		else if (content == ContentLayoutIds.I2C)
			return (FunctionContext) new FunctionI2CContext(context);
		else if (content == ContentLayoutIds.HIGHLOW)
			return (FunctionContext) new FunctionHighLowContext(context);
		else if (content == ContentLayoutIds.MOTOR)
			return (FunctionContext) new FunctionMotorContext(context);
		return null;
	}
}


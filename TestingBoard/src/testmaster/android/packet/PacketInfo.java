package testmaster.android.packet;

public interface PacketInfo {
	public static final int MODE_ACK = 0;
	public static final int MODE_ADC = 1;
	public static final int MODE_PWM = 2;
	public static final int MODE_USART = 3;
	public static final int MODE_I2C = 4;
	public static final int MODE_MOTOR = 5;
	public static final int MODE_HIGHLOW = 6;
	public static final int MODE_RESET = 7;
}

package testmaster.android.packet;

import org.apache.http.util.ByteArrayBuffer;

/*
 * $,k,Mode,Pin_number,data1,data2,data3*
 * 
 */

public class SettingPacket extends BluetoothPacket implements PacketInfo{
	private ByteArrayBuffer packet = new ByteArrayBuffer(0);
	
	public void setADCPacket(byte pin) {
		initPacket(MODE_ADC);
		packet.append(pin);
		endPacket();
	}
	
	public void setPWMPacket(byte pin, byte buadrateNum) {
		initPacket(MODE_PWM);
		packet.append(pin);
		packet.append(buadrateNum);
		endPacket();
	}
	
	public void setHightLowPacket(byte pin, byte OnOff) {
		initPacket(MODE_HIGHLOW);
		packet.append(pin);
		packet.append(OnOff);
		endPacket();
	}
	
 	public void setUSARTPacket(byte buadrateNum) {
		initPacket(MODE_USART);
		packet.append(buadrateNum);
		endPacket();
	}
	
	public void setI2CPacket() {
		initPacket(MODE_I2C);
		endPacket();		
	}
	
	public void setMotorPacket(byte P, byte I, byte D) {
		initPacket(MODE_MOTOR);
		packet.append(P);
		packet.append(I);
		packet.append(D);
		endPacket();
	}
	
	public void setInitPacket() {
		initPacket(MODE_RESET);
		endPacket();				
	}
	
	public void initPacket(int mode) {
		packet.clear();
		packet.append("$".getBytes(), 0, 1);
		packet.append(mode);
	}
	
	public void endPacket() {
		packet.append("\r\n".getBytes(), 0, 2);;			
	}
	
	public byte[] getPacket() {
		return packet.toByteArray();
	}
}
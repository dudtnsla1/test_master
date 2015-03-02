package testmaster.android.packet;

import java.nio.ByteBuffer;

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
	
	public void setPWMPacket(byte pin, String hz, byte duty) {
		initPacket(MODE_PWM);
		packet.append(pin);
		packet.append(ByteBuffer.allocate(4).putInt(Integer.parseInt(hz)).array(), 0, 4);
		packet.append(duty);
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
	
	public void setI2CPacket(byte slaveAddr, byte reAddr, byte data, byte read, byte []order) {
		initPacket(MODE_I2C);
		packet.append(slaveAddr);
		packet.append(reAddr);
		packet.append(data);
		packet.append(read);
		packet.append(order, 0, 11);
		endPacket();		
	}
	
	public void setMotorPacket(String P, String I, String D, String RPM, String gear, String pulse) {
		initPacket(MODE_MOTOR);		
		
		packet.append(ByteBuffer.allocate(4).putFloat(Float.parseFloat(P)).array(), 0, 4);
		packet.append(ByteBuffer.allocate(4).putFloat(Float.parseFloat(I)).array(), 0, 4);
		packet.append(ByteBuffer.allocate(4).putFloat(Float.parseFloat(D)).array(), 0, 4);
		packet.append(ByteBuffer.allocate(4).putInt(Integer.parseInt(RPM)).array(), 0, 4);
		packet.append(ByteBuffer.allocate(4).putInt(Integer.parseInt(gear)).array(), 0, 4);
		packet.append(ByteBuffer.allocate(4).putInt(Integer.parseInt(pulse)).array(), 0, 4);
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
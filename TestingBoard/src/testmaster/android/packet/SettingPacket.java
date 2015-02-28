package testmaster.android.packet;

import org.apache.http.util.ByteArrayBuffer;

/*
 * $,k,Mode,Pin_number,data1,data2,data3*
 * 
 */

public class SettingPacket extends BluetoothPacket implements PacketInfo{
	private ByteArrayBuffer packet = new ByteArrayBuffer(0);
		
	public void initPacket(int mode) {
		packet.clear();
		packet.append("$k,".getBytes(), 0, 3);
		packet.append(mode + '0');		
	}
	
	public void fillPacket(int size) {
		for (int i = 0; i < size; i++) {
			packet.append(',');
			packet.append(0 + '0');			
		}
		packet.append('!');			
	}
	
	public void setPacket(int mode) {
		// TODO Auto-generated constructor stub
		initPacket(mode);
		fillPacket(3);
	}	
	
	public void setPacket(int mode, int data1) {
		initPacket(mode);
	}
	
	public void setPacket(int mode, int data1, int data2) {
		initPacket(mode);
	}
	
	public void setPacket(int mode, int data1, int data2, int data3) {
		initPacket(mode);
	}
	
	public byte[] getPacket() {
		return packet.toByteArray();
	}
}
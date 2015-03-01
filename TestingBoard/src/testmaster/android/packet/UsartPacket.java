package testmaster.android.packet;

import org.apache.http.util.ByteArrayBuffer;

public class UsartPacket extends BluetoothPacket implements PacketInfo {
	private ByteArrayBuffer packet = new ByteArrayBuffer(0);
	
	public void setPacket(String data) {
		initPacket();
		packet.append(data.getBytes(), 0, data.length());
		endPacket();
	}
	
	public void initPacket() {
		packet.clear();
		packet.append("$".getBytes(), 0, 1);
		packet.append(MODE_USART);
		packet.append(5);
	}
	
	public void endPacket() {
		packet.append("\r\n".getBytes(), 0, 2);			
	}
	public byte[] getPacket() {
		return packet.toByteArray();
	}
}

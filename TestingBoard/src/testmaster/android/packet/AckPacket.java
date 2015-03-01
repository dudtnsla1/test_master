package testmaster.android.packet;

import org.apache.http.util.ByteArrayBuffer;

public class AckPacket extends BluetoothPacket implements PacketInfo {
	private ByteArrayBuffer packet = new ByteArrayBuffer(0);
	
	public void send() {
		initPacket(MODE_ACK);
		endPacket();
		sendPacket(packet.toByteArray());
	}
	
	private void initPacket(int mode) {
		packet.clear();
		packet.append("$".getBytes(), 0, 1);
		packet.append(mode);
	}
	
	private void endPacket() {
		packet.append("\r\n".getBytes(), 0, 2);;			
	}
}

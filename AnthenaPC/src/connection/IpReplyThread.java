package connection;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.main.anthenaandroid.BroadcastPacket;

public class IpReplyThread implements Runnable {
	DatagramSocket socket;
	boolean running = true;
	int portNo;

	IpReplyThread(int portNo) {
		this.portNo = portNo;
	}

	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		try {
			// Keep a socket open to listen to all the UDP traffic that is
			// destined for this port
			socket = new DatagramSocket(portNo, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (running) {
				System.out.println("Ready to receive broadcast packets!");

				// Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);
				// Packet received
				System.out.println(getClass().getName() + "Discovery packet received from: "
						+ packet.getAddress().getHostAddress());
				// See if the packet holds the right command (message)
				ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
				ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
				Object replyObject = is.readObject();
				if (replyObject instanceof BroadcastPacket) {
					BroadcastPacket serverReply = (BroadcastPacket) replyObject;
					if (!serverReply.isServer()) {
						BroadcastPacket o = new BroadcastPacket(true);
						final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
						final ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(o);
						final byte[] sendData = baos.toByteArray();

						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(),
								packet.getPort());
						socket.send(sendPacket);
						System.out.println(getClass().getName() + "Sent ip reply packet to: "
								+ sendPacket.getAddress().getHostAddress());
					}
				}
			}
		} catch (IOException | ClassNotFoundException ex) {

		}
	}
}
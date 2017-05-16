

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;

public class network {
	public void getMac(Socket sock) {
		// TODO Auto-generated method stub
		InetAddress ip;
		try {
			ip = sock.getInetAddress();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());
		}  catch(SocketException e){
			e.printStackTrace();
		}
		
	}

}

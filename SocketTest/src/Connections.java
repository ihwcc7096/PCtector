/*import java.net.*;
import java.util.*;
import java.io.*;

public class Connections extends Thread{
		Socket socket;
        DataInputStream in;
        DataOutputStream out;
        void sendToAll(String msg) {
        	
        	Iterator it = clients.keySet().iterator();
            while (it.hasNext()) {
                try {
                    DataOutputStream out = (DataOutputStream) clients.get(it.next());
                    out.writeUTF(msg);
                } catch (IOException e) {
                }
            } // while
        } // sendToAll
        Connections(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            }
        }
        public void run() {
            String name = "";
            try {
                name = in.readUTF();
                sendToAll("#" + name + "���� �����̽��ϴ�.");
                clients.put(name, out);
                System.out.println("���� ���������� ���� " 
                        + clients.size() + "�Դϴ�.");
                while (in != null) {
                    sendToAll(in.readUTF());
                }
            } catch (IOException e) {
                // ignore
            } finally {
                sendToAll("#" + name + "���� �����̽��ϴ�.");
                clients.remove(name);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" 
                        + "���� ������ �����Ͽ����ϴ�.");
                System.out.println("���� ���������� ���� " 
                        + clients.size() + "�Դϴ�.");
            } // try
        } // run
    } // ReceiverThread


	 //��ó: http://adgw.tistory.com/entry/JAVA-TCP����-���α׷�����-����-��-ä�����α׷�-���� [�Ѷ߰ſ�]
*/


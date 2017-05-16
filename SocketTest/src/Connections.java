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
                sendToAll("#" + name + "님이 들어오셨습니다.");
                clients.put(name, out);
                System.out.println("현재 서버접속자 수는 " 
                        + clients.size() + "입니다.");
                while (in != null) {
                    sendToAll(in.readUTF());
                }
            } catch (IOException e) {
                // ignore
            } finally {
                sendToAll("#" + name + "님이 나가셨습니다.");
                clients.remove(name);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" 
                        + "에서 접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는 " 
                        + clients.size() + "입니다.");
            } // try
        } // run
    } // ReceiverThread


	 //출처: http://adgw.tistory.com/entry/JAVA-TCP소켓-프로그래밍의-이해-및-채팅프로그램-예제 [앗뜨거워]
*/


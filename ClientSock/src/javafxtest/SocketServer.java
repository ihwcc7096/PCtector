package javafxtest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

 public static void main(String[] args){
     try{
           ServerSocket server = new ServerSocket(10001);
           System.out.println("Wating Connect ..");
 
           Socket sock = server.accept();
                   
           InetAddress  inetaddr = sock.getInetAddress();  
           System.out.println(inetaddr.getHostAddress()+ " �κ��� �����߽��ϴ�.");
           System.out.println(sock.getInetAddress()+ " �κ��� �����߽��ϴ�.");
   
    OutputStream out = sock.getOutputStream();
           InputStream in = sock.getInputStream();
          
           PrintWriter print = new PrintWriter(new OutputStreamWriter(out));
           BufferedReader br = new BufferedReader(new InputStreamReader(in));
           String line = null;
           while(true){
        	   	  line = br.readLine();
                  System.out.println("Ŭ���̾�Ʈ�κ��� ���۹��� ���ڿ� : "+line);
                
           }
        //   print.close();
        //   br.close();
          // sock.close();
           } catch(Exception e){
              System.out.println(e);
              }
     }

@Override
public void run() {
	// TODO Auto-generated method stub
	
}
}


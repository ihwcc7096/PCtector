import java.net.*;
import java.util.*;
import java.util.Iterator;
import java.util.StringTokenizer;

import java.io.*;
 
public class Server {
	enum Sign{JOIN, LOGIN, LOGOUT, CAM, GPS, LOCK, UNLOCK, BATTERY, DANGER, FILE_DEL,FILE_LOCA, AUTOLOGIN};
	HashMap<String, DataOutputStream> clients;
	public static Sign get_Sign(String str){	
		Sign sign=null;		
		return sign;
	}
	//socket outputstream hashmap�� ���(�޽��� ���� �� Ŭ���̾�Ʈ ���� ���ؼ�)
	public void register_sock(String clienthw, String id, Socket sock) {
		 DataOutputStream out=null;
		try {
			out = new DataOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(out != null)
			clients.put(clienthw+"><"+id, out);       	 
	}	
   
    Server() {
        clients = new HashMap<String, DataOutputStream>();
        Collections.synchronizedMap(clients);
    }
    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(10001);
            System.out.println("������ ���۵Ǿ����ϴ�.");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "���� �����Ͽ����ϴ�.");
                Connections thread = new Connections(socket);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // start()
    public static void main(String[] args) {
        new Server().start();
    }
    //1socket 1Thread
    public class Connections extends Thread{
		Socket socket;
        DataInputStream in;
        DataOutputStream out;
        //Sign�� ���� �޽��� ���� ����
        public boolean signal_mng(Sign sign,String clienthw, String id, String msg){
    		String sndmsg = sign.toString()+ ">*<";
    		Login ss;
    		switch (sign){
    		   case JOIN: 
                   Join regi= new Join();
                  try {
                     out=new DataOutputStream(socket.getOutputStream());
                     if(regi.ChkJoin(id, msg)==1){
                        System.out.println(id+ " ���ԿϷ�");
                        out.writeUTF(sndmsg+"OK");
                     }
                   //responseMsg(clienthw, id, sndmsg+"OK");
                     else
                        out.writeUTF(sndmsg+"NO");
                  } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  }              
                   break;
                case LOGIN:
                   ss= new Login();
                   register_sock(clienthw, id, socket);
                   System.out.print(id+ " ���ӽõ� - ");
                      if(ss.Chklogin(id,msg)){ //�α��� Ȯ��
                          System.out.println("success");
                          //register_sock(clienthw, id, socket);
                          sendMsg(clienthw, id,sndmsg+"��� Login" );
                          responseMsg(clienthw, id, sndmsg+"OK");
                          sndmsg= "FILE_LOCA"+ ">*<"; //���� ���� ��� ����
                          responseMsg(clienthw, id, sndmsg+ss.loginbean.getfolder());
                          //responseMsg(clienthw, id, sndmsg+ss.loginbean.getfolder());
                      }
                      else{
                         System.out.println("Failed");
                         responseMsg(clienthw, id, sndmsg+"NO");
                         clients.remove(clienthw+"><"+id);;
                      }   
                   break;
    		   case LOGOUT:
    			  sendMsg(clienthw,id,"��� ���� ����");
               	   clients.remove(clienthw+"><"+id);
                   System.out.println("[" + socket.getInetAddress() + ":"
                           + socket.getPort() + "]" 
                           + "����( HW : "+ clienthw+"/ ID : "+id+")������ �����Ͽ����ϴ�.");
                   System.out.println("���� ���������� ���� " 
                           + clients.size() + "�Դϴ�.");
    		   case CAM:
    			   sendMsg(clienthw, id, sndmsg+msg);
    			   cam_save();
    			   break;
    		   case GPS:
    			   sendMsg(clienthw, id, sndmsg+msg);
    			   break;
    		   case LOCK:
    			   sendMsg(clienthw, id, sndmsg+"OK");
    			   break;
    		   case UNLOCK:
    			   sendMsg(clienthw, id, sndmsg+"OK");
    			   break;
    		   case BATTERY:
    			   sendMsg(clienthw, id, sndmsg+msg);
    			   break;
    		   case DANGER:
    			   sendMsg(clienthw, id, sndmsg+msg);
    			   break;
    		   case FILE_DEL:
    			   sendMsg(clienthw, id, sndmsg+msg);
    			   break;
    		   case FILE_LOCA:
    			   ss= new Login();
                   if(ss.SetFolder(id, msg)){//DB�� update
                      System.out.println("success");
                      sndmsg= "FILE_LOCA"+ ">*<";
                      responseMsg(clienthw, id, sndmsg+"OK");
                      //responseMsg(clienthw, id, sndmsg+ss.loginbean.getfolder());
                  }
                  else{
                     System.out.println("Failed");
                     responseMsg(clienthw, id, sndmsg+"NO");
                  }
                  break;
    		   case AUTOLOGIN :
    			   ss= new Login();
    			   ss.getFolder(id);//DB ����� ���� ��� ����
    			   register_sock(clienthw, id, socket);
    			   System.out.println(id+"�ڵ��α��� ����");
    			   System.out.println(ss.loginbean.getfolder());
    			   
    			   sendMsg(clienthw, id,sndmsg+"OK" );
                   responseMsg(clienthw, id, sndmsg+"OK");
                   sndmsg= "FILE_LOCA"+ ">*<";
                   responseMsg(clienthw, id,sndmsg+ss.loginbean.getfolder() );
    			   break;
    		   }
    		return true;
    		}
        // ��ķ ���� ����
        	void cam_save(){//��ó: http://boxfoxs.tistory.com/209 [�ڽ����� - BoxFox]
        		FileOutputStream fout = null;
			   //in = client.getInputStream();                //Ŭ���̾�Ʈ�� ���� ����Ʈ ������ �Է��� �޴� InputStream�� ���� �����մϴ�.
	        	// DataInputStream din = new DataInputStream(in);  //InputStream�� �̿��� ������ ������ �Է��� �޴� DataInputStream�� �����մϴ�.
	        	while(true){
		            int data;
					try {
						data = in.readInt();
					           //Int�� �����͸� ���۹޽��ϴ�.
						String filename = in.readUTF();
	    		        File file = new File(filename);             //�Է¹��� File�� �̸����� �����Ͽ� �����մϴ�.
	    		        fout = new FileOutputStream(file);           //������ ������ Ŭ���̾�Ʈ�κ��� ���۹޾� �ϼ���Ű�� FileOutputStream�� �����մϴ�.
	    		        int datas = data;                            //����Ƚ��, �뷮�� �����ϴ� �����Դϴ�.
	    		        byte[] buffer = new byte[1024];        //����Ʈ������ �ӽ������ϴ� ���۸� �����մϴ�.
	    		        int len;                               //������ �������� ���̸� �����ϴ� �����Դϴ�.    		        
	    		        for(;data>0;data--){                   //���۹��� data�� Ƚ����ŭ ���۹޾Ƽ� FileOutputStream�� �̿��Ͽ� File�� �ϼ���ŵ�ϴ�.
	    		            len = in.read(buffer);
	    		            fout.write(buffer,0,len);
	    		        }   		        
	    		        System.out.println("��: "+datas+" kbps");
	    		        fout.flush();
	    		        fout.close();
	    		        
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("���ۿϷ�");
	        	}
	        }
        // �޾Ҵ� Msg ��� ����
        void responseMsg(String clienthw, String sendid, String msg){
        	Iterator it = clients.keySet().iterator();  
        	
            try {
                while (it.hasNext()) {
                	String tempid = (String) it.next();
                	if(tempid.equals(clienthw+"><"+sendid)){
    	                DataOutputStream out = (DataOutputStream) clients
    	                        .get(tempid);
    	                System.out.println("Response to "+tempid+":"+msg);
    	                out.writeUTF(msg);
                    } 
                 }// while
            } 
            catch (IOException e) {
            }
        }
        //�޽��� ��� hardware�� ����
        void sendMsg(String clienthw,String sendid, String msg) {
            Iterator it = clients.keySet().iterator();
            String rcvhw = null;
            if(clienthw.equals("PC"))
            	rcvhw = "PHONE";
            else
            	rcvhw = "PC";    
            try {
                while (it.hasNext()) {
                	String tempid = (String) it.next();
                	if(tempid.equals(rcvhw+"><"+sendid)){
    	                DataOutputStream out = (DataOutputStream) clients
    	                        .get(tempid);
    	                System.out.println("Send to "+tempid+":"+msg);
    	                out.writeUTF(msg);
                    } 
                 }// while
            } 
            catch (IOException e) {
            }
        } // sendMsg
        //��ü �޽���
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
            StringTokenizer str;
            String id=null;
            String signstr=null;
            Sign sign1=null;
            String clienthw=null;
            String msg=null;
            try {
                while(in != null){
                	String raw = in.readUTF();
                    System.out.println("Ŭ���̾�Ʈ�κ��� ���۹��� ���ڿ� : "+raw);
                    str= new StringTokenizer(raw,">*<");//�޽��� raw ��ū���� ������ ����
                    for(int i=0; i<str.countTokens();i++){
                 	      signstr=str.nextToken();
                 	      clienthw=str.nextToken();
                 		  id=str.nextToken();
                 		  try{//�޽��� ���� ��� ���� ��Ƴ�
                 			  msg=str.nextToken();
                 		  }catch(Exception e){  
                 		  }
                     	  sign1= Sign.valueOf(signstr);
                     	  signal_mng(sign1, clienthw, id, msg);
                     	  }
                 }
            } catch (IOException e) {
                // ignore
            } finally {
            	sendMsg(clienthw,id,"��� ���� ����");
            	clients.remove(clienthw+"><"+id);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" 
                        + "����( HW : "+ clienthw+"/ ID : "+id+")������ �����Ͽ����ϴ�.");
                System.out.println("���� ���������� ���� " 
                        + clients.size() + "�Դϴ�.");
            } // try
        } // run
    }// ReceiverThread
}


// 1. 10001�� ��Ʈ���� �����ϴ� ServerSocket�� ����
// 2. ServerSocket�� accept() �޼ҵ带 �����ؼ� Ŭ���̾�Ʈ�� ������ ���
// : Ŭ���̾�Ʈ�� ������ ��� accept() �޼ҵ�� Socket ��ü�� ��ȯ
//3. ��ȯ���� Socket���κ��� InputStream�� OutputStream�� ����
/*
BufferedReader keyboard =
        new BufferedReader(new InputStreamReader(System.in));
*/
//4. InputStream�� BufferedReader �������� ��ȯ
//    OutputStream�� PrintWriter �������� ��ȯ
//5. BufferedReader�� readLine() �޼ҵ带 �̿���
//   Ŭ���̾�Ʈ�� ������ ���ڿ� �� ���� �о����
/*line = br.readLine();
System.out.println("Ŭ���̾�Ʈ�κ��� ���۹��� ���ڿ� : "+line);
*/
// 6. PrintWriter�� println�� �̿��� �ٽ� Ŭ���̾�Ʈ�� ����
// 6. IO ��ü�� ������ close() �޼ҵ� ȣ��
//[��ó] JAVA ���� ���α׷��� (TCP) (TCP/IP �������) |�ۼ��� Lisa

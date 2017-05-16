import java.net.*;
import java.util.*;
import java.util.Iterator;
import java.util.StringTokenizer;

import java.io.*;
 
public class sserver {
   enum Sign{JOIN, LOGIN, LOGOUT, CAM, GPS, LOCK, UNLOCK, BATTERY, DANGER, FILE_DEL, FILE_LOCA, AUTOLOGIN};
   HashMap<String, DataOutputStream> clients;
   public static Sign get_Sign(String str){      
      Sign sign=null;      
      return sign;
   }
   
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
   
    sserver() {
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

    public class Connections extends Thread{
        Socket socket;
        DataInputStream in;
        DataOutputStream out;
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
                      if(ss.Chklogin(id,msg)){
                          System.out.println("success");
                          //register_sock(clienthw, id, socket);
                          sendMsg(clienthw, id,sndmsg+"��� Login" );
                          sndmsg= "LOGIN"+ ">*<";
                          responseMsg(clienthw, id, sndmsg+"OK");
                          sndmsg= "FILE_LOCA"+ ">*<";
                          responseMsg(clienthw, id, sndmsg+"C:\\Users\\����\\Desktop\\��α�");
                          //responseMsg(clienthw, id, sndmsg+ss.loginbean.getfolder());
                      }
                      else{
                         System.out.println("Failed");
                         sndmsg= "LOGIN"+ ">*<";
                         responseMsg(clienthw, id, sndmsg+"NO");
                         clients.remove(clienthw+"><"+id);
                         
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
             case FILE_LOCA:
                sendMsg(clienthw, id, sndmsg+msg);
                break;
             case LOCK:
                sendMsg(clienthw, id, sndmsg+msg);
                break;
             case UNLOCK:
                sendMsg(clienthw, id, sndmsg+msg);
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
             case AUTOLOGIN :
                register_sock(clienthw, id, socket);
                System.out.println(id+"�ڵ��α��� ����");
                sendMsg(clienthw, id,sndmsg+"��� ����" );
                   responseMsg(clienthw, id, sndmsg+"OK");
                   sndmsg= "FILE_LOCA"+ ">*<";
                   responseMsg(clienthw, id, sndmsg+"C:\\Users\\����\\Desktop\\��α�");
                
                break;
             }
          
          return true;
       }
        void cam_save(){//��ó: http://boxfoxs.tistory.com/209 [�ڽ����� - BoxFox]
           FileOutputStream fout = null;
         //in = client.getInputStream();                //Ŭ���̾�Ʈ�� ���� ����Ʈ ������ �Է��� �޴� InputStream�� ���� �����մϴ�.
           DataInputStream din = new DataInputStream(in);  //InputStream�� �̿��� ������ ������ �Է��� �޴� DataInputStream�� �����մϴ�.
           while(true){
               int data;
            try {
               data = din.readInt();
                       //Int�� �����͸� ���۹޽��ϴ�.
                  String filename = din.readUTF();            //String�� �����͸� ���۹޾� filename(������ �̸����� ����)�� �����մϴ�.
                  File file = new File(filename+".jpg");             //�Է¹��� File�� �̸����� �����Ͽ� �����մϴ�.
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
           }
        }
        void responseMsg(String clienthw, String sendid, String msg){
           Iterator it = clients.keySet().iterator();  
           
            try {
                while (it.hasNext()) {
                   String tempid = (String) it.next();
                   if(tempid.equals(clienthw+"><"+sendid)){
                       DataOutputStream out = (DataOutputStream) clients
                               .get(tempid);
                       System.out.println(tempid);
                       System.out.println(msg);
                       out.writeUTF(msg);                
                    } 
                 }// while
            } 
            catch (IOException e) {
            }
        }
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
                       System.out.println(tempid);
                       out.writeUTF(msg);
                    } 
                 }// while
            } 
            catch (IOException e) {
            }
        } // sendMsg
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
               
               /* name = in.readUTF();
                sendToAll("#" + name + "���� �����̽��ϴ�.");
                clients.put(name, out);
                System.out.println("���� ���������� ���� " 
                        + clients.size() + "�Դϴ�.");
                while (in != null) {
                    sendToAll(in.readUTF());
                }*/
                while(in != null){
                   String raw = in.readUTF();
                    System.out.println("Ŭ���̾�Ʈ�κ��� ���۹��� ���ڿ� : "+raw);
                    str= new StringTokenizer(raw,">*<");
                    for(int i=0; i<str.countTokens();i++){
                          signstr=str.nextToken();
                          clienthw=str.nextToken();
                         id=str.nextToken();
                          try{
                             msg=str.nextToken();
                          }catch(Exception e){}
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
/*
   public static void main(String[] args){
      try{
            ServerSocket server = new ServerSocket(10001);
            System.out.println("Wating Connect ..");
  
                    Socket sock = server.accept();
                    
                    InetAddress  inetaddr = sock.getInetAddress();  
                    System.out.println(inetaddr.getHostAddress()+ " �κ��� �����߽��ϴ�.");
            System.out.println(sock.getInetAddress()+ " �κ��� �����߽��ϴ�.");
            new network().getMac(sock);
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
           
            PrintWriter print = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringTokenizer str;
            String id=null;
            String signstr=null;
            Sign sign1=null;
            String msg=null;
            Login ss= new Login();
            while((line = br.readLine()) != null){
                   System.out.println("Ŭ���̾�Ʈ�κ��� ���۹��� ���ڿ� : "+line);
                   str= new StringTokenizer(line,">*<");
                   for(int i=0; i<str.countTokens();i++){
                         signstr=str.nextToken();
                        id=str.nextToken();
                         msg=str.nextToken();
                         sign1= get_Sign(signstr);
                         signal_mng(sign1, id, msg);
                    
                   }
                   System.out.println(id+ " ���ӽõ�");
                   if(ss.Chklogin(id,msg)){
                      System.out.println("success");
                      print.println(id + " LoginSuccess");
                      System.out.println(id + " LoginSuccess");
                      print.flush();
                   } 
                   else
                      System.out.println("failed");
            }
            print.close();
            br.close();
            sock.close();
            } catch(Exception e){
               System.out.println(e);
               }
      }
}*/

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
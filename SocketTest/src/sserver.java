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
            System.out.println("서버가 시작되었습니다.");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "에서 접속하였습니다.");
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
                     System.out.println(id+ " 가입완료");
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
                   System.out.print(id+ " 접속시도 - ");
                      if(ss.Chklogin(id,msg)){
                          System.out.println("success");
                          //register_sock(clienthw, id, socket);
                          sendMsg(clienthw, id,sndmsg+"상대 Login" );
                          sndmsg= "LOGIN"+ ">*<";
                          responseMsg(clienthw, id, sndmsg+"OK");
                          sndmsg= "FILE_LOCA"+ ">*<";
                          responseMsg(clienthw, id, sndmsg+"C:\\Users\\지혜\\Desktop\\뜸부기");
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
                sendMsg(clienthw,id,"상대 접속 종료");
                     clients.remove(clienthw+"><"+id);
                   System.out.println("[" + socket.getInetAddress() + ":"
                           + socket.getPort() + "]" 
                           + "에서( HW : "+ clienthw+"/ ID : "+id+")접속을 종료하였습니다.");
                   System.out.println("현재 서버접속자 수는 " 
                           + clients.size() + "입니다.");
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
                System.out.println(id+"자동로그인 접속");
                sendMsg(clienthw, id,sndmsg+"상대 접속" );
                   responseMsg(clienthw, id, sndmsg+"OK");
                   sndmsg= "FILE_LOCA"+ ">*<";
                   responseMsg(clienthw, id, sndmsg+"C:\\Users\\지혜\\Desktop\\뜸부기");
                
                break;
             }
          
          return true;
       }
        void cam_save(){//출처: http://boxfoxs.tistory.com/209 [박스여우 - BoxFox]
           FileOutputStream fout = null;
         //in = client.getInputStream();                //클라이언트로 부터 바이트 단위로 입력을 받는 InputStream을 얻어와 개통합니다.
           DataInputStream din = new DataInputStream(in);  //InputStream을 이용해 데이터 단위로 입력을 받는 DataInputStream을 개통합니다.
           while(true){
               int data;
            try {
               data = din.readInt();
                       //Int형 데이터를 전송받습니다.
                  String filename = din.readUTF();            //String형 데이터를 전송받아 filename(파일의 이름으로 쓰일)에 저장합니다.
                  File file = new File(filename+".jpg");             //입력받은 File의 이름으로 복사하여 생성합니다.
                  fout = new FileOutputStream(file);           //생성한 파일을 클라이언트로부터 전송받아 완성시키는 FileOutputStream을 개통합니다.
                  int datas = data;                            //전송횟수, 용량을 측정하는 변수입니다.
                  byte[] buffer = new byte[1024];        //바이트단위로 임시저장하는 버퍼를 생성합니다.
                  int len;                               //전송할 데이터의 길이를 측정하는 변수입니다.                  
                  for(;data>0;data--){                   //전송받은 data의 횟수만큼 전송받아서 FileOutputStream을 이용하여 File을 완성시킵니다.
                      len = in.read(buffer);
                      fout.write(buffer,0,len);
                  }                 
                  System.out.println("약: "+datas+" kbps");
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
                sendToAll("#" + name + "님이 들어오셨습니다.");
                clients.put(name, out);
                System.out.println("현재 서버접속자 수는 " 
                        + clients.size() + "입니다.");
                while (in != null) {
                    sendToAll(in.readUTF());
                }*/
                while(in != null){
                   String raw = in.readUTF();
                    System.out.println("클라이언트로부터 전송받은 문자열 : "+raw);
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
               sendMsg(clienthw,id,"상대 접속 종료");
               clients.remove(clienthw+"><"+id);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" 
                        + "에서( HW : "+ clienthw+"/ ID : "+id+")접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는 " 
                        + clients.size() + "입니다.");
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
                    System.out.println(inetaddr.getHostAddress()+ " 로부터 접속했습니다.");
            System.out.println(sock.getInetAddress()+ " 로부터 접속했습니다.");
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
                   System.out.println("클라이언트로부터 전송받은 문자열 : "+line);
                   str= new StringTokenizer(line,">*<");
                   for(int i=0; i<str.countTokens();i++){
                         signstr=str.nextToken();
                        id=str.nextToken();
                         msg=str.nextToken();
                         sign1= get_Sign(signstr);
                         signal_mng(sign1, id, msg);
                    
                   }
                   System.out.println(id+ " 접속시도");
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

// 1. 10001번 포트에서 동작하는 ServerSocket을 생성
// 2. ServerSocket의 accept() 메소드를 실행해서 클라이언트의 접속을 대기
// : 클라이언트가 접속할 경우 accept() 메소드는 Socket 객체를 반환
//3. 반환받은 Socket으로부터 InputStream과 OutputStream을 구함
/*
BufferedReader keyboard =
        new BufferedReader(new InputStreamReader(System.in));
*/
//4. InputStream은 BufferedReader 형식으로 변환
//    OutputStream은 PrintWriter 형식으로 변환
//5. BufferedReader의 readLine() 메소드를 이용해
//   클라이언트가 보내는 문자열 한 줄을 읽어들임
/*line = br.readLine();
System.out.println("클라이언트로부터 전송받은 문자열 : "+line);
*/
// 6. PrintWriter의 println을 이용해 다시 클라이언트로 전송
// 6. IO 객체와 소켓의 close() 메소드 호출
//[출처] JAVA 소켓 프로그래밍 (TCP) (TCP/IP 길라잡이) |작성자 Lisa
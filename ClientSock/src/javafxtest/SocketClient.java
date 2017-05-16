package javafxtest;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class SocketClient {
	
   enum Sign{LOGIN, CAM, GPS, LOCK, UNLOCK, BATTERY, DANGER, FILE_DEL, JOIN, LOGOUT, AUTOLOGIN, FILE_LOCA};
   static Socket sock;
   static BufferedReader keyboard;
   static DataInputStream in;
   static DataOutputStream out;
   static PrintWriter pw;
   static BufferedReader br;
   public static IDBean loginbean;
   
   SocketClient(){
	   c_connect("127.0.0.1", 10001);
   }
   
   public static void c_connect(String ipad, int portnum){
	   try{
		   sock = new Socket(ipad, portnum);
		   out = new DataOutputStream(sock.getOutputStream());
           in = new DataInputStream(sock.getInputStream());
          // receiveString();
          
	   }catch(Exception e){
		   System.out.println(e);
	   }
   }

   public static void sendString(String str){
	   try{
		   System.out.println(str);
		   out.writeUTF(str);
		   /*pw = new PrintWriter(new OutputStreamWriter(out));
           pw.println(str);
           pw.flush();*/
          // pw.close();
         //  sock.close();   
         }catch(Exception e){
                System.out.println(e);
         }
   }
   public static void sendImage(String filepath){
	   try{
		   System.out.println(filepath);
		   FileInputStream fin;
		   long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss");
			String str = dayTime.format(new Date(time));
			//출처: http://ra2kstar.tistory.com/123 [초보개발자 이야기.]
		   String filename = str+".PNG";
		   fin = new FileInputStream(new File(filepath));
		   byte[] buffer = new byte[1024];        //바이트단위로 임시저장하는 버퍼를 생성합니다.
	        int len;                               //전송할 데이터의 길이를 측정하는 변수입니다.
	        int data=0;                            //전송횟수, 용량을 측정하는 변수입니다.
	        
	        while((len = fin.read(buffer))>0){     //FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
	            data++;                        //데이터의 양을 측정합니다.
	        }
	        
	        int datas = data;                      //아래 for문을 통해 data가 0이되기때문에 임시저장한다.
	 
	        fin.close();
	        fin = new FileInputStream(filepath);   //FileInputStream이 만료되었으니 새롭게 개통합니다.
	        out.writeInt(data);                   //데이터 전송횟수를 서버에 전송하고,
	        out.writeUTF(filename);
	        
	         len = 0;
	        
	        for(;data>0;data--){                   //데이터를 읽어올 횟수만큼 FileInputStream에서 파일의 내용을 읽어옵니다.
	            len = fin.read(buffer);        //FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
	            out.write(buffer,0,len);       //서버에게 파일의 정보(1kbyte만큼보내고, 그 길이를 보냅니다.
	        }
	        System.out.println("약 "+datas+" kbyte");
         }catch(Exception e){
                System.out.println(e);
         } // 출처: http://boxfoxs.tistory.com/209 [박스여우 - BoxFox]
   }
   public static void receiveString(){
	   try{
		   String str = null;
		   if(in != null){
			    str= in.readUTF();
		   }
		   //String str = br.readLine();
		   System.out.println(str);
		   StringTokenizer stoken=new StringTokenizer(str,">*<");
		   int countToken = stoken.countTokens();
			
		   String data = stoken.nextToken();
		   Sign signdata=Sign.valueOf(data);
	   
		   switch(signdata){
		   		case JOIN:
		   			str=stoken.nextToken();
		   			Join join= new Join();
		   			join.OkJoin(str);
		   			break;
		   		case LOGIN:
		   			str=stoken.nextToken();
		   			Login login = new Login();
		   			login.OkLogin(str);
		   			break;
		   		case FILE_LOCA:
		   			str=stoken.nextToken();
		   			loginbean.setfolder(str);
		   			break;
		   		case LOCK:
		   			str=stoken.nextToken();
		   			loginbean.setlock(0);
		   			break;
		   		case UNLOCK:
		   			str=stoken.nextToken();
		   			loginbean.setlock(1);
		   			break;
		   		case AUTOLOGIN:
		   			str=stoken.nextToken();
		   			Login llogin = new Login();
		   			llogin.OkLogin(str);
		   			break;	
		   		
		   }
		   
         //  br.close();
          // sock.close();
         }catch(Exception e){
                System.out.println(e);
         }
   }
}


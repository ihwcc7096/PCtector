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
			//��ó: http://ra2kstar.tistory.com/123 [�ʺ������� �̾߱�.]
		   String filename = str+".PNG";
		   fin = new FileInputStream(new File(filepath));
		   byte[] buffer = new byte[1024];        //����Ʈ������ �ӽ������ϴ� ���۸� �����մϴ�.
	        int len;                               //������ �������� ���̸� �����ϴ� �����Դϴ�.
	        int data=0;                            //����Ƚ��, �뷮�� �����ϴ� �����Դϴ�.
	        
	        while((len = fin.read(buffer))>0){     //FileInputStream�� ���� ���Ͽ��� �Է¹��� �����͸� ���ۿ� �ӽ������ϰ� �� ���̸� �����մϴ�.
	            data++;                        //�������� ���� �����մϴ�.
	        }
	        
	        int datas = data;                      //�Ʒ� for���� ���� data�� 0�̵Ǳ⶧���� �ӽ������Ѵ�.
	 
	        fin.close();
	        fin = new FileInputStream(filepath);   //FileInputStream�� ����Ǿ����� ���Ӱ� �����մϴ�.
	        out.writeInt(data);                   //������ ����Ƚ���� ������ �����ϰ�,
	        out.writeUTF(filename);
	        
	         len = 0;
	        
	        for(;data>0;data--){                   //�����͸� �о�� Ƚ����ŭ FileInputStream���� ������ ������ �о�ɴϴ�.
	            len = fin.read(buffer);        //FileInputStream�� ���� ���Ͽ��� �Է¹��� �����͸� ���ۿ� �ӽ������ϰ� �� ���̸� �����մϴ�.
	            out.write(buffer,0,len);       //�������� ������ ����(1kbyte��ŭ������, �� ���̸� �����ϴ�.
	        }
	        System.out.println("�� "+datas+" kbyte");
         }catch(Exception e){
                System.out.println(e);
         } // ��ó: http://boxfoxs.tistory.com/209 [�ڽ����� - BoxFox]
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




import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {
	public static IDBean loginbean= new IDBean();

	public static boolean Chklogin(String id, String pw){ 
		//로그인 체크
		IDDAO idchk=new IDDAO();
		loginbean.setid(id);
		loginbean.setpw(pw);
		
		if (idchk.checkUser(loginbean)){ //로그인 성공
			loginbean.setautologin(1);
			return true;

		}
		else { //로그인 실패
			return false;
		}
	}
	public static boolean SetFolder(String id,String folder){
		IDDAO setf=new IDDAO();
		loginbean.setid(id);
		loginbean.setfolder(folder);
		
		if(setf.decideFolder())
			return true;
		else 
			return false;
	}
	public static void getFolder(String id){
		IDDAO setf=new IDDAO();
		loginbean.setid(id);
		
		setf.receivefolder(loginbean);
		
	}
}

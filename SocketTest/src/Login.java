

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {
	public static IDBean loginbean= new IDBean();

	public static boolean Chklogin(String id, String pw){ 
		//�α��� üũ
		IDDAO idchk=new IDDAO();
		loginbean.setid(id);
		loginbean.setpw(pw);
		
		if (idchk.checkUser(loginbean)){ //�α��� ����
			loginbean.setautologin(1);
			return true;

		}
		else { //�α��� ����
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

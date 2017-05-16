package javafxtest;

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {
	public static IDBean loginbean = new IDBean();
	public static SocketClient sc;
	
	public void Chklogin(String id, String pw){ //로그인 체크
		loginbean.setid(id);
		loginbean.setpw(pw);
		sc.sendString("LOGIN>*<PC>*<"+id+">*<"+pw);
		sc.receiveString();
		
	}
	public void OkLogin(String str){
		if (str.equals("OK")){ //로그인 성공
			AutoLogin auto=new AutoLogin();
			auto.setRegistrylogin(loginbean.getid());
			loginbean.setautologin(1);
			loginbean.setlogin(1);
			JOptionPane.showMessageDialog(null,"로그인되었습니다.");

		}
		else { //로그인 실패
			loginbean.setlogin(0);
			JOptionPane.showMessageDialog(null,"로그인이 되지 않았슴니다.");
		}
	}
}

package javafxtest;

import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {
	public static IDBean loginbean = new IDBean();
	public static SocketClient sc;
	
	public void Chklogin(String id, String pw){ //�α��� üũ
		loginbean.setid(id);
		loginbean.setpw(pw);
		sc.sendString("LOGIN>*<PC>*<"+id+">*<"+pw);
		sc.receiveString();
		
	}
	public void OkLogin(String str){
		if (str.equals("OK")){ //�α��� ����
			AutoLogin auto=new AutoLogin();
			auto.setRegistrylogin(loginbean.getid());
			loginbean.setautologin(1);
			loginbean.setlogin(1);
			JOptionPane.showMessageDialog(null,"�α��εǾ����ϴ�.");

		}
		else { //�α��� ����
			loginbean.setlogin(0);
			JOptionPane.showMessageDialog(null,"�α����� ���� �ʾҽ��ϴ�.");
		}
	}
}

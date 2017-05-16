package javafxtest;

import javax.swing.JOptionPane;

public class Join {
	public static SocketClient sc;
	public static IDBean bean;
	public void ChkJoin(String id, String pw){ //회원가입 체크
		bean.setjoin(0);
		sc.sendString("JOIN>*<PC>*<"+id+">*<"+pw);
		sc.receiveString();
			
	}
	public void OkJoin(String str){
		System.out.println(str);
		if (str.equals("OK")){ //회원가입 성공
			JOptionPane.showMessageDialog(null,"회원가입되었습니다.");
			bean.setjoin(1);
		}
		else { //회원가입 실패
			JOptionPane.showMessageDialog(null,"회원가입이 되지 않았슴니다.");
			bean.setjoin(0);
		}
	}

}

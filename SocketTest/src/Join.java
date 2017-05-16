

import javax.swing.JOptionPane;

public class Join {
	public static IDBean loginbean = new IDBean();
	public int ChkJoin(String id, String pw){ //회원가입 체크
		IDDAO idchk=new IDDAO();
		loginbean.setid(id);
		loginbean.setpw(pw);
		
		if (idchk.JoinUser(loginbean)){ //회원가입 성공
			JOptionPane.showMessageDialog(null,"회원가입되었습니다.");
			return 1;

		}
		else { //회원가입 실패
			JOptionPane.showMessageDialog(null,"회원가입이 되지 않았슴니다.");
			return 0;
		}
	}

}



import javax.swing.JOptionPane;

public class Join {
	public static IDBean loginbean = new IDBean();
	public int ChkJoin(String id, String pw){ //ȸ������ üũ
		IDDAO idchk=new IDDAO();
		loginbean.setid(id);
		loginbean.setpw(pw);
		
		if (idchk.JoinUser(loginbean)){ //ȸ������ ����
			JOptionPane.showMessageDialog(null,"ȸ�����ԵǾ����ϴ�.");
			return 1;

		}
		else { //ȸ������ ����
			JOptionPane.showMessageDialog(null,"ȸ�������� ���� �ʾҽ��ϴ�.");
			return 0;
		}
	}

}

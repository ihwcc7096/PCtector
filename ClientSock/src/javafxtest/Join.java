package javafxtest;

import javax.swing.JOptionPane;

public class Join {
	public static SocketClient sc;
	public static IDBean bean;
	public void ChkJoin(String id, String pw){ //ȸ������ üũ
		bean.setjoin(0);
		sc.sendString("JOIN>*<PC>*<"+id+">*<"+pw);
		sc.receiveString();
			
	}
	public void OkJoin(String str){
		System.out.println(str);
		if (str.equals("OK")){ //ȸ������ ����
			JOptionPane.showMessageDialog(null,"ȸ�����ԵǾ����ϴ�.");
			bean.setjoin(1);
		}
		else { //ȸ������ ����
			JOptionPane.showMessageDialog(null,"ȸ�������� ���� �ʾҽ��ϴ�.");
			bean.setjoin(0);
		}
	}

}

package javafxtest;

public class IDBean {
	private static String id=null;
	private static String pw=null;
	private static int autologin=0;
	private static String folder=null;
	private static String n_ip=null;
	private static String h_ip=null;
	private static int join=0;
	private static int login=0;
	private static int lock=0;
	

	public static String getid() {
		return id;
	}
	public static String getpw() {
		return pw;
	}
	public static int getautologin() {
		return autologin;
	}
	public static int getlock() {
		return lock;
	}
	public static int getjoin() {
		return join;
	}
	public static int getlogin() {
		return login;
	}
	public static String getfolder() {
		return folder;
	}
	public String getn_ip() {
		return n_ip;
	}
	public String geth_ip() {
		return h_ip;
	}
	public static void setid(String id) {
		IDBean.id=id;
	}
	public static void setpw(String pw) {
		IDBean.pw=pw;
	}
	public void setautologin(int autologin) {
		this.autologin=autologin;
	}
	public void setlock(int lock) {
		this.lock=lock;
	}
	public static void setfolder(String folder) {
		IDBean.folder=folder;
	}
	public void setn_ip(String n_ip) {
		this.n_ip=n_ip;
	}
	public void seth_ip(String h_ip) {
		this.h_ip=h_ip;
	}
	public static void setjoin(int join) {
		IDBean.join=join;
	}
	public static void setlogin(int login) {
		IDBean.login=login;
	}

}

package javafxtest;

import java.io.File;

public class DeleteFolder {
	public static IDBean bean;
	public void deleteF(){
		File targetFolder=new File(bean.getfolder());
		deleteFolder(targetFolder);
	}
	public boolean deleteFolder(File targetFolder){
	       File[] childFile = targetFolder.listFiles();
	       boolean confirm = false;
	       int size = childFile.length;
	  
	       if (size > 0) {
	    	   
	           for (int i = 0; i < size; i++) {
	               if (childFile[i].isFile()) {
	                   confirm = childFile[i].delete();
	                   System.out.println(childFile[i]+":"+confirm + " ����");
	               } else {
	                   deleteFolder(childFile[i]);
	               }
	           }
	       }
	       targetFolder.delete();

	       System.out.println(targetFolder + " ���������ʻ���");
	       System.out.println(targetFolder+":"+confirm + " ����");
	       return (!targetFolder.exists());
	}   
}
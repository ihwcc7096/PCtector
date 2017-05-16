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
	                   System.out.println(childFile[i]+":"+confirm + " 삭제");
	               } else {
	                   deleteFolder(childFile[i]);
	               }
	           }
	       }
	       targetFolder.delete();

	       System.out.println(targetFolder + " 폴더삭제됨삭제");
	       System.out.println(targetFolder+":"+confirm + " 삭제");
	       return (!targetFolder.exists());
	}   
}
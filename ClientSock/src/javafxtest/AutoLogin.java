package javafxtest;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class AutoLogin {

    public static String getRegistryValue(){
    	String regPath = "Software";
    	String regName = "PCtector";
    	try{
    		Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, regPath+"\\PCtector", "id");
    		return Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, regPath
    				+"\\PCtector", "id");
    	}catch (Exception e) {
		}
    	return null;
    }
    public static boolean setRegistrylogin(String id){
    	String regPath = "Software";
    	String regName = "PCtector";
    	String valueName="id";
    	String regValue = id;
    	
    	Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, regPath, regName);
    	Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, regPath+"\\PCtector", valueName, regValue);

    	 return true;
    }
    public static void delRegistrylogin(String id){
    	String regPath = "Software";
    	String regName = "PCtector";
    	String valueName="id";
    	
    	Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, regPath+"\\PCtector", valueName);
    }
    public static boolean setRegistryrun(){
    	String regPath = "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
      	String regName = "PCtector";
      	String regValue = "C:\\Program Files\\PCtector.exe";
      	
      	Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, regPath+"\\PCtector", 
      			regName, regValue);
      	
    	return true;
    }
}

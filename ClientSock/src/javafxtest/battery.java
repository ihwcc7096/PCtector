package javafxtest;

   import java.util.ArrayList;
   import java.util.List;

   import com.sun.jna.Native;
   import com.sun.jna.Structure;
   import com.sun.jna.win32.StdCallLibrary;


public class battery {
	public static SocketClient sc;
	public static IDBean bean;
      public interface Kernel32 extends StdCallLibrary {
       public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);
       /**
        * @see http://msdn2.microsoft.com/en-us/library/aa373232.aspx
        */
       public class SYSTEM_POWER_STATUS extends Structure {
           public byte ACLineStatus;
           public byte BatteryFlag;
           public byte BatteryLifePercent;
           public byte Reserved1;
           public int BatteryLifeTime;
           public int BatteryFullLifeTime;

           @Override
           protected List<String> getFieldOrder() {
               ArrayList<String> fields = new ArrayList<String>();
               fields.add("ACLineStatus");
               fields.add("BatteryFlag");
               fields.add("BatteryLifePercent");
               fields.add("Reserved1");
               fields.add("BatteryLifeTime");
               fields.add("BatteryFullLifeTime");
               return fields;
           }
           /**
            * The AC power status
            */
           public String getACLineStatusString() {
               switch (ACLineStatus) {
                   case (0): return "Offline";
                   case (1): return "Online";
                   default: return "Unknown";
               }
           }
           /**
            * The battery charge status
            */
           public String getBatteryFlagString() {
               switch (BatteryFlag) {
                   case (1): return "High, more than 66 percent";
                   case (2): return "Low, less than 33 percent";
                   case (4): return "Critical, less than five percent";
                   case (8): return "Charging";
                   case ((byte) 128): return "No system battery";
                   default: return "Unknown";
               }
           }
           /**
            * The percentage of full battery charge remaining
            */
           public String getBatteryLifePercent() {
               return (BatteryLifePercent == (byte) 255) ? "Unknown" : BatteryLifePercent + "%";
           }
           /**
            * The number of seconds of battery life remaining
            */
           public String getBatteryLifeTime() {
               if(BatteryLifeTime == -1) 
                   return "Unknown";
                else{
                   int hours =0;
                   int minutes =0;
                   int seconds = BatteryLifeTime;
                   if(seconds < 60)
                      return Integer.toString(seconds);
                   else {
                      minutes =seconds/60;
                      seconds %= 60;
                      
                      if(minutes < 60)
                         return minutes+" minutes "+seconds +" seconds";
                      else{
                         hours =minutes/60;
                         minutes %= 60;
                         return hours+" hours "+ minutes+" minutes "+seconds +" seconds";
                      }
                   }   
                } 
           }
           /**
            * The number of seconds of battery life when at full charge
            */
           public String getBatteryFullLifeTime() {
                if(BatteryFullLifeTime == -1) 
                   return "Unknown";
                else{
                   int hours =0;
                   int minutes =0;
                   int seconds = BatteryLifeTime;
                   if(seconds < 60)
                      return Integer.toString(seconds);
                   else {
                      minutes =seconds/60;
                      seconds %= 60;
                      
                      if(minutes < 60)
                         return minutes+" minutes "+seconds +" seconds";
                      else{
                         hours =minutes/60;
                         minutes %= 60;
                         return hours+" hours "+ minutes+" minutes "+seconds +" seconds";
                      }
                   }   
                }  
           }
           @Override
           public String toString() {
               StringBuilder sb = new StringBuilder();
               sb.append("전원연결여부: " + getACLineStatusString() + "\n");
               sb.append("남은배터리: " + getBatteryLifePercent() + "\n");
               sb.append("남은시간: " + getBatteryLifeTime() + "\n");
               sc.sendString("BATTERY>*<PC>*<"+bean.getid()+">*<"+sb);
               return sb.toString();
           }
       }
       /**
        * Fill the structure.
        */
       public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
   }

}
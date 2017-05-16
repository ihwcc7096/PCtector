package javafxtest;

import org.junit.Test;

import com.sun.jna.Native;  // JNA infrastructure
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
 
public class locktest {
      public interface User32 extends StdCallLibrary {
         User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
         public boolean LockWorkStation();
      }
      @Test
        public void testWindowLock() {
               // Create a proxy for user32.dll ...
               final User32 user32 = (User32) Native.loadLibrary("user32", User32.class);
               boolean result =  user32.LockWorkStation();
               System.err.println("result = " + result);
        }
}

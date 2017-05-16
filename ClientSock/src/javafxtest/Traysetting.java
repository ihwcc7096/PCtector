package javafxtest;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Traysetting {
	TrayIcon trayIcon;
	private boolean firstTime=true;
	public void createTrayIcon(final Stage stage)throws Exception{
		if (!SystemTray.isSupported()) {
		      System.out.println("SystemTray is not supported");
		      return;
		    }
			
		    SystemTray tray = SystemTray.getSystemTray();
		    
		  //닫기 눌렀을 때 hide하기
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
		    
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    String imageurl = java.net.URLDecoder.decode(Intro.class.getResource("").getPath()+"../../res/logo.png","UTF-8");
		    Image image = toolkit.getImage(imageurl);
		    
		    
		  //팝업 메뉴 설정
            PopupMenu popup = new PopupMenu();
            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(new ActionListener() {
        	      public void actionPerformed(java.awt.event.ActionEvent e) {
                      Platform.runLater(new Runnable() {
                          public void run() {
                              stage.show();
                          }
                      });
        	      }
            });
            popup.add(showItem);
            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                  }
                });
            popup.add(closeItem);
            
            trayIcon = new TrayIcon(image, "PCtector", popup);
            trayIcon.setImageAutoSize(true);
	
		    tray.add(trayIcon);
	
	}
	public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("PCtector",
                    "PCtector 작동중",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }
    
}


package javafxtest;

import java.awt.TrayIcon;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Intro extends Application{
	TrayIcon trayIcon;
	public static IDBean loginbean = new IDBean();
	public static SocketClient sc = new SocketClient();

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("fxmlIntro.fxml"));
		
		// Tray 부분
		Traysetting test=new Traysetting();
		test.createTrayIcon(primaryStage);
	    Platform.setImplicitExit(false);
	    Scene scene2 = new Scene(new Group(), 800, 600);
	    primaryStage.setScene(scene2);
	    primaryStage.show();
		
	    // window 설정, fxmlIntro
		Scene scene = new Scene(root);
		primaryStage.setTitle("PCtector");
		Image image= new Image("file:res/logo.png");
		primaryStage.getIcons().add(image);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		AutoLogin auto= new AutoLogin();
		Parent changexml;
		if(auto.getRegistryValue()!=null){
			loginbean.setid(auto.getRegistryValue());
			sc.sendString("AUTO>*<PC>*<"+loginbean.getid()+">*<OK");// 추가
			changexml=FXMLLoader.load(getClass().getResource("fxmlUI.fxml"));
		}
		else{
		//fxmlLogin 애니메이션 이동
			changexml=FXMLLoader.load(getClass().getResource("fxmlLogin.fxml"));
		}
		StackPane sp = (StackPane)scene.getRoot();
		sp.getChildren().clear();
		sp.getChildren().add(changexml);
		changexml.setTranslateX(200);
		
		Timeline tl = new Timeline();
		KeyValue kv = new KeyValue(changexml.translateXProperty(), 0);
		KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
		tl.getKeyFrames().add(kf);
		tl.play();
		
	}
	public static void main(String[] args){
		launch(args);
	}
	
	

}

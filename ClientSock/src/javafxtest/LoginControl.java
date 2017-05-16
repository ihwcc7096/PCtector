package javafxtest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LoginControl implements Initializable{
	@FXML private Button loginbutton;
	@FXML private Button joinbutton;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	public static IDBean bean;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginbutton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				Login login=new Login();
				login.Chklogin(id.getText(),pw.getText());
				
				//로그인 성공시 페이지 이동 (애니메이션이동)
				if(bean.getlogin()==1){			
					try{
						Parent root1=FXMLLoader.load(getClass().getResource("fxmlUI.fxml"));
						StackPane sp = (StackPane)loginbutton.getScene().getRoot();
						sp.getChildren().clear();
						sp.getChildren().add(root1);
						root1.setTranslateX(200);
						
						Timeline tl = new Timeline();
						KeyValue kv = new KeyValue(root1.translateXProperty(), 0);
						KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
						tl.getKeyFrames().add(kf);
						tl.play();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		joinbutton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){		
					try{
						Parent root1=FXMLLoader.load(getClass().getResource("fxmlJoin.fxml"));
						StackPane sp = (StackPane)joinbutton.getScene().getRoot();
						sp.getChildren().clear();
						sp.getChildren().add(root1);
						root1.setTranslateX(200);
						
						Timeline tl = new Timeline();
						KeyValue kv = new KeyValue(root1.translateXProperty(), 0);
						KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
						tl.getKeyFrames().add(kf);
						tl.play();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
}

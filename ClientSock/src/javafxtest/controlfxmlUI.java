package javafxtest;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafxtest.battery.Kernel32;

public class controlfxmlUI implements Initializable{
	@FXML private ImageView ibutton;
	@FXML private Button fsearch;
	@FXML private Button image;
	@FXML private Button loginbutton;
	@FXML private Button logoutbutton;
	@FXML private Label deletefloca;
	@FXML private Label sendimage;
	@FXML private Label batteryinfo;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private Tab etc;
	private int flag=0;
	public static IDBean loginbean;
	public static SocketClient sc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
		
		System.out.println(IDBean.getfolder());
		if(IDBean.getfolder()!="")
			deletefloca.setText(IDBean.getfolder());
		if(loginbean.getlock()==1)
			flag=1;
		
		ibutton.setOnMouseClicked(new EventHandler(){
			public void handle(Event event){
				if(flag==0){
					Image image= new Image("file:res/pclock2.png");
					sc.sendString("LOCK>*<PC>*<"+loginbean.getid()+">*<OK");//추가
					ibutton.setImage(image);
					flag=1;
				}
				else{
					Image image= new Image("file:res/pclock.png");
					sc.sendString("UNLOCK>*<PC>*<"+loginbean.getid()+">*<OK");//추가
					ibutton.setImage(image);
					loginbean.setlock(1);
					flag=0;
				}
			}
		});
		
		fsearch.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				DirectoryChooser dc = new DirectoryChooser();
				File selectedDc = dc.showDialog(null);
				String selectedDcPath = selectedDc.getPath();
				IDBean.setfolder(selectedDcPath);
				sc.sendString("FILE_LOCA>*<PC>*<"+loginbean.getid()+">*<"+IDBean.getfolder());
				deletefloca.setText(selectedDcPath);
				
			}
		});
		image.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				
				FileChooser fc = new FileChooser();
				File selectedFc = fc.showOpenDialog(null);
				
				sendimage.setText(selectedFc.getPath());
				sc.sendString("CAM>*<PC>*<"+loginbean.getid()+">*<"+sendimage.getText());
				sc.sendImage(sendimage.getText());
			
			}
		});
		logoutbutton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				AutoLogin auto=new AutoLogin();
				auto.delRegistrylogin(loginbean.getid());
				IDBean.setlogin(0);
				IDBean.setjoin(0);
				
				try{
					sc.sendString("LOGOUT>*<PC>*<"+loginbean.getid()+">*<OK");//추가
					Parent root1=FXMLLoader.load(getClass().getResource("fxmlLogin.fxml"));
					StackPane sp = (StackPane)logoutbutton.getScene().getRoot();
					sp.getChildren().clear();
					sp.getChildren().add(root1);
					root1.setTranslateX(200);
					
					Timeline tl = new Timeline();
					KeyValue kv = new KeyValue(root1.translateXProperty(), 0);
					KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
					tl.getKeyFrames().add(kf);
					tl.play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		etc.setOnSelectionChanged(new EventHandler(){
			public void handle(Event event){
				Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
				batteryinfo.setText(batteryStatus.toString());
			}
		});
	}
}

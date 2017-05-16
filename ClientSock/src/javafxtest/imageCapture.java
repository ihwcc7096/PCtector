package javafxtest;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class imageCapture {
	static {
        System.loadLibrary("opencv_java2411");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    
    public static void faceCapture() {
    	int flag=0;
        VideoCapture cap = new VideoCapture(0);
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml"); //얼굴 정면 패턴
        
        if (!cap.isOpened()) { //웹캠 열기 실패
            System.exit(-1);
        }
 
        Mat image = new Mat();       
 
        while (true) {
            cap.read(image); // 웹캠으로부터 이미지를 받아옴
            
            if (!image.empty()) {// 이미지 받아오기 실패

                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(image, faceDetections); //이미지와 얼굴 패턴 비교
                System.out.println(String.format("Detected %s objs", faceDetections.toArray().length));
         
                if(flag==0){ // 처음 웹캡 켜졌을 때 캡쳐
	                String filename = "output.png";
	                System.out.println(String.format("Writing %s", filename));
	                Highgui.imwrite(filename, image);
	                flag=1;
                }
                
                if(faceDetections.toArray().length!=0){ // 얼굴이 인식 되었을 때 캡쳐
                	String filename = "output2.png";
                    System.out.println(String.format("Writing %s", filename));
                    
                    for (Rect rect : faceDetections.toArray()) { //인식된 얼굴을 사각형으로 표시
                        Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0));
                    }
                    Highgui.imwrite(filename, image);
                    flag=2;
                }

                if(flag==2){ //웹캠 종료
                	return;
                }
            } else {
                System.out.println("No captured frame -- camera disconnected");
            }
        }
    }
}

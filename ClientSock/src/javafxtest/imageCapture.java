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
        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml"); //�� ���� ����
        
        if (!cap.isOpened()) { //��ķ ���� ����
            System.exit(-1);
        }
 
        Mat image = new Mat();       
 
        while (true) {
            cap.read(image); // ��ķ���κ��� �̹����� �޾ƿ�
            
            if (!image.empty()) {// �̹��� �޾ƿ��� ����

                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(image, faceDetections); //�̹����� �� ���� ��
                System.out.println(String.format("Detected %s objs", faceDetections.toArray().length));
         
                if(flag==0){ // ó�� ��ĸ ������ �� ĸ��
	                String filename = "output.png";
	                System.out.println(String.format("Writing %s", filename));
	                Highgui.imwrite(filename, image);
	                flag=1;
                }
                
                if(faceDetections.toArray().length!=0){ // ���� �ν� �Ǿ��� �� ĸ��
                	String filename = "output2.png";
                    System.out.println(String.format("Writing %s", filename));
                    
                    for (Rect rect : faceDetections.toArray()) { //�νĵ� ���� �簢������ ǥ��
                        Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0));
                    }
                    Highgui.imwrite(filename, image);
                    flag=2;
                }

                if(flag==2){ //��ķ ����
                	return;
                }
            } else {
                System.out.println("No captured frame -- camera disconnected");
            }
        }
    }
}

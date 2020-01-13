
package autoFunctions;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;

import global.Helper;
import global.TerraBot;
import util.Rect;

public class TerraCV {

    public Helper h = new Helper();
    public TerraBot bot;
    public VuforiaLocalizer vuforia;
    public VuforiaLocalizer.CloseableFrame currentFrame;
    public Image img;
    public Bitmap bm;
    LinearOpMode op;

    public int Accuracy = 7;


    ElapsedTime debug = new ElapsedTime();
    public double time = 0;


    public void init(TerraBot b, LinearOpMode o){
        bot = b;
        op = o;
        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = h.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

       vuforia = ClassFactory.getInstance().createVuforia(parameters);

       Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
       vuforia.setFrameQueueCapacity(1);

    }
    public void takePicture(){
        //1280, 720
        while (op.opModeIsActive() && img == null) {
            try {currentFrame = vuforia.getFrameQueue().take();}catch (InterruptedException e){}
            long numImages = currentFrame.getNumImages();
            for (int i = 0; i < numImages; i++) {
                if (currentFrame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                    img = currentFrame.getImage(i);
                    break;
                }
            }
            if (img != null) {
                bm = vuforia.convertFrameToBitmap(currentFrame);
            }
        }
    }
    public StonePos getStonePos(int bottom, int range){
        debug.reset();
        StonePos pos;
        Rect left = new Rect(0,bottom,426,range);
        Rect middle = new Rect(426,bottom,426,range);
        Rect right = new Rect(853,bottom,426,range);
        double[] values = new double[3];
        values[0] = getAverageOfPixels(left);
        values[1] = getAverageOfPixels(middle);
        values[2] = getAverageOfPixels(right);
        int index = h.findMin(values);
        if(index == 0){
            pos = StonePos.LEFT;
        }else if(index == 1){
            pos = StonePos.MIDDLE;
        }else{
            pos = StonePos.RIGHT;
        }
        time = debug.milliseconds();

        return pos;
    }

    public double getAverageOfPixels(Rect rect){
        double total = 0;
        int x1 = rect.getX1();
        int y1 = rect.getY1();
        int x2 = rect.getX2();
        int y2 = rect.getY2();

        for (int x = x1; x < x2; x+= Accuracy) {
            for (int y = y1; y < y2; y+=Accuracy ) {
                int pix = bm.getPixel(x,y);
                float[] hsv = h.rgbToHSV(pix);
                total += hsv[2];
                if(!op.opModeIsActive()){
                    break;
                }
            }
            if(!op.opModeIsActive()){
                break;
            }
        }
        return total/(rect.getArea());
    }
    public enum StonePos{
        LEFT,
        RIGHT,
        MIDDLE
    }
}
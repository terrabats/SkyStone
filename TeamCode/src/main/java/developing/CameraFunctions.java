
package developing;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import global.Helper;
import global.TerraBot;
import teleFunctions.TeleThread;
import util.CodeSeg;
import util.Rect;

public class CameraFunctions {

    Helper h = new Helper();
    TerraBot bot;
    public VuforiaLocalizer vuforia;
    public VuforiaLocalizer.CloseableFrame currentFrame;
    public Image img;
    public Bitmap bm;
    OpMode op;

    ArrayList<Bitmap> vid = new ArrayList<>();
    TeleThread recThread = new TeleThread();
    TeleThread saveThread = new TeleThread();
    Storage st = new Storage();
    String nameOfVid;

    public int pixel_format = PixelFormat.RGB_565;


    private boolean doNotRecord = false;



    public void init(OpMode o, boolean cameraOn) {
        op = o;
        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters;
        if(cameraOn) {
            parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        }else {
            parameters = new VuforiaLocalizer.Parameters();
        }
        parameters.vuforiaLicenseKey = h.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        //CameraDevice.getInstance().setFlashTorchMode(true);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(1);

    }
    public void disableRecording(){
        doNotRecord = true;
    }

    public Bitmap takePicture() {
        try {
            currentFrame = vuforia.getFrameQueue().take();
        } catch (InterruptedException e) {
        }
        long numImages = currentFrame.getNumImages();
        for (int i = 0; i < numImages; i++) {
            if (currentFrame.getImage(i).getFormat() == pixel_format) {
                img = currentFrame.getImage(i);
                break;
            }
        }
        if (img != null) {
            bm = vuforia.convertFrameToBitmap(currentFrame);
        }
        return bm;
    }

    public void changePixelFormat(int pf){
        pixel_format = pf;
    }

    public void startRec(){
        if(!doNotRecord) {
            recThread.init(new CodeSeg() {
                @Override
                public void run() {
                    vid.add(takePicture());
                }
            });
            Thread t = new Thread(recThread);
            t.start();
        }
    }
    public void stopRecAndSave(String name){
        if(!doNotRecord) {
            nameOfVid = name;
            recThread.stop();
            saveThread.init(new CodeSeg() {
                @Override
                public void run() {
                    st.saveVideo(getVid(), nameOfVid);
                }
            });
            saveThread.changeToOnce();
            Thread t = new Thread(saveThread);
            t.start();
        }
    }
    public ArrayList<Bitmap> getVid(){
        return vid;
    }

    public Bitmap toARGB_8888(Bitmap in){
        return in.copy(Bitmap.Config.ARGB_8888,true);
    }
}
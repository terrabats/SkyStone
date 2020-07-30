
package developing;

import android.graphics.Bitmap;

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

import java.nio.ByteBuffer;
import java.util.ArrayList;

import global.Helper;
import global.TerraBot;
import util.Rect;

public class CameraFunctions {

    Helper h = new Helper();
    TerraBot bot;
    public VuforiaLocalizer vuforia;
    public VuforiaLocalizer.CloseableFrame currentFrame;
    public Image img;
    public Bitmap bm;
    OpMode op;


    public void init(OpMode o) {
        op = o;
        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = h.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        //CameraDevice.getInstance().setFlashTorchMode(true);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(1);

    }

    public Bitmap takePicture() {
        try {
            currentFrame = vuforia.getFrameQueue().take();
        } catch (InterruptedException e) {
        }
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
        return bm;
    }
}
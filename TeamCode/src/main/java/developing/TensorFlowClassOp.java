package developing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import autoFunctions.Odometry;
import global.Helper;
import util.CodeSeg;
import global.TerraBot;
import teleFunctions.TeleThread;
import developing.TensorFlowTest.Recognition;


@TeleOp(name = "TensorFlowClassOp", group = "new")
public class TensorFlowClassOp extends OpMode {

    TensorFlowTest tf = new TensorFlowTest();
    CameraFunctions cf = new CameraFunctions();


    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        tf.init();
        cf.init(this, true);
        cf.changePixelFormat(PixelFormat.RGBA_8888);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        Bitmap in  = cf.takePicture();
        if(in != null) {
            Recognition rec = tf.predictClass(cf.toARGB_8888(in));
            telemetry.addData("Recognition:", rec.toString());
            telemetry.update();
        }
    }

}
package developing;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.IOException;

import autoFunctions.Odometry;
import global.Helper;
import util.CodeSeg;
import global.TerraBot;
import teleFunctions.TeleThread;


@TeleOp(name = "TensorFlowTestOp", group = "new")
public class TensorFlowTestOp extends OpMode {

    TensorFlowTest tf = new TensorFlowTest();
    CameraFunctions cf = new CameraFunctions();

    Bitmap bm = null;



    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        tf.init();
        cf.init(this);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }


    @Override
    public void loop() {

        if(gamepad1.x){
            bm = cf.takePicture();
            if(bm == null){
                telemetry.addData("It ", "Failed");
                telemetry.update();
            }else {
                telemetry.addData("It ", "Worked");
                telemetry.update();
                byte[] arr = cf.bitmapToArray(bm);
            }
        }
        if(gamepad1.y){
            telemetry.addData("Guess: ", tf.predict(10));
            telemetry.update();
        }
    }

}
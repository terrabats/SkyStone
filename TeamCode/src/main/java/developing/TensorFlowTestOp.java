package developing;

import android.graphics.Bitmap;

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


@TeleOp(name = "TensorFlowTestOp", group = "new")
public class TensorFlowTestOp extends OpMode {

    TensorFlowTest tf = new TensorFlowTest();



    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        tf.init();
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.y){
            telemetry.addData("Guess: ", tf.predictNum(10));
            telemetry.update();
        }
    }

}
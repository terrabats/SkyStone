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


@TeleOp(name = "CameraTestOp", group = "new")
public class CameraTestOp extends OpMode {

    CameraFunctions cf = new CameraFunctions();
    TerraBot bot = new TerraBot();
    Helper h = new Helper();



    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        bot.init(hardwareMap);
        cf.init(this);
//        cf.disableRecording();
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }

    @Override
    public void start() {
        cf.startRec();
    }

    @Override
    public void loop() {
        double fpow = h.calcPow(-gamepad1.right_stick_y, 1, bot);
        double spow = h.calcPow(gamepad1.right_stick_x, 0.8, bot);
        double tpow = h.calcPow(gamepad1.left_stick_x, 0.6, bot);
        bot.move(fpow, spow, tpow);
    }

    @Override
    public void stop() {
        cf.stopRecAndSave(Helper.getDataAndTime());
    }
}
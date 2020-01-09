package autoUtil;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import global.CodeSeg;
import global.Helper;
import global.TerraBot;
///////////////////////////////////////////////////////////////////////////////////import autoUtil.TargetDetection.stoneP;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class RobotFunctions {
    TerraBot bot = null;
    Odometry odometry = new Odometry();

    public void init(TerraBot t) {
        bot = t;
        odometry.init(bot);
    }

    public void start(Path path, LinearOpMode o){
        bot.move(0.2,0,0);
        while (o.opModeIsActive() && path.isExecuting()){
            odometry.updateGlobalPosition();
            double[] pows = path.update(odometry);
            if(pows!= null) {
                bot.move(pows[1], pows[0], pows[2]);
            }
            o.telemetry.addData("cl",odometry.cl);
            o.telemetry.addData("cr",odometry.cr);
            o.telemetry.addData("Ty", odometry.ty);
            //o.telemetry.addData("Pos", "{R, L, C, X, Y} = %f, %f, %f, %f, %f", odometry.ticksToInches(odometry.cr),odometry.ticksToInches(odometry.cl),odometry.cc, odometry.tx,odometry.ty);
            o.telemetry.update();
        }
        bot.move(0,0,0);
    }


//    public void turnDeg(double deg, double p) {
//        ElapsedTime total = new ElapsedTime();
//
//        double TPC = 0.03;
//
//        double TW = 0.2;
//
//        pause(0.1);
//        double errH = 0;
//        double errIH = 0;
//        boolean done = false;
//
//        total.reset();
//        while (op.opModeIsActive() && !done) {
//            double changeH = bot.getHeading();
//            errH = changeH+deg;
//            bot.move(0, 0, Range.clip((errH*TPC),-p,p));
//            if(Math.abs(errH) < 10){
//                if(t.seconds() > TW){
//                    done = true;
//                }
//            }else{
//                t.reset();
//            }
//            op.telemetry.addData("bruh", bot.getHeading());
//            op.telemetry.update();
//        }
//        TPC = 0.02;
//        double TIC = 0.03;
//        errIH = 0;
//        done = false;
//
//        double count = 0;
//        double startT = 0;
//        double endT = 0;
//        double diff = 0;
//        total.reset();
//        while (op.opModeIsActive() && !done) {
//            double changeH = bot.getHeading();
//            errH = changeH+deg;
//            errIH += errH*diff;
//            bot.move(0, 0, Range.clip((errH*TPC)+(errIH*TIC),-p,p));
//            if(Math.abs(errH) < 2){
//                if(t.seconds() > TW){
//                    done = true;
//                }
//            }else{
//                t.reset();
//            }
//            op.telemetry.addData("pos", bot.getHeading());
//            op.telemetry.update();
//            if(count % 2 == 0){
//                startT = total.seconds();
//                diff = startT-endT;
//            }else{
//                endT = total.seconds();
//                diff = endT-startT;
//            }
//            count++;
//        }
//        bot.move(0, 0, 0);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public void moveAS(double dis, double p) {
//        ElapsedTime total = new ElapsedTime();
//
//        double TPC = 0.02;
//
//        double TW = 0.05;
//
//        pause(0.1);
//        double errH = 0;
//        double errIH = 0;
//        boolean done = false;
//
//        total.reset();
//        while (op.opModeIsActive() && !done) {
//            double changeH = bot.getArmDis();
//            errH = dis-changeH;
//            bot.move(Range.clip((errH*TPC),-p,p), 0, 0);
//            if(Math.abs(errH) < 5){
//                if(t.seconds() > TW){
//                    done = true;
//                }
//            }else{
//                t.reset();
//            }
//            op.telemetry.addData("posdis", bot.getArmDis());
//            op.telemetry.update();
//        }
//        TPC = 0.03;
//        double TIC = 0.05;
//
//        pause(0.1);
//        errIH = 0;
//        done = false;
//
//        double count = 0;
//        double startT = 0;
//        double endT = 0;
//        double diff = 0;
//        total.reset();
//        while (op.opModeIsActive() && !done) {
//            double changeH = bot.getArmDis();
//            errH = dis-changeH;
//            errIH += errH*diff;
//            bot.move(Range.clip((errH*TPC)+(errIH*TIC),-p,p), 0, 0);
//            if(Math.abs(errH) < 1){
//                if(t.seconds() > TW){
//                    done = true;
//                }
//            }else{
//                t.reset();
//            }
//            op.telemetry.addData("posD", bot.getArmDis());
//            op.telemetry.update();
//            if(count % 2 == 0){
//                startT = total.seconds();
//                diff = startT-endT;
//            }else{
//                endT = total.seconds();
//                diff = endT-startT;
//            }
//            count++;
//        }
//        bot.move(0, 0, 0);
//    }
//
//
//    public void pause(double secs) {
//        t.reset();
//        while (t.seconds() < secs && op.opModeIsActive()) {
//            op.sleep(50);
//            op.idle();
//        }
//    }
//
//    public void moveVuforia(double x, double y, double ang,  double p) {
//        double ph = h.getPos(op,td)[2];
//        turnDeg(ang-ph, p);
////        double py = h.getPos(op, td)[1];
////        double px = h.getPos(op, td)[0];
////        moveDis(0, y - py, p);
////        moveDis(px - x, 0, p);
//
////        pause(0.5);
////        double px = h.getPos(op, td)[0];
////        double py = h.getPos(op, td)[1];
////        pause(0.5);
////        moveDis(0, px-x, p);
////        pause(0.5);
////        moveDis(py-y, 0, p);
//    }
//    public stoneP scanVuforia(){
//        if(h.getPos(op,td) != null){
//            double py = h.getPos(op,td)[1];
//            if(py >0){
//                return stoneP.MIDDLE;
//            }else{
//                return stoneP.LEFT;
//            }
//        }else{
//            return stoneP.RIGHT;
//        }
//    }
//
//    public void calibrateGyro() {
//        op.telemetry.addData("Mode", "calibrating...");
//        op.telemetry.update();
//
//        // make sure the imu gyro is calibrated before continuing
//        while (!op.isStopRequested() && !bot.gyro.isGyroCalibrated()) {
//            op.sleep(50);
//            op.idle();
//        }
//
//        op.telemetry.addData("Mode", "waiting for start");
//        op.telemetry.addData("imu calib status", bot.gyro.getCalibrationStatus().toString());
//        op.telemetry.update();
//    }
//
//    public void telemetryText(String cap, String text) {
//        op.telemetry.addData(cap, text);
//        op.telemetry.update();
//    }
//
//    public void telemetryValue(String cap, double d) {
//        op.telemetry.addData(cap, d);
//        op.telemetry.update();
//    }

}

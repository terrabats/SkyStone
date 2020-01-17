package autoFunctions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Currency;

import global.TerraBot;
import util.CodeSeg;
///////////////////////////////////////////////////////////////////////////////////import autoUtil.TerraCV.stoneP;


public class RobotFunctions {

    TerraBot bot = null;
    LinearOpMode op = null;
    public Odometry odometry = new Odometry();

    ElapsedTime timer = new ElapsedTime();

    public TerraCV.StonePos stonePos;

    public void init(TerraBot t, LinearOpMode o) {
        bot = t;
        op = o;
        odometry.init(bot);
    }

    public void start(Path path, LinearOpMode o){
        while (o.opModeIsActive() && path.isExecuting()){
            odometry.updateGlobalPosition();
            double[] pows = path.update(odometry);
            if(pows!= null) {
                bot.move(pows[1], pows[0], pows[2]);
            }
            o.telemetry.addData("x", odometry.getGlobalPose()[0]);
            o.telemetry.addData("y", odometry.getGlobalPose()[1]);
            o.telemetry.addData("h", odometry.getGlobalPose()[2]);
            o.telemetry.addData("vx", path.XVelocity);
            o.telemetry.addData("vy", path.YVelocity);
            o.telemetry.addData("vh", path.HVelocity);
            o.telemetry.update();
        }
        bot.move(0,0,0);
    }

    public void scanStones(Path p, final TerraCV cv){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.move(0,0,0);
                cv.takePicture();
                stonePos = cv.getStonePos(0,300);
                op.telemetry.addData("Stone", stonePos.toString());
                op.telemetry.update();
            }
        });
    }

    public void intake(Path p, final double pow){
        bot.move(0,0,0);
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.intake(pow);
            }
        });
    }

    public void grabFoundation(Path p, final double pos){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.move(0,0,0);
                bot.foundationGrab(pos);
            }
        });
    }

    public void flip(Path p, final double p1, final double p2){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.move(0,0,0);
                bot.flip(p1,p2);
            }
        });
    }

    public void grab(Path p, final double pos){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.move(0,0,0);
                bot.grab(pos);
                op.sleep(500);
            }
        });
    }

    public void pause(Path p, final long time){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.move(0,0,0);
                op.sleep(time);
            }
        });
    }

    public void resetOdometry(final Path p){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                odometry.reset();
            }
        });
    }
    public void resetHeading(final Path p, final int heading){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                odometry.theta = (bot.getHeading() - heading);
            }
        });
    }

    public void setAccuracy(final Path p, final double x, final double y, final double h){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                p.HACCURACY = h;
                p.XACCURACY = x;
                p.YACCURACY = y;
            }
        });
    }
    public void move(final double y, final double x, final double t, final double time){
        timer.reset();
        bot.move(y, x, t);
        while (op.opModeIsActive() && timer.seconds() < time){}
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
    public void telemetryText(String text) {
        op.telemetry.addData(":", text);
        op.telemetry.update();
    }

    public void telemetryValue(String cap, double d) {
        op.telemetry.addData(cap, d);
        op.telemetry.update();
    }

}

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
//            o.telemetry.addData("x", odometry.getGlobalPose()[0]);
//            o.telemetry.addData("y", odometry.getGlobalPose()[1]);
//            o.telemetry.addData("h", odometry.getGlobalPose()[2]);
//            o.telemetry.addData("vx", path.XVelocity);
//            o.telemetry.addData("vy", path.YVelocity);
//            o.telemetry.addData("vh", path.HVelocity);
//            o.telemetry.update();
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
    public void setScale(final Path p,final double scale, final boolean sketch){
        p.addPose(0,0,0);
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                p.multiplyKD(scale);
                p.sketch = sketch;
            }
        });

    }

    public void move(final double y, final double x, final double t, final double time){
        timer.reset();
        bot.move(y, x, t);
        while (op.opModeIsActive() && timer.seconds() < time){}
        bot.move(0,0,0);
    }

    public void telemetryText(String text) {
        op.telemetry.addData(":", text);
        op.telemetry.update();
    }

    public void telemetryValue(String cap, double d) {
        op.telemetry.addData(cap, d);
        op.telemetry.update();
    }

}

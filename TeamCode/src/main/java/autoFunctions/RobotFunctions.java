package autoFunctions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.reflect.ParameterizedType;
import java.util.Currency;

import global.TerraBot;
import util.CodeSeg;
import util.Rect;
///////////////////////////////////////////////////////////////////////////////////import autoUtil.TerraCV.stoneP;


public class RobotFunctions {

    TerraBot bot = null;
    public LinearOpMode op = null;
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
    }
    public void scanStonesBeforeInit(TerraCV cv){
        while (!op.isStarted()){
            cv.takePictureBeforeInit();
            Rect area = new Rect(240, 400, 720, 150);

            TerraCV.StonePos sp = cv.getStonePos(area);
            if(sp != null) {
                stonePos = sp;
            }
            if(stonePos.equals(TerraCV.StonePos.RIGHT)) {
                op.telemetry.addData("status", "READy");
            }else if(stonePos.equals(TerraCV.StonePos.MIDDLE)){
                op.telemetry.addData("status", "REaDY");
            }else if (stonePos.equals(TerraCV.StonePos.LEFT)){
                op.telemetry.addData("status", "rEADY");
            }
            op.telemetry.update();
        }
    }

    public void scanStones(Path p, final TerraCV cv){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                cv.takePicture();
                Rect area = new Rect(0,0,1270, 710);
                stonePos = cv.getStonePos(area);
                stonePos = TerraCV.StonePos.RIGHT;
//                op.telemetry.addData("Stone", stonePos.toString());
//                op.telemetry.update();
            }
        });
    }

    public void intake(Path p, final double pow){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.intake(pow);
            }
        });
    }

    public void grabFoundation(Path p, final double pos){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.foundationGrab(pos);
            }
        });
    }

    public void flip(Path p, final double p1, final double p2){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.flip(p1,p2);
            }
        });
    }

    public void grab(Path p, final double pos){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                bot.grab(pos);
                //op.sleep(500);
            }
        });
    }

    public void pause(Path p, final long time){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                op.sleep(time);
            }
        });
    }

    public void resetOdometry(final Path p){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                odometry.reset();
            }
        });
    }
    public void resetHeading(final Path p, final int heading){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                odometry.theta = (bot.getHeading() - heading);
            }
        });
    }

    public void setAccuracy(final Path p, final double x, final double y, final double h){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                p.HACCURACY = h;
                p.XACCURACY = x;
                p.YACCURACY = y;
            }
        });
    }
    public void multiplyD(final Path p, final double scale){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                p.scaleD = scale;
            }
        });
    }
    public void setScale(final Path p,final double scale){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                p.multiplyKD(scale);
            }
        });

    }

    public void move(final double y, final double x, final double t, final double time){
        timer.reset();
        bot.move(y, x, t);
        while (op.opModeIsActive() && timer.seconds() < time){}
    }

    public void customThread(final Path p, final CodeSeg code){
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        code.run();
                    }
                });
                t.start();
            }
        });
    }

    public void telemetryText(final String text) {
        op.telemetry.addData(":", text);
        op.telemetry.update();
    }

    public void telemetryValue(Path p, final String cap, final double d) {
        p.addCustom(new CodeSeg() {
            @Override
            public void run() {
                op.telemetry.addData(cap, d);
                op.telemetry.update();
            }
        });
    }

}

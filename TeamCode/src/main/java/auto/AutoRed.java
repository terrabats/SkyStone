package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;
import util.Rect;

@Autonomous(name = "AutoRed", group = "Auto")
public class AutoRed extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path path = new Path();
    Path path2 = new Path();
    TerraCV.StonePos stonePos;

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("done initing");
        sleep(1000);
        rf.telemetryText("scaning...");
        rf.scanStonesBeforeInit(cv);
        waitForStart();
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            path.addPose(34,-2,-55, true);
            rf.intake(path, 1);
            path.addPose(3,5,-10, false);
            path.addPose(-14, 10, 155,false);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            path.addPose(34,8,55, true);
            rf.intake(path, 1);
            path.addPose(3,-5,10, false);
            path.addPose(-14, 12, 25,false);
        }else {
            path.addPose(34,0,55, true);
            rf.intake(path, 1);
            path.addPose(3,-5,10, false);
            path.addPose(-14, 20, 25,false);
        }
        grabStone(path);
        path.addPose(-2,50,0, false);
        rf.flip(path, 0.8, 0.8);
        rf.grabFoundation(path, 0.6);
        rf.multiplyD(path, 3.5);
        rf.setAccuracy(path, 0.5, 0.5, 2);
        path.addPose(0,20,90, true);
        rf.setAccuracy(path, 1, 1, 6);
        rf.multiplyD(path, 2);
        path.addPose(6, 0, 0, false);
        rf.grabFoundation(path, 0.85);
        dropStone(path);
        rf.setScale(path, 4);
        path.addPose(-10,-20, -50, false);
        path.addPose(5, -10, -50, false);
        rf.grabFoundation(path, 0);
        rf.start(path, this);
        path2.continuePath(path);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            path2.addPose(6, -54, 0, true);
            rf.intake(path2, 1);
            path2.addPose(10,-3,-10, true);
            path2.addPose(0,-4,0,false);
            path2.addPose(-13, 20, 20, false);
            grabStone(path2);
            path2.addPose(-2,26,0,false);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            path2.addPose(6, -68, 0, true);
            rf.intake(path2, 1);
            path2.addPose(10,-3,-10, true);
            path2.addPose(0,-4,0,false);
            path2.addPose(-13, 20, 20, false);
            grabStone(path2);
            path2.addPose(-2,40,0,false);
        }else {
            path2.addPose(6, -78, 0, true);
            rf.intake(path2, 1);
            path2.addPose(10,-3,-10, true);
            path2.addPose(0,-4,0,false);
            path2.addPose(-13, 20, 20, false);
            grabStone(path2);
            path2.addPose(-2,50,0,false);
        }
        rf.flip(path2, 0.8, 0.8);
        path2.addPose(0,10,0, false);
        bot.move(0,0,0);
        rf.pause(path2, 500);
        dropStone(path2);


        rf.start(path2, this);
        bot.move(0,0,0);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 3);
    }

    private void grabStone(Path p){
        CodeSeg grabStone = new CodeSeg() {
            @Override
            public void run() {
                ElapsedTime timer = new ElapsedTime();
                bot.flip(0,0);
                while (timer.seconds() < 0.6){}
                bot.grab(1);
                bot.intake(0);
            }
        };
        rf.customThread(p, grabStone);
    }

    private void dropStone(Path p){
        CodeSeg dropStone = new CodeSeg() {
            @Override
            public void run() {
                ElapsedTime timer = new ElapsedTime();
                bot.grab(bot.sp2);
                while (timer.seconds() < 0.5){}
                bot.flip(bot.sp, bot.sp);
            }
        };
        rf.customThread(p, dropStone);
    }

}

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
//            toFoundation.addPose(25,-2,-45, true);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(2,4,-5, false);
//            toFoundation.addPose(-15,11,-40, true);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            path.addPose(34,9,45, true);
            rf.intake(path, 1);
            path.addPose(2,-5,10, false);
            path.addPose(-13, 16, 35,false);
        }else {
//            toFoundation.addPose(25,-1,44, true);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(2,-4,11, false);
//            toFoundation.addPose(-14,14,-145, false);
        }
        grabStone();
        path.addPose(0,50,0, false);
        rf.flip(path, 0.8, 0.8);
        rf.grabFoundation(path, 0.6);
        rf.setAccuracy(path, 0.5, 0.5, 2);
        path.addPose(2,20,90, true);
        rf.setAccuracy(path, 1, 1, 6);
        path.addPose(4, 0, 0, false);
        rf.grabFoundation(path, 0.85);
        dropStone();
        rf.setScale(path, 4);
        path.addPose(-10,-20, -50, false);
        path.addPose(0, -10, -50, false);
        rf.grabFoundation(path, 0);
        rf.start(path, this);

        path2.continuePath(path);
        path2.addPose(7, -69, 0, true);
//        rf.setAccuracy(path2, 1, 1, 6);
//        rf.intake(path2, 1);
//        path2.addPose(5, -5, -25, true);
//        path2.addPose(10, -10, 0, false);
//        path2.addPose(-22, 20, 35, false);
//        grabStone();
//        path2.addPose(0, 40, 0, false);
//        rf.flip(path2, 0.8, 0.8);
//        path2.addPose(0, 20, 0, false);
        dropStone();


        rf.start(path2, this);
        //dropStone()
// rf.grabFoundation(toFoundation, 0.85);
//        toFoundation.addPose(4,0,0, false);
//        rf.setScale(toFoundation,4);
//        toFoundation.addPose(-10, -20, -50, false);
//        toFoundation.addPose(0,-5,-40, false);
//        rf.setScale(toFoundation, 1);
        //rf.grabFoundation(toFoundation, 0);
//        toFoundation.addPose(0, -45, 0, false);
//        toFoundation.addPose(16, -20, -20, true);
//        rf.intake(toFoundation, 1);
//        toFoundation.addPose(2, -6, 0, false);
//        toFoundation.addPose(-13, 20, 20, true);
//        grabStone();
//        toFoundation.addPose(0,35,0, false);
//        rf.flip(toFoundation, 0.8, 0.8);
//        toFoundation.addPose(-5,20,0, false);
//        dropStone();
//        rf.pause(toFoundation, 500);
//        toFoundation.addPose(3, -52, 0, false);
//        toFoundation.addPose(12, -30, -20, true);
//        rf.intake(toFoundation, 1);
//        toFoundation.addPose(2, -5, 0, false);
//        toFoundation.addPose(-13, 20, 20, false);
//        grabStone();
//        toFoundation.addPose(0, 70, 0, false);
//        rf.flip(toFoundation, 0.8, 0.8);
//        dropStone();
//        rf.pause(toFoundation, 500);
//        toFoundation.addPose(0, -40, 0, true);
//
//        rf.start(toFoundation, this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 3);
    }

    private void grabStone(){
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
        rf.customThread(path, grabStone);
    }

    private void dropStone(){
        CodeSeg dropStone = new CodeSeg() {
            @Override
            public void run() {
                ElapsedTime timer = new ElapsedTime();
                bot.grab(bot.sp2);
                while (timer.seconds() < 0.5){}
                bot.flip(bot.sp, bot.sp);
            }
        };
        rf.customThread(path, dropStone);
    }

}

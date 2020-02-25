package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


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
    Path toStone = new Path();
    Path toFoundation = new Path();
    TerraCV.StonePos stonePos;

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("done initing");
        sleep(1000);
        rf.telemetryText("scaning...");
        rf.scanStonesBeforeInit(cv);
        waitForStart();
//        toStone.addPose(10,0,0, false);
//        rf.scanStones(toStone,cv);
//        rf.start(toStone,this);
//        toFoundation.continuePath(toStone);
//        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
//            toFoundation.addPose(25,-2,-45, true);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(2,4,-5, false);
//            toFoundation.addPose(-15,11,-40, true);
//        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
//            toFoundation.addPose(25,-10,-40, true);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(2,4,-10, false);
//            toFoundation.addPose(-12,17,-40, false);
//        }else {
//            toFoundation.addPose(25,-1,44, true);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(2,-4,11, false);
//            toFoundation.addPose(-14,14,-145, false);
//        }
//        grabStone();
//        toFoundation.addPose(0,50,0, false);
//        rf.flip(toFoundation, 0.8, 0.8);
//        toFoundation.addPose(4,20,-90, true);
//        dropStone();
//        rf.grabFoundation(toFoundation, 0.85);
//        toFoundation.addPose(4,0,0, false);
//        rf.setScale(toFoundation,4);
//        toFoundation.addPose(-10, -20, -50, false);
//        toFoundation.addPose(0,-5,-40, false);
//        rf.setScale(toFoundation, 1);
//        rf.grabFoundation(toFoundation, 0);
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
                bot.flip(0,0);
                rf.op.sleep(600);
                bot.grab(1);
                bot.intake(0);
            }
        };
        rf.customThread(toFoundation, grabStone);
    }

    private void dropStone(){
        CodeSeg dropStone = new CodeSeg() {
            @Override
            public void run() {
                bot.grab(bot.sp2);
                rf.op.sleep(500);
                bot.flip(bot.sp, bot.sp);
            }
        };
        rf.customThread(toFoundation, dropStone);
    }

}

package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;

@Autonomous(name = "AutoRed", group = "Auto")
public class AutoRed extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path toStone = new Path();
    Path toFoundation = new Path();
//    Path moveFoundation = new Path();
//    Path toSecond = new Path();

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("ready");
        waitForStart();
        //ToStone
        toStone.addPose(10,0,0);
        rf.scanStones(toStone,cv);
        toStone.addPose(0,0,0);
        rf.start(toStone,this);
        //ToFoundation
        toFoundation.continuePath(toStone);
        //rf.resetSum(toFoundation);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            toFoundation.addPose(23,1,-40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,4,-10);
            toFoundation.addPose(-14,-14,133);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            toFoundation.addPose(23,10,40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,-4,10);
            toFoundation.addPose(-12,-17,32);
        }else {
            toFoundation.addPose(23,2,40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,-4,10);
            toFoundation.addPose(-12,-11,32);
        }
        grabStone();
        toFoundation.addPose(0,-50,0);
        rf.flip(toFoundation, 0.9, 0.8);
        toFoundation.addPose(4,-20,85);
        rf.grab(toFoundation, bot.sp2);
        rf.pause(toFoundation, 500);
        rf.flip(toFoundation, bot.sp, bot.sp);


        rf.setScale(toFoundation,1.5, true);
        toFoundation.addPose(4,0,0);
        rf.grabFoundation(toFoundation, 1.2);
        rf.pause(toFoundation, 1000);
        rf.setScale(toFoundation, 6, true);
        toFoundation.addPose(-32,0,0);
        rf.setScale(toFoundation, 1.05, false);
        rf.grabFoundation(toFoundation, 0);
        rf.pause(toFoundation, 1000);
        toFoundation.addPose(0,30,0);
        toFoundation.addPose(25, 0, 0);
        toFoundation.addPose(0,20,0);
        rf.start(toFoundation,this);
//        toFoundation.addPose(0,5,0);
//        rf.grab(toFoundation,0);
//        rf.grabFoundation(toFoundation, 1);
//        toFoundation.addPose(0,0,0);
//        rf.pause(toFoundation,500);
//        rf.setAccuracy(toFoundation, 3, 3, 15);
//        toFoundation.addPose(20,-10,-100);
//        rf.flip(toFoundation, bot.sp, bot.sp);
//        rf.grabFoundation(toFoundation, 0);
//        rf.start(moveFoundation, this);
//        rf.move( -0.5, 0,0, 1);
//        rf.odometry.reset();
//        toSecond.addPose(56,3,0);
//
//        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
//            toSecond.addPose(12,0,0);
//            toSecond.addPose(10,10,-40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(2, 2, 10);
//            toSecond.addPose(-22,-20,30);
//        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
//            toSecond.addPose(20,0,0);
//            toSecond.addPose(10,10,-40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(4, 2, 10);
//            toSecond.addPose(-33,-20,30);
//        }else {
//            toSecond.addPose(25,0,0);
//            toSecond.addPose(10,10,-40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(2, 2, 10);
//            toSecond.addPose(-38,-20,30);
//        }
//        grabStone2();
//        toSecond.addPose(-35,0,0);
//        rf.flip(toSecond, 0.65, 0.85);
//        toSecond.addPose(-21,-4,0);
//        rf.grab(toSecond, 0);
//        rf.flip(toSecond, bot.sp, bot.sp);
//        rf.pause(toSecond, 500);
//        toSecond.addPose(40, -2, 0);
//        rf.start(toSecond, this);


    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 4);
    }

    private void grabStone(){
        rf.flip(toFoundation, 0,0);
        rf.pause(toFoundation,100);
        rf.grab(toFoundation,1);
        //rf.pause(toFoundation,500);
        rf.intake(toFoundation,0);
    }
//    private void grabStone2(){
//        rf.flip(toSecond, 0,0);
//        rf.pause(toSecond,500);
//        rf.grab(toSecond,1);
//        rf.intake(toSecond,0);
//    }



}

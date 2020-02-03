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
    Path moveFoundation = new Path();
    Path toSecond = new Path();

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("ready");
        waitForStart();
        //ToStone
        toStone.addPose(10,0,0);
        rf.scanStones(toStone,cv);
        rf.start(toStone,this);
        //ToFoundation
        toFoundation.continuePath(toStone);
        //rf.resetSum(toFoundation);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            toFoundation.addPose(21,1,-40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,4,-10);
            toFoundation.addPose(-19,14,-46);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            toFoundation.addPose(22,9,40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,-4,10);
            toFoundation.addPose(-15,13,43);
        }else {
            toFoundation.addPose(23,3,40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,-6,10);
            toFoundation.addPose(-15,20,38);
        }
//
//        grabStone();
//        rf.resetOdometry(toFoundation);
        rf.start(toFoundation,this);
//
//        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
//            rf.resetHeading(moveFoundation, -90);
//            moveFoundation.addPose(30,0,0);
//            rf.flip(moveFoundation, 0.65, 0.85);
//            moveFoundation.addPose(30,-5,-90);
//            moveFoundation.addPose(0,-3,0);
//            rf.grab(moveFoundation,0);
//            rf.grabFoundation(moveFoundation, 1);
//            moveFoundation.addPose(0,0,0);
//            rf.pause(moveFoundation,500);
//            rf.setAccuracy(moveFoundation, 3, 3, 15);
//            moveFoundation.addPose(-20,10,-100);
//            rf.flip(moveFoundation, bot.sp, bot.sp);
//            rf.grabFoundation(moveFoundation, 0);
//        }else {
//            rf.resetHeading(moveFoundation, 90);
//            moveFoundation.addPose(-30,0,0);
//            rf.flip(moveFoundation, 0.65, 0.85);
//            moveFoundation.addPose(-30,7,90);
//            moveFoundation.addPose(0,5,0);
//            rf.grab(moveFoundation,0);
//            rf.grabFoundation(moveFoundation, 1);
//            moveFoundation.addPose(0,0,0);
//            rf.pause(moveFoundation,500);
//            rf.setAccuracy(moveFoundation, 3, 3, 15);
//            moveFoundation.addPose(20,-10,-100);
//            rf.flip(moveFoundation, bot.sp, bot.sp);
//            rf.grabFoundation(moveFoundation, 0);
//        }
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
        cv.init(bot,this, 5);
    }

    private void grabStone(){
        rf.flip(toFoundation, 0,0);
        rf.pause(toFoundation,500);
        rf.grab(toFoundation,1);
        rf.intake(toFoundation,0);
    }
    private void grabStone2(){
        rf.flip(toSecond, 0,0);
        rf.pause(toSecond,500);
        rf.grab(toSecond,1);
        rf.intake(toSecond,0);
    }



}

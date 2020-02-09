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

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("ready");
        waitForStart();
        toStone.addPose(10,0,0);
        rf.scanStones(toStone,cv);
        toStone.addPose(0,0,0);
        rf.start(toStone,this);
        toFoundation.continuePath(toStone);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            toFoundation.addPose(23,-2,-40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,4,-10);
            toFoundation.addPose(-12,11,-32);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            toFoundation.addPose(23,-10,-40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,4,-10);
            toFoundation.addPose(-12,17,-32);
        }else {
            toFoundation.addPose(23,-1,40);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(2,-4,10);
            toFoundation.addPose(-14,14,-133);
        }
        grabStone();
        toFoundation.addPose(0,50,0);
        rf.flip(toFoundation, 0.9, 0.8);
        toFoundation.addPose(1,20,-85);
        rf.grab(toFoundation, bot.sp2);
        rf.pause(toFoundation, 500);
        rf.flip(toFoundation, bot.sp, bot.sp);


        rf.setScale(toFoundation,1.5, true);
        toFoundation.addPose(4,0,0);
        rf.grabFoundation(toFoundation, 1.2);
        rf.pause(toFoundation, 1000);
        rf.setScale(toFoundation, 4, true);
        toFoundation.addPose(-33,1,0);
        rf.setScale(toFoundation, 1.05, false);
        rf.grabFoundation(toFoundation, 0);
        rf.pause(toFoundation, 1000);
        toFoundation.addPose(0,-30,0);
        toFoundation.addPose(25, 0, 0);
        toFoundation.addPose(0,-20,0);
        rf.start(toFoundation,this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 3);
    }

    private void grabStone(){
        rf.flip(toFoundation, 0,0);
        rf.pause(toFoundation,100);
        rf.grab(toFoundation,1);
        rf.intake(toFoundation,0);
    }


}

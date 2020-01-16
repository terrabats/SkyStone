package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
    Path toSecond = new Path();



    @Override
    public void runOpMode() {
        initialize();
        toStone.HACCURACY = 3;
        rf.telemetryText("ready");
        waitForStart();
        //ToStone
        toStone.addPose(10,0,0);
        rf.scanStones(toStone,cv);
        rf.start(toStone,this);
        //ToFoundation
        toFoundation.continuePath(toStone);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){




            toFoundation.addPose(25,-5,-60);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(7,5,-10);
            toFoundation.addPose(-28,14,-33);
            rf.intake(toFoundation,0);
            rf.flip(toFoundation, 0,0);
            rf.grab(toFoundation,1);
            toFoundation.addPose(0,30,0);
            rf.flip(toFoundation, 0.55, 0.75);
            toFoundation.addPose(-7,35,-95);
            rf.grabFoundation(toFoundation,1);
            toFoundation.addPose(7,0,0);
            rf.grab(toFoundation, 0);






        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            toFoundation.addPose(25,-14,-60);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(7,5,-10);
            toFoundation.addPose(-28,23,-33);
            rf.intake(toFoundation,0);
            rf.flip(toFoundation, 0,0);
            rf.grab(toFoundation,1);
            toFoundation.addPose(0,30,0);
            rf.flip(toFoundation, 0.55, 0.75);
            toFoundation.addPose(-9,40,-95);
            rf.grabFoundation(toFoundation,1);
            toFoundation.addPose(5,0,0);
            rf.grab(toFoundation, 0);
        }else {
            toFoundation.addPose(25,8,60);
            rf.intake(toFoundation, 1);
            toFoundation.addPose(7,-5,10);
            toFoundation.addPose(-28,10,33);
            rf.intake(toFoundation,0);
            rf.flip(toFoundation, 0,0);
            rf.grab(toFoundation,1);
            toFoundation.addPose(0,30,0);
            rf.flip(toFoundation, 0.55, 0.75);
            toFoundation.addPose(9,40,95);
            rf.grabFoundation(toFoundation,1);
            toFoundation.addPose(6,0,0);
            rf.grab(toFoundation, 0);
        }

        rf.start(toFoundation,this);
        toSecond.HACCURACY = 10;
        toSecond.XACCURACY = 2;
        toSecond.YACCURACY = 2;
        sleep(800);
        toSecond.continuePath(toFoundation);
        rf.flip(toSecond, bot.sp, bot.sp);
        toSecond.addPose(-20, -30, -90);
        rf.grabFoundation(toSecond, 0);
        toSecond.addPose(0,15,0);
        toSecond.addPose(10,-20,30);
        rf.start(toSecond, this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 5);
    }



}

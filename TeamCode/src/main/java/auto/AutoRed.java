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
        grabStone();
        path.addPose(-2,50,0, false);
        rf.flip(path, 0.8, 0.8);
        rf.grabFoundation(path, 0.6);
        rf.multiplyD(path, 3);
        rf.setAccuracy(path, 0.5, 0.5, 2);
        path.addPose(0,20,90, true);
        rf.setAccuracy(path, 1, 1, 6);
        rf.multiplyD(path, 2);
        path.addPose(6, 0, 0, false);
        rf.grabFoundation(path, 0.85);
        dropStone();
        rf.setScale(path, 4);
        path.addPose(-10,-20, -50, false);
        path.addPose(5, -10, -50, false);
        rf.grabFoundation(path, 0);
        rf.start(path, this);
        path2.continuePath(path);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            path2.addPose(6, -45, 0, true);
            rf.intake(path2, 1);
            path2.addPose(10,-16,-25, false);
            path2.addPose(-13, 10, 10, false);
            path2.addPose(0,40,0,false);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){

        }else {
            path2.addPose(6, -45, 0, true);
            rf.intake(path2, 1);
            path2.addPose(10,-16,-25, false);
            path2.addPose(-13, 10, 10, false);
            path2.addPose(0,40,0,false);
        }


        rf.start(path2, this);

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

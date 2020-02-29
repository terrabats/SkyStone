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
    Path path3 = new Path();
    Path path4 = new Path();

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
            path2.addPose(4, -68, 0, true);
            rf.intake(path2, 1);
            path2.addPose(12,0,-10, true);
            path2.addPose(0,-7,0,false);
            path2.addPose(-13, 20, 20, false);
            grabStone(path2);
            path2.addPose(-2,40,0,false);
        }else {
            path2.addPose(6, -74, 0, true);
            rf.intake(path2, 1);
            path2.addPose(12,-3,-10, true);
            path2.addPose(0,-5,0,false);
            path2.addPose(-13, 20, 20, false);
            grabStone(path2);
            path2.addPose(-2,50,0,false);
        }
        rf.flip(path2, 0.8, 0.8);
        path2.addPose(-6,15,0, true);
        rf.start(path2, this);
        bot.move(0,0,0);
        path3.continuePath(path2);
        dropStone(path3);
        rf.pause(path3, 500);
        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            path3.addPose(10, -60, 0, true);
            rf.intake(path3, 1);
            path3.addPose(13,-3,-20, true);
            path3.addPose(0,-5,0,false);
            path3.addPose(-14, 20, 20, false);
            grabStone(path3);
            path3.addPose(0,35,0,false);
            rf.flip(path3, 0.8, 0.8);
            rf.setScale(path3, 3);
            path3.addPose(0,35, 0, true);
            rf.setScale(path3, 1);
        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
            path3.addPose(6, -75, 0, true);
            rf.intake(path3, 1);
            path3.addPose(13,-3,-20, true);
            path3.addPose(0,-5,0,false);
            path3.addPose(-12, 20, 20, false);
            grabStone(path3);
            path3.addPose(0,45,0,false);
            rf.flip(path3, 0.8, 0.8);
            rf.setScale(path3, 2);
            path3.addPose(0,35, 0, false);
            rf.setScale(path3, 1);
        }else {
            path3.addPose(6, -40, 0, false);
            path3.addPose(20,-23,-20, true);
            rf.intake(path3, 1);
            rf.setScale(path3, 0.5);
            path3.addPose(5, -8, 0, false);
            rf.setScale(path3, 1);
            path3.addPose(-23, 27, 20, false);
            grabStone(path3);
            path3.addPose(0,40,0, false);
            rf.flip(path3, 0.8,0.8);
            rf.setScale(path3, 2);
            path3.addPose(0,25, 0, false);
            rf.setScale(path3, 1);
        }
        rf.start(path3, this);
        bot.move(0,0,0);
        path4.continuePath(path3);
        path4.addPose(-6, 0, 0,true);
        dropStone(path4);
        rf.pause(path4, 500);
        path4.addPose(2,-40, 0, true);
        rf.start(path4, this);
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

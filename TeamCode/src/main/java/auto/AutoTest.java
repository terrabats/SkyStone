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

@Autonomous(name = "AutoTest", group = "Auto")
public class AutoTest extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path path = new Path();
    TerraCV.StonePos stonePos;

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("done initing");
        sleep(1000);
        rf.telemetryText("scaning...");
        //rf.scanStonesBeforeInit(cv);
        waitForStart();
        //path.addPose(90, 0, 0, true);
        rf.flip(path, 0.8,0.8);
        path.addPose(-30,-6,-0, true);
        //path.addPose();
        rf.start(path, this);
        bot.move(0,0,0);
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

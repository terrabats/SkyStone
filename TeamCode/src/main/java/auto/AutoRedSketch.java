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

@Autonomous(name = "AutoRedSketch", group = "Auto")
public class AutoRedSketch extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path path = new Path();

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("done initing");
        sleep(500);
        rf.telemetryText("scaning...");
        rf.scanStonesBeforeInit(cv);
        waitForStart();

        rf.intake(path, 1);

        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
            path.addPose(34, -12, 35, true);}else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
        }else {
        }
        rf.start(path, this);

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
        rf.customThread(path, grabStone);
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
        rf.customThread(path, dropStone);
    }

}

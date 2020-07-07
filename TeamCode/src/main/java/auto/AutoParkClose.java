package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;

@Autonomous(name = "AutoParkClose", group = "Auto")
public class AutoParkClose extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    Path move = new Path();

    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("ready");
        waitForStart();
        move.addPose(10,0,0,true);
        rf.start(move,this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
    }



}

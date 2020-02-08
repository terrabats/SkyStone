package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;

@Autonomous(name = "AutoRedFound", group = "Auto")
public class AutoRedFound extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    Path moveFoundation = new Path();
    @Override
    public void runOpMode() {
        initialize();
        rf.telemetryText("ready");
        waitForStart();
        //ToFoundation
        moveFoundation.addPose(-30,-10,0);
        rf.setScale(moveFoundation,0.4);
        moveFoundation.addPose(-2,0,0);
        rf.grabFoundation(moveFoundation, 0.9);
        rf.pause(moveFoundation, 1000);
        rf.setScale(moveFoundation, 1.2);
        moveFoundation.addPose(15,15,-83);
        rf.grabFoundation(moveFoundation, 0);
        rf.pause(moveFoundation, 1000);
        moveFoundation.addPose(-10,35,0);
        rf.start(moveFoundation,this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
    }



}

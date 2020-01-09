package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoUtil.Path;
import autoUtil.RobotFunctions;
import autoUtil.TargetDetection;
import global.CodeSeg;
import global.Helper;
import global.TerraBot;
@Autonomous(name = "AutoRed", group = "Auto")
public class AutoRed extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    Path path = new Path();


    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        path.addPose(10,0,0);
        rf.start(path,this);
    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot);
        path.init();
    }



}

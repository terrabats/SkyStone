package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
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
        path.addPose(-5,40,0);
        path.addPose(-40,-5,0);
        rf.start(path,this);
    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot);
        path.init();
    }



}
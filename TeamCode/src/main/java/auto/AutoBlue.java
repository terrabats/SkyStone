package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
@Autonomous(name = "AutoBlue", group = "Auto")
public class AutoBlue extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV tc = new TerraCV();
    Path p1  = new Path();
    Path p2 = new Path();
    TerraCV.StonePos stonePos;


    @Override
    public void runOpMode(){
        //Front of robot to stone = 19 inches
        initialize();
        waitForStart();
        p1.addPose(10,0,0);
        rf.start(p1,this);
    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        tc.init(bot,this, 7);
    }
}

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
        telemetry.addData("size", p1.YPoses.size()); // 2
        telemetry.addData("last", p1.YPoses.get(p1.YPoses.size()-1)); // 10
        telemetry.update();
        sleep(5000);
        p2.continuePath(p1);
        telemetry.addData("size", p2.YPoses.size());// 1
        telemetry.addData("0", p2.YPoses.get(0));//10
        telemetry.update();
        sleep(5000);
//        rf.start(p1,this);
//        telemetry.addData("bruh", "hi");
//        telemetry.update();
//        p2.addPose(20,0,0);
//        rf.start(p2,this);

    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot);
        tc.init(bot,this, 7);
    }
}

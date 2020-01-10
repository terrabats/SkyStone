package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.TerraCV;
import global.TerraBot;
@Autonomous(name = "AutoBlue", group = "Auto")
public class AutoBlue extends LinearOpMode {
    TerraBot bot = new TerraBot();
    TerraCV tc = new TerraCV();
    TerraCV.StonePos stonePos;

    @Override
    public void runOpMode(){
        //Front of robot to stone = 19 inches
        initialize();
        waitForStart();
        tc.takePicture();
        stonePos = tc.getStonePos(0, 400);
        telemetry.addData("stonePos", stonePos.toString());
        telemetry.addData("time", tc.time);
        telemetry.update();
        sleep(3000);

    }

    private void initialize(){
        bot.init(hardwareMap);
        tc.init(bot,this);
        tc.Accuracy = 7;
    }
}

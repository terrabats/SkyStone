package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
import util.CodeSeg;

@Autonomous(name = "AutoRed", group = "Auto")
public class AutoRed extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path toStone = new Path();
    Path toFoundation = new Path();
    TerraCV.StonePos stonePos;



    @Override
    public void runOpMode() {
        initialize();
        telemetry.addData("Status", "Ready");
        telemetry.update();
        waitForStart();
        toStone.addPose(10,0,0);
        toStone.addCustom(new CodeSeg() {
            @Override
            public void run() {
                cv.takePicture();
                stonePos = cv.getStonePos(0,400);
                telemetry.addData("Stone", stonePos.toString());
                telemetry.update();
            }
        });
        rf.start(toStone,this);
        toFoundation.continuePath(toStone);
        if(stonePos.equals(TerraCV.StonePos.RIGHT)){
            toFoundation.addPose(22,-3,-45);
        }else if(stonePos.equals(TerraCV.StonePos.MIDDLE)){

        }else {

        }
        rf.start(toFoundation,this);
    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot);
        cv.init(bot,this, 7);
    }



}

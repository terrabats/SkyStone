package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import autoFunctions.Path;
import autoFunctions.RobotFunctions;
import autoFunctions.TerraCV;
import global.TerraBot;
@Autonomous(name = "AutoBlue", group = "Auto")
public class AutoBlue extends LinearOpMode {
    TerraBot bot = new TerraBot();
    RobotFunctions rf = new RobotFunctions();
    TerraCV cv = new TerraCV();
    Path toStone = new Path();
    Path toFoundation = new Path();
    Path moveFoundation = new Path();
    Path toSecond = new Path();



    @Override
    public void runOpMode() {
        initialize();
        bot.gyro.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        while (opModeIsActive()){
            telemetry.addData("Acceleration", bot.gyro.getAcceleration().xAccel);
            telemetry.addData("Position: x", bot.gyro.getPosition().toUnit(DistanceUnit.INCH).x);
            telemetry.addData("Position: y", bot.gyro.getPosition().toUnit(DistanceUnit.INCH).y);
            telemetry.addData("Position: z", bot.gyro.getPosition().toUnit(DistanceUnit.INCH).z);
            telemetry.update();
        }
//        toStone.HACCURACY = 3;
//        rf.telemetryText("ready");
//        waitForStart();
//        //ToStone
//        toStone.addPose(10,2,0);
//        rf.scanStones(toStone,cv);
//        rf.start(toStone,this);
//        //ToFoundation
//        toFoundation.continuePath(toStone);
//        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
//            toFoundation.addPose(21,1,-45);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(3,4,-10);
//            toFoundation.addPose(-12,-20,-36);
//        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
//            toFoundation.addPose(21,-7,-45); // CHeck
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(3,4,-10);
//            toFoundation.addPose(-12,-13,-36);
//        }else {
//            toFoundation.addPose(21,3,45);
//            rf.intake(toFoundation, 1);
//            toFoundation.addPose(3,-4,10);
//            toFoundation.addPose(-16,-15,34);
//        }
//
//        grabStone();
//        rf.resetOdometry(toFoundation);
//        rf.start(toFoundation,this);
//
//        if(rf.stonePos.equals(TerraCV.StonePos.LEFT)){
//            rf.resetHeading(moveFoundation, 90);
//            moveFoundation.addPose(30,0,0);
//            rf.flip(moveFoundation, 0.55, 0.75);
//            moveFoundation.addPose(30,12,90);
//            moveFoundation.addPose(0,3,0);
//            rf.grab(moveFoundation,bot.sp2);
//            rf.grabFoundation(moveFoundation, 1);
//            moveFoundation.addPose(0,0,0);
//            rf.pause(moveFoundation,500);
//            rf.setAccuracy(moveFoundation, 3, 3, 15);
//            moveFoundation.addPose(-20,-9,100);
//            rf.flip(moveFoundation, bot.sp, bot.sp);
//            rf.grabFoundation(moveFoundation, 0);
//        }else {
//            rf.resetHeading(moveFoundation, -90);
//            moveFoundation.addPose(-30,0,0);
//            rf.flip(moveFoundation, 0.65, 0.85);
//            moveFoundation.addPose(-30,-12,-90);
//            moveFoundation.addPose(0,-5,0);
//            rf.grab(moveFoundation,bot.sp2);
//            rf.grabFoundation(moveFoundation, 1);
//            moveFoundation.addPose(0,0,0);
//            rf.pause(moveFoundation,500);
//            rf.setAccuracy(moveFoundation, 3, 3, 15);
//            moveFoundation.addPose(20,9,100);
//            rf.flip(moveFoundation, bot.sp, bot.sp);
//            rf.grabFoundation(moveFoundation, 0);
//        }
//        rf.start(moveFoundation, this);
//        rf.move( -0.6, 0,0, 1);
//        rf.odometry.reset();
//        toSecond.addPose(56,4,0);
//
//        if(rf.stonePos.equals(TerraCV.StonePos.RIGHT)){
//            toSecond.addPose(31,0,0);
//            toSecond.addPose(10,-12,40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(2, -2, -10);
//            toSecond.addPose(-38,10,-30);
//        }else if(rf.stonePos.equals(TerraCV.StonePos.MIDDLE)){
//            toSecond.addPose(23,0,0);
//            toSecond.addPose(10,-12,40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(4, -2, -10);
//            toSecond.addPose(-33,10,-30);
//        }else {
//            toSecond.addPose(15,0,0);
//            toSecond.addPose(10,-12,40);
//            rf.intake(toSecond,1);
//            toSecond.addPose(2, -2, -10);
//            toSecond.addPose(-22,10,-30);
//        }
//
//        grabStone2();
//        toSecond.addPose(-35,0,-5);
//        rf.flip(toSecond, 0.65, 0.85);
//        toSecond.addPose(-23,-4,0);
//        rf.grab(toSecond, 0);
//        rf.flip(toSecond, bot.sp, bot.sp);
//        rf.pause(toSecond, 500);
//        toSecond.addPose(40, 0, 0);
//        rf.start(toSecond, this);
    }

    private void initialize(){
        bot.init(hardwareMap);
        rf.init(bot, this);
        cv.init(bot,this, 5);
    }

    private void grabStone(){
        rf.flip(toFoundation, 0,0);
        rf.pause(toFoundation,500);
        rf.grab(toFoundation,1);
        rf.intake(toFoundation,0);
    }
    private void grabStone2(){
        rf.flip(toSecond, 0,0);
        rf.pause(toSecond,500);
        rf.grab(toSecond,1);
        rf.intake(toSecond,0);
    }

}

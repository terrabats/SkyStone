package auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import autoUtil.Path;
import autoUtil.TargetDetection;
import global.CodeSeg;
import global.Helper;
import global.TerraBot;
@Autonomous(name = "AutoBlue", group = "Auto")
public class AutoBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

    }
//    TerraBot bot = new TerraBot();
//    DriveFunctions df = new DriveFunctions();
//    TargetDetection td = new TargetDetection();
//    Helper h = new Helper();
//    Path path1 = new Path();
//    Path path2 = new Path();
//    Path path3 = new Path();
//    Path path4 = new Path();
//    Path path5 = new Path();
//    Path pathI = new Path();
//    Path pathI2 = new Path();
//    TargetDetection.stoneP pos;
//
//
//    @Override
//    public void runOpMode() {
//        initialize();
//        waitForStart();
//        bot.cturn(90);
//        path1.addLine(0,-13,0, 0.8);
//        path1.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.move(0,0,0);
//                bot.arm.setPower(-0.7);
//                pos = df.scanVuforia();
//                df.telemetryText("StonePos", pos.toString());
//                bot.arm.setPower(0);
//            }
//        });
//        path1.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                df.turnDeg(180,0.8);
//                bot.resetGyro();
//                h.resetAllEncoders(bot);
//            }
//        });
//        df.start(path1,this);
//        h.resetAllEncoders(bot);
//        switch (pos){
//            case LEFT:
//                pathI.addLine(-12,22, 0,0.8);
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.turnDeg(0,0.8);
//                    }
//                });
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.moveAS(22,.5);
//                    }
//                });
//                break;
//            case MIDDLE:
//                pathI.addLine(-23,22, 0,0.8);
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.turnDeg(0,0.8);
//                    }
//                });
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.moveAS(14,.5);
//                    }
//                });
//                break;
//            case RIGHT:
//                pathI.addLine(-32,22, 0,0.8);
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.turnDeg(0,0.8);
//                    }
//                });
//                pathI.addCustomBlock(new CodeSeg() {
//                    @Override
//                    public void run() {
//                        df.moveAS(3,.5);
//                    }
//                });
//                break;
//        }
//        df.start(pathI, this);
//        h.resetAllEncoders(bot);
//        path2.addLine(0,0,55,0.7);
//        path2.addLine(7,0,0,0.8);
//        path2.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.move(0.27,0,0);
//                bot.intake(0.4);
//                while (opModeIsActive() && !bot.isStoneLoaded()){}
//                bot.move(0,0,0);
//                bot.intake(0);
//            }
//        });
//        path2.addLine( -5,0,0, 0.8);
//        path2.addLine(0,0,-45,0.6);
//        path2.addLine(0,-8,0,0.6);
//        path2.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                df.turnDeg(0,0.8);
//                h.resetAllEncoders(bot);
//            }
//        });
//        df.start(path2,this);
//        pathI2.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                df.moveAS(30,0.8);
//                df.turnDeg(0,0.8);
//                h.resetAllEncoders(bot);
//            }
//        });
//        df.start(pathI2,this);
//        path3.addLine(36,0,0,0.8);
//        path3.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.arm.setPower(0.6);
//                //Set to 0.4 for low battery
//                //Set to 0.38 fr high
//            }
//        });
//        path3.addLine(57,0,0,0.8);
//        path3.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                df.turnDeg(90,0.8);
//                bot.move(0,0,0);
//                df.moveAS(31,.8);
//                h.resetAllEncoders(bot);
//            }
//        });
//        df.start(path3,this);
//        path4.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.move(0.25,0,0);
//                bot.arm.setPower(-0.7);
//                df.pause(0.8);
//                bot.intake(-0.6);
//                bot.move(0,0,0);
//                df.pause(0.8);
//                bot.intake(0);
//                bot.arm.setPower(-0.2);
//            }
//        });
//        df.start(path4,this);
//        path5.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.move(-0.8,0,-0.8);
//                while(opModeIsActive() && bot.getHeading() < -5){}
//                bot.move(0,0,0);
//            }
//        });
//        path5.addCustomBlock(new CodeSeg() {
//            @Override
//            public void run() {
//                bot.arm.setPower(0.8);
//                df.pause(0.5);
//                bot.move(1,0,0);
//                df.pause(1.5);
//                bot.move(0,0,0);
//            }
//        });
//        df.start(path5,this);
//    }
//
//    private void initialize(){
//        bot.init(hardwareMap);
//        h.initVuforia(this, bot, true);
//        td.init(h.getVuforia(), telemetry);
//        df.init(bot, td, this);
//        df.telemetryText("Status:","Done Initailizing");
//        df.calibrateGyro();
//        df.telemetryText("Status:","Ready To Win");
//        path1.setConstants(0.04,0.04,0.5,0.5,0.5,0.3);
//        path2.setConstants(0.04,0.06,0.5,0.5,0.6,0.5);
//        path3.setConstants(0.04,0.04,0.5,0.5,0.5,0.5);
//        path4.setConstants(0.04,0.04,0.5,0.5,0.5,0.5);
//        path5.setConstants(0.04,0.04,0.5,0.5,0.5,0.5);
//        pathI.setConstants(0.04,0.04,0.5,0.5,0.5,0.5);
//        pathI2.setConstants(0.04,0.04,0.5,0.5,0.5,0.5);
//
//    }
//
//

}

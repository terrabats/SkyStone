package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import autoFunctions.Odometry;
import util.CodeSeg;
import global.TerraBot;
import teleFunctions.TeleThread;


@TeleOp(name = "TerraOpWin", group = "new")
public class TerraOp extends OpMode {
    public TerraBot bot = new TerraBot();
    public TeleThread t = new TeleThread();
    Odometry odometry = new Odometry();
    public Thread thread;

    private CodeSeg teleOpCode = new CodeSeg() {
        @Override
        public void run() {
            bot.move(-gamepad1.right_stick_y,gamepad1.right_stick_x, gamepad1.left_stick_x);

            if(bot.noAutoModules()){
                if(gamepad2.left_trigger > 0){
                    bot.flip(bot.sp,bot.sp);
                }
                if(bot.isLiftInLimits(gamepad2)) {
                    bot.lift((-gamepad2.right_stick_y / 2)+0.08);
                }else{
                    bot.lift(0.08);
                }

                if(gamepad2.right_bumper){
                    bot.grab(0);
                }else if(gamepad2.left_bumper){
                    bot.grab(1);
                }
            }else {
                bot.update();
            }

            if(gamepad2.right_trigger > 0){
                bot.t.reset();
                bot.flipOut.start();
            }

            if(gamepad2.b){
                bot.t1.reset();
                bot.grab.start();
            }


            if(bot.isPulling && !gamepad1.left_bumper){
                bot.intake(1);
            }else if(gamepad1.right_bumper){
                bot.isPulling = true;
            }else if(gamepad1.left_bumper){
                bot.isPulling = false;
                bot.intake(-1);
            }else {
                bot.intake(0);
            }

            if(gamepad1.right_trigger > 0){
                bot.foundationGrab(1);
            }else if(gamepad1.left_trigger > 0){
                bot.foundationGrab(0);
            }

            odometry.updateGlobalPosition();

            telemetry.addData("Height, StoneDistance", "%f, %f", bot.getLiftHeight(), bot.getStoneDistance());
            telemetry.update();

        }
    };

    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        bot.init(hardwareMap);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        odometry.init(bot);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
        t.init(teleOpCode);
        thread = new Thread(t);
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        t.stop();
    }
}
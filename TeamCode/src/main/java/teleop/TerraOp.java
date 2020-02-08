package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import autoFunctions.Odometry;
import global.Helper;
import util.CodeSeg;
import global.TerraBot;
import teleFunctions.TeleThread;


@TeleOp(name = "TerraOpWin", group = "new")
public class TerraOp extends OpMode {
    public TerraBot bot = new TerraBot();
    public TeleThread t = new TeleThread();
    public Helper h = new Helper();
    Odometry odometry = new Odometry();
    public Thread thread;

    private CodeSeg teleOpCode = new CodeSeg() {
        @Override
        public void run() {

            bot.move(h.calcPow(-gamepad1.right_stick_y),h.calcPow(gamepad1.right_stick_x), h.calcPow(gamepad1.left_stick_x));

            if(bot.noAutoModules()){
                if(gamepad2.left_trigger > 0){
                    bot.flip(bot.sp,bot.sp);
                }
                if(bot.isLiftInLimits(gamepad2)) {
                    bot.lift((-gamepad2.right_stick_y / 1.4)+0.08);
                }else{
                    bot.lift(0.08);
                }

                if(gamepad2.right_bumper){
                    bot.grab(1);
                }else if(gamepad2.left_bumper){
                    bot.grab(bot.sp2);
                }
            }else {
                bot.update();
            }

            if(gamepad2.right_trigger > 0){
               bot.flip(1,1);
            }

            if(gamepad2.y){
                bot.t1.reset();
                bot.grab.start();
            }
            if(gamepad2.x){
                bot.t1.reset();
                bot.retract.start();
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

            // telemetry.addData("x, y, h", "%f, %f, %f", odometry.tx,odometry.ty, odometry.theta);
            telemetry.update();

        }
    };

    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        bot.init(hardwareMap);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
        odometry.init(bot);
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
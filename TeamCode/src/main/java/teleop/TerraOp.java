package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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

    double fpow = 0;
    double spow = 0;
    double tpow = 0;

    private CodeSeg teleOpCode = new CodeSeg() {
        @Override
        public void run() {

            //bot.move(h.calcPow(-gamepad1.right_stick_y),h.calcPow(gamepad1.right_stick_x), h.calcPow(gamepad1.left_stick_x));
            if (bot.highGear) {
                fpow = h.calcPow(-gamepad1.right_stick_y, 1, bot);
                spow = h.calcPow(gamepad1.right_stick_x, 0.8, bot);
                tpow = h.calcPow(gamepad1.left_stick_x, 0.6, bot);
            } else {
                fpow = h.calcPow(-gamepad1.right_stick_y, 0.4, bot);
                spow = h.calcPow(gamepad1.right_stick_x, 0.4, bot);
                tpow = h.calcPow(gamepad1.left_stick_x, 0.3, bot);
            }
            bot.move(fpow, spow, tpow);


            if(gamepad2.right_bumper && bot.highGear && bot.delay.seconds() > 0.3){
                bot.highGear = false;
                bot.delay.reset();
            }else if(gamepad2.right_bumper && !bot.highGear && bot.delay.seconds() > 0.3){
                bot.highGear = true;
                bot.delay.reset();
            }

            if(bot.noAutoModules()){
                if(gamepad2.left_trigger > 0){
                    bot.flip(bot.sp,bot.sp);
                }
                if(bot.isLiftInLimits(gamepad2)) {
                    bot.lift((-gamepad2.right_stick_y*0.65)+0.08);
                }else{
                    bot.lift(0.08);
                }

                if(gamepad2.left_bumper && bot.grabing && bot.delay.seconds() > 0.3){
                    bot.grab(1);
                    bot.grabing = false;
                    bot.delay.reset();
                }else if(gamepad2.left_bumper && !bot.grabing && bot.delay.seconds() > 0.3){
                    bot.grab(bot.sp2);
                    bot.grabing = true;
                    bot.delay.reset();
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
                bot.highGear = true;
            }
            if(gamepad2.x){
                bot.t1.reset();
                bot.retract.start();
                bot.highGear = true;
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

            if(gamepad1.dpad_down){
                bot.foundationGrab(0.85);
            }else if(gamepad1.dpad_up){
                bot.foundationGrab(0);
            }
            odometry.updateGlobalPosition();

            telemetry.addData("x, y, h", "%f, %f, %f", odometry.tx,odometry.ty, odometry.theta);
            //telemetry.addData("height", bot.getLiftHeight());
            //telemetry.addData("stoneDis", bot.getStoneDistance());
            //telemetry.addData("gyro", bot.getHeading());
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
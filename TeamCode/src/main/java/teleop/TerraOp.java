package teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import autoUtil.Odometry;
import global.CodeSeg;
import global.Helper;
import global.TerraBot;
import teleUtil.TeleThread;


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

            if(bot.isLiftInLimits(gamepad2)) {
                bot.lift(-gamepad2.right_stick_y / 2);
            }

            if(gamepad2.right_bumper){
                bot.grab(0);
            }else if(gamepad2.left_bumper){
                bot.grab(1);
            }

            if(gamepad2.right_trigger > 0){
                bot.flip(0);
            }else if(gamepad2.left_trigger > 0){
                bot.flip(1);
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

            odometry.updateGlobalPosition();

            telemetry.addData("Pos", "{R, L, C, X, Y} = %f, %f, %f, %f, %f", (odometry.cr/odometry.TICKS_FOR_ODOMETRY)*(2*Math.PI*odometry.ENCODER_WHEEL_RADIUS),odometry.cl,odometry.cc,
            odometry.tx,odometry.ty);
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
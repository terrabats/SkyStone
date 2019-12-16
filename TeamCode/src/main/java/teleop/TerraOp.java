package teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import global.CodeSeg;
import global.Helper;
import global.TerraBot;
import teleUtil.TeleThread;


@TeleOp(name = "TerraOpWin", group = "new")
public class TerraOp extends OpMode {
    public TerraBot bot = new TerraBot();
    public Helper h = new Helper();
    public TeleThread t = new TeleThread();
    public Thread thread;
    private CodeSeg teleOpCode = new CodeSeg() {
        @Override
        public void run() {
            bot.move(h.calcPow(-gamepad1.right_stick_y), h.calcPow(gamepad1.right_stick_x), h.calcPow(gamepad1.left_stick_x));

            if (!bot.pull.executing && !bot.withdraw.executing && !bot.place.executing) {
                if (gamepad1.right_trigger > 0) {
                    bot.moveArm(gamepad1.right_trigger);
                } else if (gamepad1.left_trigger > 0) {
                    bot.moveArm(-gamepad1.left_trigger);
                } else {
                    bot.moveArm(0);
                }
                if (bot.isLiftInLimits(gamepad2)) {
                    if (-gamepad2.right_stick_y > 0) {
                        bot.lift(-gamepad2.right_stick_y * 0.7);
                    } else if (-gamepad2.right_stick_y < 0) {
                        bot.lift(-gamepad2.right_stick_y * 0.5);
                    } else {
                        bot.lift(0);
                    }
                } else {
                    bot.lift(0);
                }
            } else {
                bot.update();
            }
            if (bot.isPulling) {
                bot.intake(0.5);
            } else {
                bot.intake(0);
            }
            if (gamepad1.right_bumper) {
                bot.isPulling = true;
            } else if (gamepad1.left_bumper) {
                bot.intake(-0.5);
                bot.isPulling = false;
            } else if (bot.isStoneLoaded()) {
                bot.isPulling = false;
            }
            if (gamepad1.x) {
                bot.t.reset();
                bot.pull.start();
            }
            if (gamepad2.x) {
                bot.t.reset();
                bot.withdraw.start();
            }
            if (gamepad2.y) {
                bot.place.start();
            }
            if (gamepad2.right_bumper) {
                bot.grab(0);
            } else if (gamepad2.left_bumper) {
                bot.grab(0.7);
            }
            if (gamepad2.right_trigger > 0) {
                bot.flip(0);
            } else if (gamepad2.left_trigger > 0) {
                bot.flip(1);
            }
            if (gamepad2.dpad_up) {
                bot.capTurn(0.15);
            } else if (gamepad2.dpad_down) {
                bot.capTurn(0.8);
            }
            telemetry.addData("Height", bot.getLiftHeight());
            //telemetry.addData("Pos", "{L1, R1, R2, L2} = %d, %d, %d, %d", bot.l1.getCurrentPosition(),bot.r1.getCurrentPosition(), bot.r2.getCurrentPosition(), bot.l2.getCurrentPosition());
            telemetry.update();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    };

    @Override
    public void init() {
        bot.init(hardwareMap);
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
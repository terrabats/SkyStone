package event;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import global.Helper;

@TeleOp(name = "TerraEventGo", group = "new")
public class TerraEventOp extends OpMode {
    public TerraEventBot bot = new TerraEventBot();
    public Helper h = new Helper();

    @Override
    public void init() {
        bot.init(hardwareMap);
    }

    @Override
    public void loop() {
        bot.move(h.calcPow(-gamepad1.right_stick_y), h.calcPow(gamepad1.right_stick_x), h.calcPow(gamepad1.left_stick_x));
    }
}
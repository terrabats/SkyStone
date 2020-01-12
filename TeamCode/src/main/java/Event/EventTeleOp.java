package Event;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Event.TeleEvent;

@TeleOp(name = "Terrabat", group = "new")
public class EventTeleOp extends OpMode{

    public TeleEvent bot = new TeleEvent();

    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        bot.init(hardwareMap);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        bot.move(-gamepad1.right_stick_y,gamepad1.right_stick_x, gamepad1.left_stick_x);
    }

    @Override
    public void stop() {

    }
}

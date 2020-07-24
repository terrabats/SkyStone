package event;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Disabled
public class TeleEvent {
    public DcMotor left = null;
    public DcMotor right = null;
    public DcMotor front = null;


    public HardwareMap hwMap = null;

    public void init(HardwareMap hardwareMap){
        hwMap = hardwareMap;

        left = hwMap.get(DcMotor.class, "left");
        front = hwMap.get(DcMotor.class, "front");
        right = hwMap.get(DcMotor.class, "right");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);
        front.setDirection(DcMotorSimple.Direction.FORWARD);

        left.setPower(0);
        right.setPower(0);
        front.setPower(0);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(double y, double x, double t) {
        left.setPower(y - x - t);
        right.setPower(y + x + t);
    }
}

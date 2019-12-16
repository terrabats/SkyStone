package event;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class TerraEventBot {

    public DcMotor l1 = null;
    public DcMotor l2 = null;
    public DcMotor r1 = null;
    public DcMotor r2 = null;
    HardwareMap hwMap;


    public void init(HardwareMap hardwareMap) {
        hwMap = hardwareMap;

        l1 = hwMap.get(DcMotor.class, "l1");
        l2 = hwMap.get(DcMotor.class, "l2");
        r1 = hwMap.get(DcMotor.class, "r1");
        r2 = hwMap.get(DcMotor.class, "r2");

        l1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        l1.setDirection(DcMotor.Direction.FORWARD);
        l2.setDirection(DcMotor.Direction.FORWARD);
        r1.setDirection(DcMotor.Direction.REVERSE);
        r2.setDirection(DcMotor.Direction.REVERSE);

        l1.setPower(0);
        l2.setPower(0);
        r1.setPower(0);
        r2.setPower(0);
    }

    public void move(double y, double x, double t) {
        l1.setPower(y + x + t);
        r1.setPower(y - x - t);
        r2.setPower(-y + x - t);
        l2.setPower(-y - x + t);
    }
}
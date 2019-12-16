package global;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

import teleUtil.AutoModule;
import teleUtil.Limits;


public class TerraBot {

    public DcMotor l1 = null;
    public DcMotor l2 = null;
    public DcMotor r1 = null;
    public DcMotor r2 = null;
    public DcMotor arm = null;
    public DcMotor rin = null;
    public DcMotor lin = null;
    public DcMotor lift = null;

    public Servo f = null;
    public Servo g = null;
    public Servo c = null;
    public Servo cp = null;

    public BNO055IMU gyro;
    public DistanceSensor ds;
    public DistanceSensor cs;
    public DistanceSensor as;

    WebcamName wc = null;


    public int heading = 0;
    public int lastAngle = 0;
    public boolean isPulling = false;

    public HardwareMap hwMap = null;
    public ElapsedTime t = new ElapsedTime();
    public Limits lim = new Limits();
    public Helper h = new Helper();
    final double MAX_LIFT = 17.5;
    final double MIN_LIFT = 2.8;
    public AutoModule withdraw = new AutoModule();
    public AutoModule pull = new AutoModule();
    public AutoModule place = new AutoModule();


    public void init(HardwareMap hardwareMap) {
        hwMap = hardwareMap;

        l1 = hwMap.get(DcMotor.class, "l1");
        l2 = hwMap.get(DcMotor.class, "l2");
        r1 = hwMap.get(DcMotor.class, "r1");
        r2 = hwMap.get(DcMotor.class, "r2");
        arm = hwMap.get(DcMotor.class, "arm");
        rin = hwMap.get(DcMotor.class, "rin");
        lin = hwMap.get(DcMotor.class, "lin");
        lift = hwMap.get(DcMotor.class, "lift");
        f = hwMap.get(Servo.class, "f");
        g = hwMap.get(Servo.class, "g");
        c = hwMap.get(Servo.class,"c");
        cp = hwMap.get(Servo.class, "cp");
        gyro = hwMap.get(BNO055IMU.class, "imu");
        ds = hwMap.get(DistanceSensor.class, "ds");
        cs = hwMap.get(DistanceSensor.class, "cs");
        as = hwMap.get(DistanceSensor.class, "as");
        wc = hwMap.get(WebcamName.class, "wc");

        l1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        l1.setDirection(DcMotor.Direction.FORWARD);
        l2.setDirection(DcMotor.Direction.FORWARD);
        r1.setDirection(DcMotor.Direction.REVERSE);
        r2.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.REVERSE);
        rin.setDirection(DcMotor.Direction.REVERSE);
        lin.setDirection(DcMotor.Direction.FORWARD);
        lift.setDirection(DcMotor.Direction.REVERSE);
        f.setDirection(Servo.Direction.REVERSE);
        g.setDirection(Servo.Direction.FORWARD);
        c.setDirection(Servo.Direction.REVERSE);
        cp.setDirection(Servo.Direction.REVERSE);

        l1.setPower(0);
        l2.setPower(0);
        r1.setPower(0);
        r2.setPower(0);
        arm.setPower(0);
        rin.setPower(0);
        lin.setPower(0);
        lift.setPower(0);

        f.setPosition(0);
        g.setPosition(0.7);
        cturn(90);
        cp.setPosition(0.15);

        h.resetEncoder(lift);
        h.resetEncoder(arm);
        h.resetEncoder(l1);
        h.resetEncoder(l2);
        h.resetEncoder(r1);
        h.resetEncoder(r2);
        h.initGyro(gyro);

        h.defineWithdraw(this);
        h.definePull(this);
        h.definePlace(this);

        lim.addLimit(lift, MIN_LIFT, MAX_LIFT);
    }

    public int getHeading() {
        int currentangle = (int) gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        int deltaAngle = currentangle - lastAngle;
        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;
        heading += deltaAngle;
        lastAngle = currentangle;
        return heading;
    }

    public void resetGyro() {
        lastAngle = (int) gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        heading = 0;
    }

    public void move(double y, double x, double t) {
        l1.setPower(y + x + t);
        r1.setPower(y - x - t);
        r2.setPower(-y + x - t);
        l2.setPower(-y - x + t);
    }


    public void moveArm(double p) {
        arm.setPower(p);
    }

    public void intake(double p) {
        rin.setPower(p);
        lin.setPower(p);
    }

    public void lift(double p) {
        lift.setPower(p);
    }

    public void grab(double pos) {
        g.setPosition(pos);
    }

    public void flip(double pos) {
        f.setPosition(pos);
    }

    public void cturn(double deg) { c.setPosition(((deg-20)/240));}

    public void capTurn(double pos){cp.setPosition(pos);}

    public double getcpos(){return (c.getPosition()*240)+20;}

    public double getArmPos() {
        return arm.getCurrentPosition() * h.INTAKE_DEG_PER_TICK;
    }

    public boolean isLiftInLimits(Gamepad g2) { return lim.isInLimits(g2, lift, getLiftHeight()); }

    public double getLiftHeight() { return ds.getDistance(DistanceUnit.INCH); }

    public double getArmDis() { return as.getDistance(DistanceUnit.INCH); }

    public boolean isStoneLoaded() { return h.isStone(cs.getDistance(DistanceUnit.INCH), 0.5); }


    public void update() {
        withdraw.update(h.dynamicsForWithdraw(this));
        pull.update(h.dynamicsForPull(this));
        place.update(h.dynamicsForPlace(this));
    }


}
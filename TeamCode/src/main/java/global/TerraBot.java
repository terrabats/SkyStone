package global;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import teleFunctions.AutoModule;
import teleFunctions.Limits;


public class TerraBot {

    public DcMotor l1 = null;
    public DcMotor l2 = null;
    public DcMotor r1 = null;
    public DcMotor r2 = null;

    public DcMotor rin = null;
    public DcMotor lin = null;
    public DcMotor rft = null;
    public DcMotor lft = null;

    public Servo f1 = null;
    public Servo f2 = null;
    public Servo g = null;
    public Servo fg1 = null;
    public Servo fg2 = null;

    public DistanceSensor lh = null;
    public DistanceSensor ss = null;

    public HardwareMap hwMap = null;
    public ElapsedTime t = new ElapsedTime();
    public ElapsedTime t1 = new ElapsedTime();
    public Helper h = new Helper();
    public Limits lim = new Limits();
    public AutoModule flipOut = new AutoModule();
    public AutoModule grab = new AutoModule();
    public AutoModule place = new AutoModule();
    public AutoModule retract = new AutoModule();

    public boolean isPulling = false;

    public final double minH = 1;
    public final double maxH = 27;

    public final double sp = 0.15;




    public void init(HardwareMap hardwareMap) {
        hwMap = hardwareMap;

        l1 = hwMap.get(DcMotor.class, "l1");
        l2 = hwMap.get(DcMotor.class, "l2");
        r1 = hwMap.get(DcMotor.class, "r1");
        r2 = hwMap.get(DcMotor.class, "r2");
        rin = hwMap.get(DcMotor.class, "rin");
        lin = hwMap.get(DcMotor.class, "lin");
        rft = hwMap.get(DcMotor.class, "rft");
        lft = hwMap.get(DcMotor.class, "lft");
        f1 = hwMap.get(Servo.class, "f1");
        f2 = hwMap.get(Servo.class, "f2");
        g = hwMap.get(Servo.class, "g");
        fg1 = hwMap.get(Servo.class, "fg1");
        fg2 = hwMap.get(Servo.class, "fg2");
        lh = hwMap.get(DistanceSensor.class, "lh");
        ss = hwMap.get(DistanceSensor.class, "ss");


        l1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        l1.setDirection(DcMotor.Direction.REVERSE);
        l2.setDirection(DcMotor.Direction.FORWARD);
        r1.setDirection(DcMotor.Direction.FORWARD);
        r2.setDirection(DcMotor.Direction.REVERSE);
        rin.setDirection(DcMotor.Direction.FORWARD);
        lin.setDirection(DcMotor.Direction.REVERSE);
        rft.setDirection(DcMotor.Direction.REVERSE);
        lft.setDirection(DcMotor.Direction.FORWARD);

        f1.setDirection(Servo.Direction.REVERSE);
        f2.setDirection(Servo.Direction.FORWARD);
        g.setDirection(Servo.Direction.REVERSE);
        fg1.setDirection(Servo.Direction.FORWARD);
        fg2.setDirection(Servo.Direction.REVERSE);

        l1.setPower(0);
        l2.setPower(0);
        r1.setPower(0);
        r2.setPower(0);
        rin.setPower(0);
        lin.setPower(0);
        rft.setPower(0);
        lft.setPower(0);

        f1.setPosition(sp);
        f2.setPosition(sp);
        g.setPosition(0);
        fg1.setPosition(0);
        fg2.setPosition(0);

        lim.addLimit(rft, minH, maxH);

        l1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        l2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        h.defineFlipOut(this);
        h.defineGrab(this);
        h.definePlace(this);




    }

    public void move(double y, double x, double t) {
        l1.setPower(y - x - t);
        r1.setPower(y + x + t);
        r2.setPower(-y + x - t);
        l2.setPower(-y - x + t);
    }

    public void intake(double p) {
        rin.setPower(p);
        lin.setPower(p);
    }

    public void lift(double p) {
        rft.setPower(p);
        lft.setPower(p);
    }

    public void flip(double p1,double p2){
        f1.setPosition(p1);
        f2.setPosition(p2);
    }
    public void grab(double pos){
        g.setPosition(pos);
    }

    public void foundationGrab(double pos){
        fg1.setPosition(pos);
        fg2.setPosition(pos);
    }
    public double getLiftHeight(){
        return lh.getDistance(DistanceUnit.INCH);
    }
    public double getStoneDistance(){
        return ss.getDistance(DistanceUnit.INCH);
    }
    public boolean isLiftInLimits(Gamepad g2){
        return lim.isInLimits(g2, rft, getLiftHeight());
    }
    public double getCenterEncoder(){
        return -l2.getCurrentPosition() * 1.0;
    }
    public double getLeftEncoder(){
        return -r1.getCurrentPosition() * 1.0;
    }
    public double getRightEncoder(){
        return -l1.getCurrentPosition() * 1.0;
    }

    public boolean noAutoModules(){
        return !flipOut.executing && !grab.executing && !place.executing;
    }
    public void update(){
        flipOut.update(h.dynamicsForFlipOut(this));
        grab.update(h.dynamicsGrab(this));
        place.update(h.dynamicsPlace(this));
    }

}
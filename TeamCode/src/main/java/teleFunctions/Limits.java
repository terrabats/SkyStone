package teleFunctions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

public class Limits {

    ArrayList<DcMotor> motors = new ArrayList<>();
    ArrayList<Double> lower = new ArrayList<>();
    ArrayList<Double> upper = new ArrayList<>();


    public void addLimit(DcMotor d, double low, double high) {
        motors.add(d);
        lower.add(low);
        upper.add(high);
    }

    public boolean isInLimits(Gamepad g2, DcMotor d, double pos) {
        int i = motors.indexOf(d);
        double lower_bound = lower.get(i);
        double upper_bound = upper.get(i);



        MotionMode dir = null;

        boolean inLim = true;

        if (pos <= lower_bound) {
            dir = MotionMode.FORWARD;
            inLim = false;
        } else if (pos >= upper_bound) {
            dir = MotionMode.REVERSE;
            inLim = false;
        }

        if (!inLim) {
            if (dir == getControllerDirection(g2)) {
                inLim = true;
            }
        }
        return inLim;
    }

    public MotionMode getControllerDirection(Gamepad in) {
        if (-in.right_stick_y > 0) {
            return MotionMode.FORWARD;
        } else if (in.right_stick_y > 0) {
            return MotionMode.REVERSE;
        } else {
            return MotionMode.STATIC;
        }
    }

    public enum MotionMode {
        FORWARD,
        REVERSE,
        STATIC
    }
}
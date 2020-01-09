package autoUtil;

import java.util.ArrayList;

import global.CodeSeg;

public class Path {
    int count = 1;

    ArrayList<Double> XPoses = new ArrayList<>();
    ArrayList<Double> YPoses = new ArrayList<>();
    ArrayList<Double> HPoses = new ArrayList<>();
    ArrayList<CodeSeg> Customs = new ArrayList<>();

    PID XControl = new PID();
    PID YControl = new PID();
    PID HControl = new PID();

    final double ACCURACY = 0.5;

    double XError = 0;
    double YError = 0;
    double HError = 0;

    double XVelocity = 0;
    double YVelocity= 0;
    double HVelocity = 0;



    boolean isDone = false;

    public void init() {
        XPoses.add(0.0);
        YPoses.add(0.0);
        HPoses.add(0.0);
        Customs.add(null);


        XControl.setCoeffecients(0.13,0.2);
        YControl.setCoeffecients(0.12,0.19);
        HControl.setCoeffecients(0,0);
    }

    public double[] update(Odometry odometry) {
        double[] currentPose = odometry.getGlobalPose();
        if (count < XPoses.size()) {
            XError = currentPose[0] - XPoses.get(count);
            YError = currentPose[1] - YPoses.get(count);
            HError = currentPose[2] - HPoses.get(count);
            XVelocity = odometry.ticksToInches(odometry.strafe);
            YVelocity = odometry.ticksToInches(odometry.forward);
            HVelocity = odometry.inchesToDegrees(odometry.ticksToInches(odometry.turn));

            //isEnd();
            return calcPowers(currentPose);
        } else {
            double[] out = new double[3];
            out[0] = 0;
            out[1] = 0;
            out[2] = 0;
            isDone = true;
            return out;
        }
    }

    public double[] calcPowers(double[] currentPose) {
        double[] out = new double[3];
        if(count < XPoses.size()) {
            if (Customs.get(count) == null) {
                double targetTheta = Math.atan2(YError, XError);
                double robotTheta = Math.toRadians(currentPose[2]);
                out[0] = -Math.cos(targetTheta - robotTheta) * XControl.getPower(XError,XVelocity);
                out[1] = -Math.sin(targetTheta - robotTheta) * YControl.getPower(YError,YVelocity);
                out[2] = Math.signum(HError) * HControl.getPower(HError,HVelocity);
            } else {
                out[0] = 0;
                out[1] = 0;
                out[2] = 0;
                Customs.get(count).run();
                count++;
            }
        }
        return out;
    }



    public void isEnd() {
        if (Math.abs(XError) < ACCURACY && Math.abs(YError) < ACCURACY && Math.abs(HError) < ACCURACY) {
            count++;
        }
    }

    public void addPose(double y, double x, double h) {
        XPoses.add(XPoses.get(XPoses.size() - 1) + x);
        YPoses.add(YPoses.get(YPoses.size() - 1) + y);
        HPoses.add(HPoses.get(HPoses.size() - 1) + h);
        Customs.add(null);
    }

    public void addCustom(CodeSeg c) {
        XPoses.add(XPoses.get(XPoses.size() - 1));
        YPoses.add(YPoses.get(YPoses.size() - 1));
        HPoses.add(HPoses.get(HPoses.size() - 1));
        Customs.add(c);

    }

    public boolean isExecuting() {
        return !isDone;
    }
}
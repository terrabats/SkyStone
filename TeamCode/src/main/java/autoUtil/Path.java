package autoUtil;

import java.util.ArrayList;

import global.CodeSeg;

public class Path {
    int count = 1;

    ArrayList<Double> XPoses = new ArrayList<>();
    ArrayList<Double> YPoses = new ArrayList<>();
    ArrayList<Double> HPoses = new ArrayList<>();
    ArrayList<CodeSeg> Customs = new ArrayList<>();

    final double ACCURACY = 1;

    double XError = 0;
    double YError = 0;
    double HError = 0;

    boolean isDone = false;

    public void init() {
        XPoses.add(0.0);
        YPoses.add(0.0);
        HPoses.add(0.0);
        Customs.add(null);
    }

    public double[] update(double[] currentPose) {
        if (count < XPoses.size()) {
            XError = currentPose[0] - XPoses.get(count);
            YError = currentPose[1] - YPoses.get(count);
            HError = currentPose[2] - HPoses.get(count);
            isEnd();
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
        if (Customs.get(count) == null) {
            double targetTheta = Math.atan2(YError, XError);
            double robotTheta = Math.toRadians(currentPose[2]);
            out[0] = Math.cos(targetTheta - robotTheta) * 0.2;
            out[1] = -Math.sin(targetTheta - robotTheta) * 0.2;
            out[2] = Math.signum(HError) * 0.05;
        } else {
            Customs.get(count).run();
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
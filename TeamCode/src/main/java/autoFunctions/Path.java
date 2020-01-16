package autoFunctions;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

import global.Helper;
import util.CodeSeg;
import util.Vector;

public class Path {
    int count = 1;

    public ArrayList<Double> XPoses = new ArrayList<>();
    public ArrayList<Double> YPoses = new ArrayList<>();
    public ArrayList<Double> HPoses = new ArrayList<>();
    public ArrayList<CodeSeg> Customs = new ArrayList<>();


    PID XControl = new PID();
    PID YControl = new PID();
    PID HControl = new PID();

    ElapsedTime t = new ElapsedTime();

    Helper h = new Helper();

    public double XACCURACY = 1;
    public double YACCURACY = 1;
    public double HACCURACY = 5;
    final double MINVEL = 0.05;
    final double STALL = 5;

    double XError = 0;
    double YError = 0;
    double HError = 0;

    double XVelocity = 0;
    double YVelocity= 0;
    double HVelocity = 0;

    boolean waiting = false;



    boolean isDone = false;

    public Path() {
        XPoses.add(0.0);
        YPoses.add(0.0);
        HPoses.add(0.0);
        Customs.add(null);

        XControl.setCoeffecients(0.16,0.21);
        YControl.setCoeffecients(0.14,0.19);
        HControl.setCoeffecients(0.025,0.03);
    }

    public void setCoefficents(double kx, double ky, double kh, double dx, double dy, double dh){
        XControl.setCoeffecients(kx,dx);
        YControl.setCoeffecients(ky,dy);
        HControl.setCoeffecients(kh,dh);
    }

    public void continuePath(Path p){
        XPoses.set(0,p.XPoses.get(p.XPoses.size()-1));
        YPoses.set(0,p.YPoses.get(p.YPoses.size()-1));
        HPoses.set(0,p.HPoses.get(p.HPoses.size()-1));
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
        if(count < XPoses.size()) {
            if (Customs.get(count) == null) {

                double robotTheta = currentPose[2];

                Vector mv = new Vector(XError,YError);
                mv = mv.getRotatedVector(-robotTheta);

                double[] rawPow = new double[3];
                rawPow[0] = XControl.getPower(mv.x,XVelocity);
                rawPow[1] = YControl.getPower(mv.y,YVelocity);
                rawPow[2] = HControl.getPower(HError,HVelocity);
                double[] normPow = h.normalize(rawPow);

                out[0] = -Math.signum(mv.x) * normPow[0];
                out[1] = -Math.signum(mv.y) * normPow[1];
                out[2] = Math.signum(HError) * normPow[2];

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
        double averageVel = h.average(XVelocity,YVelocity,HVelocity);
//        if(averageVel < MINVEL){
//            if(!waiting) {
//                t.reset();
//                waiting = true;
//            }
//        }else{
//            waiting = false;
//        }
//
//        if(t.seconds() > STALL && waiting){
//            count++;
//            waiting = false;
//        }

        if(count == XPoses.size()-1){
            if (Math.abs(XError) < XACCURACY && Math.abs(YError) < YACCURACY && Math.abs(HError) < HACCURACY && averageVel < MINVEL) {
                count++;
            }
        }else {
            if (Math.abs(XError) < XACCURACY && Math.abs(YError) < YACCURACY && Math.abs(HError) < HACCURACY) {
                count++;
            }
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
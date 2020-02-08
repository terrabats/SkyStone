package autoFunctions;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.time.chrono.MinguoChronology;
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

    public double XACCURACY = 0.5;
    public double YACCURACY = 0.5;
    public double HACCURACY = 2;
    final double MINVEL = 0.05;

    double XError = 0;
    double YError = 0;
    double HError = 0;

    double XVelocity = 0;
    double YVelocity= 0;
    double HVelocity = 0;

    double XErrorSum = 0;
    double YErrorSum = 0;
    double HErrorSum = 0;

    public double xp = 0.2;
    public double xd = 0.6;

    public double yp = 0.12;
    public double yd = 0.4;

    public double hp = 0.03;
    public double hd = 0.03;





    boolean isDone = false;

    public Path() {
        XPoses.add(0.0);
        YPoses.add(0.0);
        HPoses.add(0.0);
        Customs.add(null);
        resetCoeffeicents();
    }

    public void setCoefficents(double kx, double ky, double kh, double dx, double dy, double dh, double ix, double iy, double ih){
        XControl.setCoeffecients(kx,dx,ix);
        YControl.setCoeffecients(ky,dy, iy);
        HControl.setCoeffecients(kh,dh, ih);
    }
    public void multiplyKD(double scale){
        setCoefficents(xp*scale, yp*scale, hp*scale, xd*scale, yd*scale, hd*scale, XControl.Ki,YControl.Ki,HControl.Ki);
    }
    public void addI(double val){
        setCoefficents(XControl.Kp, YControl.Kp, HControl.Kp, XControl.Kd, YControl.Kd, HControl.Kd, val,val*0.9,val*0.25);
    }

    public void resetCoeffeicents(){
        XControl.setCoeffecients(xp,xd, 0.0);
        YControl.setCoeffecients(yp,yd, 0.0);
        HControl.setCoeffecients(hp,hd, 0.0);
    }

    public void continuePath(Path p){
        XPoses.set(0,p.XPoses.get(p.XPoses.size()-1));
        YPoses.set(0,p.YPoses.get(p.YPoses.size()-1));
        HPoses.set(0,p.HPoses.get(p.HPoses.size()-1));
    }

    public double[] update(Odometry odometry) {
        double[] currentPose = odometry.getGlobalPose();
        if (count < XPoses.size()) {
            double pows[] = calcPowers(currentPose);
            XError = currentPose[0] - XPoses.get(count);
            YError = currentPose[1] - YPoses.get(count);
            HError = currentPose[2] - HPoses.get(count);
            XVelocity = odometry.ticksToInches(odometry.strafe);
            YVelocity = odometry.ticksToInches(odometry.forward);
            HVelocity = odometry.inchesToDegrees(odometry.ticksToInches(odometry.turn));
            isEnd();
            return pows;
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

                if(XControl.Ki != 0.0){
                    XErrorSum += mv.x*0.1;
                    YErrorSum += mv.y*0.1;
                    HErrorSum += HError*0.1;
                }

                double[] rawPow = new double[3];
                rawPow[0] = XControl.getPower(mv.x,XVelocity, XErrorSum);
                rawPow[1] = YControl.getPower(mv.y,YVelocity, YErrorSum);
                rawPow[2] = HControl.getPower(HError,HVelocity, HErrorSum);
                double[] normPow = h.normalize(rawPow);

                out[0] = -Math.signum(mv.x) * normPow[0];
                out[1] = -Math.signum(mv.y) * normPow[1];
                out[2] = Math.signum(HError) * normPow[2];

            } else {
                out[0] = 0;
                out[1] = 0;
                out[2] = 0;
                Customs.get(count).run();
                resetCoeffeicents();
                resetSums();
                count++;

            }
        }
        return out;
    }



    public void isEnd() {
        if(count < XPoses.size()) {
            double averageVel = h.average(XVelocity, YVelocity, HVelocity);
            if (averageVel < MINVEL && Math.abs(XError) < (XACCURACY * 4) && Math.abs(YError) < (YACCURACY * 4) && Math.abs(HError) < (HACCURACY * 4)) {
                addI(0.08);
                multiplyKD(1.2);
            }
            if (Math.abs(XError) < XACCURACY && Math.abs(YError) < YACCURACY && Math.abs(HError) < HACCURACY) {
                count++;
                resetCoeffeicents();
                resetSums();
            }
        }
    }

    public void resetSums(){
        XErrorSum = 0;
        YErrorSum = 0;
        HErrorSum = 0;
    }
    public void addPose(double y, double x, double h) {
        XPoses.add(XPoses.get(XPoses.size() - 1) + x);
        YPoses.add(YPoses.get(YPoses.size() - 1) + y);
        HPoses.add(HPoses.get(HPoses.size() - 1) + h*1.09);
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
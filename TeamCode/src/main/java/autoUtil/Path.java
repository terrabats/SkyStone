package autoUtil;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

import global.CodeSeg;
import global.Helper;
import autoUtil.TargetDetection.stoneP;
import global.TerraBot;

public class Path {
    Helper h = new Helper();
    ArrayList<Double> Lpath = new ArrayList<>();
    ArrayList<Double> Rpath = new ArrayList<>();
    ArrayList<Double> L2path = new ArrayList<>();
    ArrayList<Double> R2path = new ArrayList<>();

    ArrayList<Double> forPows = new ArrayList<>();
    ArrayList<Double> strPows = new ArrayList<>();
    ArrayList<Double> turPows = new ArrayList<>();
    ArrayList<Double> powers = new ArrayList<>();

    ArrayList<CodeSeg> customs = new ArrayList<>();

    public double Ltotal = 0;
    public double Rtotal = 0;
    public double R2total = 0;
    public double L2total = 0;

    public double FORWARD_C = 0;
    public double STRAFE_C = 0;
    public double TURN_C = 0;
    public double FORWARD_A = 0;
    public double STRAFE_A = 0;
    public double TURN_A = 0;

    public int segNum = 0;

    public boolean isExecuting = true;

    public Path(){

    }

    public void setConstants(double fc, double sc, double tc, double fa, double sa, double ta){
        this.FORWARD_C = fc;
        this.STRAFE_C = sc;
        this.TURN_C = tc;
        this.FORWARD_A = fa;
        this.STRAFE_A = sa;
        this.TURN_A = ta;
    }

    public void addLine(double d, double s, double deg, double power){
        double p = h.POWER_C;

        int robotForDis = (int) (d * h.TICKS_FOR_MOVE);
        int robotStrDis = (int) (s * h.TICKS_FOR_MOVE * h.STRAFE_CONSTANT);
        int robotTurDis = (int) (deg * h.TICKS_FOR_MOVE * h.TURN_CONSTANT);
        double ang = Math.atan2(robotForDis, robotStrDis);
        double forPow = Math.sin(ang) * p;
        double strPow = Math.cos(ang) * p;
        double turPow = Math.signum(deg) * p;
        double totalDis = Math.sqrt(robotForDis * robotForDis + robotStrDis * robotStrDis);

        double Ldis = (((forPow+strPow)/p) * totalDis) + robotTurDis;
        double Rdis = (((forPow-strPow)/p) * totalDis) - robotTurDis;
        double R2dis = (((-forPow+strPow)/p) * totalDis) - robotTurDis;
        double L2dis = (((-forPow-strPow)/p) * totalDis) + robotTurDis;


        Ltotal += Ldis;
        Rtotal += Rdis;
        R2total += R2dis;
        L2total += L2dis;

        Lpath.add(Ltotal);
        Rpath.add(Rtotal);
        R2path.add(R2total);
        L2path.add(L2total);

        forPows.add(forPow);
        strPows.add(strPow);
        turPows.add(turPow);
        powers.add(power);

        customs.add(null);
    }
    public void addCustomBlock(CodeSeg seg){
        Lpath.add(Ltotal);
        Rpath.add(Rtotal);
        R2path.add(R2total);
        L2path.add(L2total);

        forPows.add(0.0);
        strPows.add(0.0);
        turPows.add(0.0);
        powers.add(0.0);

        customs.add(seg);

    }
    public double[] getPowers(double[] currentPositions, Telemetry telemetry){
        double[] pows = new double[3];
        if(customs.get(segNum) == null) {
            double Ldis = Lpath.get(segNum);
            double Rdis = Rpath.get(segNum);
            double R2dis = R2path.get(segNum);
            double L2dis = L2path.get(segNum);

            double forPow = forPows.get(segNum);
            double strPow = strPows.get(segNum);
            double turPow = turPows.get(segNum);
            double power = powers.get(segNum);

            double Lerr = Ldis - currentPositions[0];
            double Rerr = Rdis - currentPositions[1];
            double R2err = R2dis + currentPositions[2];
            double L2err = L2dis + currentPositions[3];

            double R2err2 = R2dis - currentPositions[2];
            double L2err2 = L2dis - currentPositions[3];

            double forErr = (+Lerr + Rerr - R2err - L2err) / (h.TICKS_FOR_MOVE * 4);
            double strErr = (+Lerr - Rerr + R2err2 - L2err2) / (h.TICKS_FOR_MOVE * 4);
            double turErr = (+Lerr - Rerr - R2err2 + L2err2) / (h.TICKS_FOR_MOVE * 4);

            double signTurn = Math.signum(turErr / turPow);
            double signStrafe = Math.signum(strErr / strPow);
            double signForward = Math.signum(forErr / forPow);

            pows[0] = Range.clip((signForward * forPow) + (forErr * FORWARD_C), -power, power);
            pows[1] = Range.clip(((signStrafe * strPow) + (strErr * STRAFE_C)), -power, power);
            pows[2] = Range.clip(((signTurn * turPow) + (turErr * TURN_C)), -power, power);

            if (Math.abs(forErr) < FORWARD_A && Math.abs(strErr) < STRAFE_A && Math.abs(turErr) < TURN_A) {
                segNum++;
            }
            telemetry.addData("errF", forErr);
            telemetry.addData("errS", strErr);
            telemetry.addData("errT", turErr);
            telemetry.update();
            if (segNum == powers.size()) {
                isExecuting = false;
            }
            return pows;
        }else{
            customs.get(segNum).run();
            segNum++;
            if (segNum == powers.size()) {
                isExecuting = false;
            }
            return null;
        }
    }

    public boolean isExecuting(){
        return isExecuting;
    }

}
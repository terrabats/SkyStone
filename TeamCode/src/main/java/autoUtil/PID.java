package autoUtil;

public class PID {
    public double Kp = 0;
    public double Kd = 0;


    public void setCoeffecients(double k, double d){
        Kp = k;
        Kd = d;
    }
    public double getPower(double ce, double cv){
        ce = abs(ce);
        cv = abs(cv);

        return Kp*abs(ce) - Kd*abs(cv);
    }
    public double abs(double in){
        return Math.abs(in);
    }
}

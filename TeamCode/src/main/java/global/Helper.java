package global;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.slf4j.helpers.Util;

import java.util.ArrayList;

import teleFunctions.Stage;
import util.CodeSeg;

public class Helper {


    public final double MIN_STONE_DIS = 5;
    public final double deadRange = 0.0;
    public final String VUFORIA_KEY = "AdfjEqf/////AAABmUFlTr2/r0XAj6nkD8iAbHMf9l6LwV12Hw/ie9OuGUT4yTUjukPdz9SlCFs4axhhmCgHvzOeNhrjwoIbSCn0kCWxpfHAV9kakdMwFr6ysGpuQ9xh2xlICm2jXxVfqYKGlWm3IFk1GuGR7N5jt071axc/xFBQ0CntpghV6siUTyuD4du5rKhqO1pp4hILhJLF5I6LbkiXN93utfwje/8kEB3+V4TI+/rVj9W+c7z26rAQ34URhQ5AcPlhIfjLyUcTW15+UylM0dxGiMpQprreFVaOk32O2epod9yIB5zgSin1bd7PiCXHbPxhVhMz0cMNRJY1LLfuDru3npuemePUkpSOp5SFbuGjzso9hDA/6V3L";

    public boolean left = false;

    public double calcPow(double in, double maxPow, TerraBot bot){
        //return Math.signum(in)*yint+(Math.pow(in,exp)*(1-yint));
        if(bot.highGear) {
            if (Math.abs(in) > deadRange) {
                return maxPow * Math.signum(in);
            } else {
                return 0;
            }
        }else{
            return maxPow * in;
        }
    }
    public double average(double vx, double vy, double vh){
        return (vx+vy+vh)/3;
    }

    public void initGyro(BNO055IMU g){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        g.initialize(parameters);
    }
    public boolean isStoneDetected(double dis){
        return dis < MIN_STONE_DIS;
    }
    public double[] normalize(double in[]){
        double[] out = new double[3];
        double sum = in[0]+in[1]+in[2];
        if(sum > 1){
            out[0] = in[0]/sum;
            out[1] = in[1]/sum;
            out[2] = in[2]/sum;
        }else{
            out = in;
        }
        return out;
    }
    public int findMin(double[] in){
        double min = 100;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if(in[i] <  min){ min = in[i];}
        }
        for (int i = 0; i < 3; i++) {
            if(in[i] == min){
                index = i;
                break;
            }
        }
        return index;
    }
    public float[] rgbToHSV(int pix){
        int r = Color.red(pix);
        int g = Color.green(pix);
        int b = Color.blue(pix);
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        return hsv;
    }


    public void defineGrab(final TerraBot bot) {
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.15,0.15);
                bot.intake(0);
                bot.isPulling = false;
                return time > 0.2;
            }
        });
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.grab(1);
                return time > 0.6;
            }
        });
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(bot.sp, bot.sp);
                return time > 0.9;
            }
        });
    }
    public void defineRetract(final TerraBot bot){
        bot.retract.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.grab(bot.sp2);
                return time > 0.5;
            }
        });
        bot.retract.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(bot.sp,bot.sp);
                return time > 1.2;
            }
        });
        bot.retract.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.lift(-0.4);
                if(pos < bot.minH){
                    bot.lift(0);
                }
                return pos < bot.minH;
            }
        });

    }


    public ArrayList<Double> dynamicsGrab(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.t1.seconds());
        return dynamics;
    }
    public ArrayList<Double> dynamicsRetract(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.getLiftHeight());
        return dynamics;
    }





}

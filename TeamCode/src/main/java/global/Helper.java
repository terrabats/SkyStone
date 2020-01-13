package global;

import android.graphics.Color;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.slf4j.helpers.Util;

import java.util.ArrayList;

import teleFunctions.Stage;
import util.CodeSeg;

public class Helper {
    public final double LIFT_CM_PER_TICK = 0.044879895;
    public final double INTAKE_DEG_PER_TICK = 0.21428571428;
    public final double TICKS_FOR_MOVE = 36.96032508;
    public final double STRAFE_CONSTANT = 1.315;
    public final double TURN_CONSTANT = 0.291;
    public final double INCHES_PER_SEC = 45;
    public final double POWER_C = 0.15;
    public final double TIMEOUT = 2;
    public double oldHeight = 0;
    private double exp = 11;
    private double yint = 0.25;
    public VuforiaLocalizer vuforia;
    ElapsedTime timer = new ElapsedTime();
    public final String VUFORIA_KEY = "AdfjEqf/////AAABmUFlTr2/r0XAj6nkD8iAbHMf9l6LwV12Hw/ie9OuGUT4yTUjukPdz9SlCFs4axhhmCgHvzOeNhrjwoIbSCn0kCWxpfHAV9kakdMwFr6ysGpuQ9xh2xlICm2jXxVfqYKGlWm3IFk1GuGR7N5jt071axc/xFBQ0CntpghV6siUTyuD4du5rKhqO1pp4hILhJLF5I6LbkiXN93utfwje/8kEB3+V4TI+/rVj9W+c7z26rAQ34URhQ5AcPlhIfjLyUcTW15+UylM0dxGiMpQprreFVaOk32O2epod9yIB5zgSin1bd7PiCXHbPxhVhMz0cMNRJY1LLfuDru3npuemePUkpSOp5SFbuGjzso9hDA/6V3L";

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

    public void defineFlipOut(final TerraBot bot) {
        bot.flipOut.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.7, 0.7);
                return time > 1;
            }
        });
        bot.flipOut.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.6,0.8);
                return time > 2;
            }
        });
        bot.flipOut.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.9, 0.9);
                return time > 3;
            }
        });

    }
    public void defineGrab(final TerraBot bot) {
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0,0);
                return time > 1;
            }
        });
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.grab(1);
                return time > 2;
            }
        });
        bot.grab.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.3,0.3);
                return time > 3;
            }
        });
    }
    public void definePlace(final TerraBot bot){
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.lift(0.5);
                bot.t1.reset();
                return pos > 10;
            }
        });
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.lift(0);
                bot.flip(0.7,0.7);
                return time > 1;
            }
        });
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.6,0.8);
                return time > 2;
            }
        });
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.9,0.9);
                return time > 3;
            }
        });

    }
    public void defineRetract(final TerraBot bot){
        bot.retract.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.grab(0);
                return time > 1;
            }
        });
        bot.retract.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(bot.sp,bot.sp);
                return time > 3;
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


    public ArrayList<Double> dynamicsForFlipOut(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.t.seconds());
        return dynamics;
    }
    public ArrayList<Double> dynamicsGrab(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.t1.seconds());
        dynamics.add(bot.t1.seconds());
        return dynamics;
    }
    public ArrayList<Double> dynamicsPlace(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.getStoneDistance());
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

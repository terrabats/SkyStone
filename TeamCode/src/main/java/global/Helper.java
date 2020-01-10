package global;

import android.graphics.Color;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.slf4j.helpers.Util;

import java.util.ArrayList;

import teleFunctions.Stage;

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
            public boolean run(double pos) {
                bot.flip(pos, pos);
                bot.fp += 0.07;
                bot.t.reset();
                return pos > 0.7;
            }
        });
        bot.flipOut.addStage(new Stage() {
            @Override
            public boolean run(double time) {
                bot.flip(0.6,0.8);
                return time > 1;
            }
        });
        bot.flipOut.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.flip(pos, pos);
                bot.fp += 0.05;
                return pos > 1;
            }
        });

    }

    public ArrayList<Double> dynamicsForFlipOut(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.fp);
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.fp);
        return dynamics;
    }




}

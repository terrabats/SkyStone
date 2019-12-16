package global;

import android.hardware.camera2.CameraDevice;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Frame;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;

import autoUtil.TargetDetection;
import teleUtil.Stage;

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
    public static final String VUFORIA_KEY = "AdfjEqf/////AAABmUFlTr2/r0XAj6nkD8iAbHMf9l6LwV12Hw/ie9OuGUT4yTUjukPdz9SlCFs4axhhmCgHvzOeNhrjwoIbSCn0kCWxpfHAV9kakdMwFr6ysGpuQ9xh2xlICm2jXxVfqYKGlWm3IFk1GuGR7N5jt071axc/xFBQ0CntpghV6siUTyuD4du5rKhqO1pp4hILhJLF5I6LbkiXN93utfwje/8kEB3+V4TI+/rVj9W+c7z26rAQ34URhQ5AcPlhIfjLyUcTW15+UylM0dxGiMpQprreFVaOk32O2epod9yIB5zgSin1bd7PiCXHbPxhVhMz0cMNRJY1LLfuDru3npuemePUkpSOp5SFbuGjzso9hDA/6V3L";

    public void initGyro(BNO055IMU g) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        g.initialize(parameters);
    }
    public void initVuforia(LinearOpMode op, TerraBot bot, boolean initMonitor){
        VuforiaLocalizer.Parameters parameters;
        if(initMonitor) {
            int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
            parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        }else{
            parameters = new VuforiaLocalizer.Parameters();
        }
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = bot.wc;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    public VuforiaLocalizer getVuforia(){return vuforia;}

    public void resetEncoder(DcMotor d) {
        d.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void resetAllEncoders(TerraBot bot){
        resetEncoder(bot.l1);
        resetEncoder(bot.r1);
        resetEncoder(bot.r2);
        resetEncoder(bot.l2);
    }

    public double calcPow(double in) {
        if (in > 0) {
            return ((1-yint) * Math.pow(in,exp)) + yint;
        } else if (in < 0) {
            return ((1-yint) * Math.pow(in,exp)) - yint;
        } else {
            return 0;
        }
    }
    public double[] getPos(LinearOpMode op, TargetDetection td){
        double[] pos = null;
        timer.reset();
        while (op.opModeIsActive() && pos == null && timer.seconds() < TIMEOUT) {
            pos = td.getOreintation();
        }
        return pos;
    }
    public boolean inRange(double min, double max, double test){ return (test > min && test < max );}

    public boolean isStone(double dis, double min) {
        return dis < min;
    }

    public void defineWithdraw(final TerraBot bot) {
        bot.withdraw.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.grab(0.7);
                return pos > 0.5;
            }
        });
        bot.withdraw.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.flip(0);
                return pos > 1;
            }
        });
        bot.withdraw.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.lift(-0.5);
                if (pos <= bot.MIN_LIFT) {
                    bot.lift(0);
                    return true;
                }
                return false;
            }
        });
    }

    public void definePull(final TerraBot bot) {
        bot.pull.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.moveArm(0.7);
                bot.intake(0.3);
                if (pos > 1.25) {
                    bot.moveArm(0);
                    bot.intake(0);
                    return true;
                }
                return false;
            }
        });
        bot.pull.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.grab(0);
                return pos > 2.25;
            }
        });
        bot.pull.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.moveArm(-0.8);
                if (pos > 3.3) {
                    bot.moveArm(0);
                    return true;
                }
                return false;
            }
        });
    }

    public void definePlace(final TerraBot bot) {
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.lift(0.9);
                if (pos > 10) {
                    oldHeight = bot.getLiftHeight();
                    return true;
                }
                return false;
            }
        });
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.lift(0.7);
                if ((pos - oldHeight) > 1) {
                    bot.lift(0);
                    return true;
                }
                return false;
            }
        });
        bot.place.addStage(new Stage() {
            @Override
            public boolean run(double pos) {
                bot.flip(1);
                return pos == 1;
            }
        });
    }

    public ArrayList<Double> dynamicsForPull(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.t.seconds());
        return dynamics;
    }

    public ArrayList<Double> dynamicsForWithdraw(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.t.seconds());
        dynamics.add(bot.getLiftHeight());
        return dynamics;
    }

    public ArrayList<Double> dynamicsForPlace(TerraBot bot) {
        ArrayList<Double> dynamics = new ArrayList<>();
        dynamics.add(bot.getArmDis());
        dynamics.add(bot.getLiftHeight());
        dynamics.add(bot.f.getPosition());
        return dynamics;
    }
}

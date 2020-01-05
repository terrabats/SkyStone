
package autoUtil;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import global.Helper;
import global.TerraBot;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.INTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

public class TargetDetection {
//    //Constants
//    private static final float mmPerInch = 25.4f;
//    private static final float mmTargetHeight = (6) * mmPerInch;
//    private static final float stoneZ = 2.00f * mmPerInch;
//    private static final float bridgeZ = 6.42f * mmPerInch;
//    private static final float bridgeY = 23 * mmPerInch;
//    private static final float bridgeX = 5.18f * mmPerInch;
//    private static final float bridgeRotY = 59;
//    private static final float bridgeRotZ = 180;
//    private static final float halfField = 72 * mmPerInch;
//    private static final float quadField = 36 * mmPerInch;
//    //Camera Specific constants
//    final float cfd = -6f * mmPerInch;
//    final float cvd = 9 * mmPerInch;
//    final float cld = 7 * mmPerInch;
//    private final float pxr = 90f;
//    private final float pyr = -90f;
//    private final float pzr = -180f;
//
//
//    private OpenGLMatrix lastLocation = null;
//    private VuforiaLocalizer vuforia = null;
//
//    private boolean targetVisible = false;
//    private Telemetry t;
//    VuforiaTrackables targetsSkyStone;
//    List<VuforiaTrackable> allTrackables = new ArrayList<>();
//    Helper help = new Helper();
//
//    public void init(VuforiaLocalizer v, Telemetry telemetry) {
//        vuforia = v;
//        t = telemetry;
//        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
//        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);stoneTarget.setName("Stone Target");
//        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);blueRearBridge.setName("Blue Rear Bridge");
//        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);redRearBridge.setName("Red Rear Bridge");
//        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);redFrontBridge.setName("Red Front Bridge");
//        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);blueFrontBridge.setName("Blue Front Bridge");
//        VuforiaTrackable red1 = targetsSkyStone.get(5);red1.setName("Red Perimeter 1");
//        VuforiaTrackable red2 = targetsSkyStone.get(6);red2.setName("Red Perimeter 2");
//        VuforiaTrackable front1 = targetsSkyStone.get(7);front1.setName("Front Perimeter 1");
//        VuforiaTrackable front2 = targetsSkyStone.get(8);front2.setName("Front Perimeter 2");
//        VuforiaTrackable blue1 = targetsSkyStone.get(9);blue1.setName("Blue Perimeter 1");
//        VuforiaTrackable blue2 = targetsSkyStone.get(10);blue2.setName("Blue Perimeter 2");
//        VuforiaTrackable rear1 = targetsSkyStone.get(11);rear1.setName("Rear Perimeter 1");
//        VuforiaTrackable rear2 = targetsSkyStone.get(12);rear2.setName("Rear Perimeter 2");
//        allTrackables.addAll(targetsSkyStone);
//
//        stoneTarget.setLocation(OpenGLMatrix.translation(0, 0, stoneZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
//        blueFrontBridge.setLocation(OpenGLMatrix.translation(-bridgeX, bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));
//        blueRearBridge.setLocation(OpenGLMatrix.translation(-bridgeX, bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));
//        redFrontBridge.setLocation(OpenGLMatrix.translation(-bridgeX, -bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));
//        redRearBridge.setLocation(OpenGLMatrix.translation(bridgeX, -bridgeY, bridgeZ).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));
//        red1.setLocation(OpenGLMatrix.translation(quadField, -halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));
//        red2.setLocation(OpenGLMatrix.translation(-quadField, -halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));
//        front1.setLocation(OpenGLMatrix.translation(-halfField, -quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));
//        front2.setLocation(OpenGLMatrix.translation(-halfField, quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));
//        blue1.setLocation(OpenGLMatrix.translation(-quadField, halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
//        blue2.setLocation(OpenGLMatrix.translation(quadField, halfField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
//        rear1.setLocation(OpenGLMatrix.translation(halfField, quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
//        rear2.setLocation(OpenGLMatrix.translation(halfField, -quadField, mmTargetHeight).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
//
//        OpenGLMatrix robotFromCamera = OpenGLMatrix.translation(cfd, cld, cvd).multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, pxr, pyr, pzr));
//
//
//        for (VuforiaTrackable trackable : allTrackables) {
//            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera,BACK);
//        }
//
//
//    }
//
//    public double[] getOreintation() {
//        float x,y,h;
//        targetsSkyStone.activate();
//        targetVisible = false;
//        for (VuforiaTrackable trackable : allTrackables) {
//            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
//                t.addData("Visible Target", trackable.getName());
//                targetVisible = true;
//                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
//                if (robotLocationTransform != null) {
//                    lastLocation = robotLocationTransform;
//                }
//                break;
//            }
//        }
//        if (targetVisible) {
//            VectorF translation = lastLocation.getTranslation();
//            x = translation.get(0) / mmPerInch;
//            y = translation.get(1) / mmPerInch;
//            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
//            h = rotation.thirdAngle;
//            if(help.inRange(-10,0, h)) {
//                h+=2.5;
//            } else if(help.inRange(0,10, h)){
//                h-=2.5;
//            } else if(help.inRange(-180, 0, h)){
//                h += 182.5;
//            }else if(help.inRange(0,180, h)){
//                h -= 180;
//            }
//            t.addData("Pos (in)", "{X, Y, H} = %.1f, %.1f, %.1f", x, y, h);
//            t.update();
//            return new double[]{x,y,h};
//        } else {
//            t.addData("Visible Target", "none");
//            t.update();
//            return null;
//        }
//    }
//    public void close() {
//        targetsSkyStone.deactivate();
//    }
//
//    public enum stoneP{
//        LEFT,
//        MIDDLE,
//        RIGHT
//    }
}
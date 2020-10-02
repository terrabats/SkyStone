package developing;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import developing.TensorFlowClassifier.Recognition;


@TeleOp(name = "TensorFlowClassOp", group = "new")
public class TensorFlowClassOp extends OpMode {

    TensorFlowClassifier tf = new TensorFlowClassifier();
    CameraFunctions cf = new CameraFunctions();


    @Override
    public void init() {
        telemetry.addData("Status:", "Not Ready");
        telemetry.update();
        tf.init(0.75);
        cf.init(this, true);
        cf.changePixelFormat(PixelFormat.RGBA_8888);
        telemetry.addData("Status:", "Ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        Bitmap in  = cf.takePicture();
        if(in != null) {
            Recognition rec =  tf.getRecognition(cf.toARGB_8888(in));
            telemetry.addData("Recognition0:", rec.toString());
            telemetry.update();
        }
    }

}
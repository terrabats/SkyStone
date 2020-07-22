package global;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TensorFlowTest {

    public Interpreter tflite;

    public void init(){
        try{
            tflite = new Interpreter(loadModelFile());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public float predict(float in){
        float[] inputVal = new float[1];
        inputVal[0] = in;
        float[][] outputval = new float[1][1];

        tflite.run(inputVal,outputval);

        float inferredValue = outputval[0][0];

        return inferredValue;
    }

    public static MappedByteBuffer loadModelFile() throws IOException {
        FtcRobotControllerActivity bruh = new FtcRobotControllerActivity();
        AssetFileDescriptor fileDescriptor = bruh.getAssets().openFd("test.tflite");
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
      }

}

package developing;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.DequantizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TensorFlowTest {

    public Interpreter tflite;

    public void init() {
        try{
            tflite = new Interpreter(loadModelFile());
        }catch (IOException e){

        }
    }

    public float predictNum(float in) {
        float[][] inputVal = new float[1][1];
        inputVal[0][0] = in;
        float[][] outputval = new float[1][1];
        tflite.run(inputVal, outputval);
        float inferredValue = outputval[0][0];
        return inferredValue;
    }

    public Recognition predictClass(Bitmap in) {
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeOp(128, 128, ResizeOp.ResizeMethod.BILINEAR))
                        .build();
        TensorImage tImage = new TensorImage(DataType.FLOAT32);
        tImage.load(in);
        tImage = imageProcessor.process(tImage);
        TensorBuffer probabilityBuffer = TensorBuffer.createFixedSize(new int[]{1,2}, DataType.FLOAT32);
        tflite.run(tImage.getBuffer(), probabilityBuffer.getBuffer());
        float[] nums = probabilityBuffer.getFloatArray();
        if (nums[0] > nums[1]){
           return Recognition.NoStone;
        }else{
            return Recognition.Stone;
        }
    }

    public enum Recognition{
        Stone,
        NoStone;
    }

    public MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = FtcRobotControllerActivity.assetManager.openFd("class.tflite");
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }



}

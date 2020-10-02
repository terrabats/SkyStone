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

public class TensorFlowClassifier {

    public Interpreter tflite;

    public double MIN_CONFIDENCE;

    public void init(double min_conf) {
        MIN_CONFIDENCE = min_conf;
        try{
            tflite = new Interpreter(loadModelFile());
        }catch (IOException e){

        }
    }

    public float[] predictClass(Bitmap in) {
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
        return nums;
    }

    public Recognition getRecognition(Bitmap in){
        float[] prob = predictClass(in);
        if (prob[0] < MIN_CONFIDENCE && prob[1] < MIN_CONFIDENCE){
            return Recognition.ring0;
        }else if(prob[0] > prob[1]){
            return Recognition.ring1;
        }else if(prob[1] > prob[0]){
            return Recognition.ring3;
        }else {
            return Recognition.ring0;
        }
    }

    public MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = FtcRobotControllerActivity.assetManager.openFd("class.tflite");
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    public enum Recognition{
        ring0,
        ring1,
        ring3;
    }



}

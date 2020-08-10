package developing;

import android.graphics.Bitmap;
import android.os.Environment;


import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.image.ops.Rot90Op;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Storage {
    public int num = 0;


    public void saveBitmap(Bitmap in,String dirname, String name){
        File outFile = getOutputFile(dirname);
        File f = new File(outFile,name);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = in;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }catch(IOException e1){

        }

    }
    public File getOutputFile(String dirname){
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/test/");
        dir.mkdir();
        File dir1= new File(dir.getAbsolutePath()+"/"+dirname+"/");
        dir1.mkdir();
        return dir1;
    }

    public void saveText(String in, String dirname, String name)  {
        File outFile = getOutputFile(dirname);
        try (PrintWriter out = new PrintWriter(outFile.getAbsolutePath() + name)) {
            out.println(in);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void saveVideoData(ArrayList<String> vidData, String dirname, String name){
        File outFile =  getOutputFile(dirname);
        try {
            PrintWriter out = new PrintWriter(outFile.getAbsolutePath() + name);
            for( String s:vidData){
                out.println(s);
            }
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void saveVideo(ArrayList<Bitmap> vid,ArrayList<String> vidData, String dirname,double time){
        for (Bitmap curr : vid) {
            saveBitmap(curr, dirname, Integer.toString(num) + ".png");
            num++;
        }
        if(vidData != null){
            saveVideoData(vidData, dirname, "videoData.txt");
        }
        saveText(Double.toString(time/vid.size()), dirname, "time.txt");
    }

}

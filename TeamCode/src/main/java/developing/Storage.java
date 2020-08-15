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
    File outputFile;


    public void saveBitmap(Bitmap in, String name){
        File f = new File(outputFile,name);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 0, bos);
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
    public void makeOutputFile(String dirname){
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/test/");
        dir.mkdir();
        File dir1= new File(dir.getAbsolutePath()+"/"+dirname+"/");
        dir1.mkdir();
        outputFile = dir1;
    }

    public void saveText(String in,String name)  {
        try {
            PrintWriter out = new PrintWriter(outputFile.getAbsolutePath()+"/" + name);
            out.println(in);
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void saveVideoData(ArrayList<String> vidData, String name){
        try {
            PrintWriter out = new PrintWriter(outputFile.getAbsolutePath() + "/"+name);
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
        makeOutputFile(dirname);
        saveText(Double.toString(time/vid.size()), "time.txt");
        for (Bitmap curr : vid) {
            saveBitmap(curr, Integer.toString(num) + ".png");
            num++;
        }
        if(vidData != null){
            saveVideoData(vidData, "videoData.txt");
        }
    }

}

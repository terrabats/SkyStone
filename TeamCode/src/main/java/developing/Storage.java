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
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Storage {
    public int num = 0;

    public void saveBitmap(Bitmap in,String dirname, String name){
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/test/");
        dir.mkdir();
        File dir1= new File(dir.getAbsolutePath()+"/"+dirname+"/");
        dir1.mkdir();
        File f = new File(dir1,name);

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
    public void saveVideo(ArrayList<Bitmap> vid, String name){
        for (Bitmap curr : vid) {
            saveBitmap(curr, name, Integer.toString(num) + ".png");
            num++;
        }
    }



}

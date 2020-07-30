package developing;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Storage {
    FileOutputStream outputStream;
    public void writeFileExternalStorage(File in, String name) {
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/test/");
        dir.mkdir();
        File file = new File(dir,name);
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

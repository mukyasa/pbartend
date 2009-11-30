package com.move;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MoveTest extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       SmsDbHelper help = new SmsDbHelper(this);
       help.getReadableDatabase();
  }
        
/*     
        try {
        	//set <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
            File root = Environment.getExternalStorageDirectory();
            
            if (root.canWrite()){
            	
            	File movedDb = new File(root,Dbtest.FILE_NM);
            	FileWriter writer = new FileWriter(movedDb);
            	Log.v("","Starting");
            	
            	String sCurrentLine;
            	 
            	BufferedReader in = new BufferedReader(new FileReader("/data/data/com.move/databases/"+Dbtest.FILE_NM));
	            BufferedWriter out = new BufferedWriter(writer);
                 
                while ((sCurrentLine = in.readLine()) != null) 
                	out.write(sCurrentLine);

                out.close();
                
                Log.v("","Finished"); 
                
            }
        } catch (IOException e) {
            Log.v("", "Could not write file " + e.getMessage());
        }
        */
        
    void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        
        while ((len = in.read(buf)) > 0) 
            out.write(buf, 0, len);
        
        in.close();
        out.close();
        
    }
}
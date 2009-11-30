package com.move;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MoveTest extends Activity {
    /** Called when the activity is first created. */
	private static final String MEDIA_PATH = new String("/sdcard/");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     
       // Dbtest.getInstance(this);
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
        
        
    }
}
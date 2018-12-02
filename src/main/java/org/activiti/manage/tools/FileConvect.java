package org.activiti.manage.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileConvect {
	 //通过这个方法把一个名为filename的文件转化为一个byte数组 
    public static byte[] fileToByte(File file){ 
        byte[] b = null; 
        try { 
            b = new byte[(int) file.length()]; 
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(file)); 
            is.read(b); 
        } catch (FileNotFoundException e) { 
        // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
        return b; 
    } 
}

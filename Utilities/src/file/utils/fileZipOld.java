/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author James
 */
public class fileZipOld {
    
    public static void oldFilesToZip(File dest, File searchdest) throws IOException{
        File backupzip = new File(searchdest+".zip");
            FileZip.pack(dest.toPath(), backupzip.toPath());
    }
}

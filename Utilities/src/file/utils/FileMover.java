/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author James
 */
public class FileMover {
    
    public static void main(String args[]) throws IOException {
        File source = new File("C:\\James44\\Nuts\\morenuts\\thosenuts.txt");
        File dest = new File("C:\\James45\\deweper\\anothewrone");
        
        copyFiles(source, dest);
    }
    
    public static void copyFiles(File source, File dest) throws IOException {
        dest.mkdirs();
        FileUtils.copyFileToDirectory(source, dest);
    }
    
}

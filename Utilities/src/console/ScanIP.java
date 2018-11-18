/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import static console.ConsoleCommand.FileReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author James
 */
public class ScanIP {

    public static void main(String[] argv) throws IOException, InterruptedException {
       File outputFile = new File("C:\\testical.txt");
        outputFile.createNewFile();
        List<String> results = ConsoleCommand.runCommand("arp -a");
//        List<String> IPs = new; 
        separateIPs(results);
    }
    
    public static List<String> separateIPs(List<String> results) throws IOException{
//        List<String> fileLines = FileReader(results);
        
        for (String line : results) {
            if (line.length() > 1 && isNumeric(line.trim().substring(0, 1))){
                String[] IP = line.trim().split(" ");
                System.out.println(IP[0]);
            }
            
        }
        return results;
    }
    
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
    
}

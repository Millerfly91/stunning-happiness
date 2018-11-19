/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class ScanIP {

    private static String[] IP;
    //String[] IP = null;

    public static void main(String[] argv) throws IOException, InterruptedException {
//        List<String> results = ConsoleCommand.runCommand("arp -a");
////        System.out.println(results);
////        List<String> results = ConsoleCommand.runCommand("arp -a");
////        List<String> IPs = new; 
//        List<String> IPList = separateIPs(results);
        System.out.println(getActiveIPs());
    }
    
    public static List getActiveIPs() throws IOException{
        List<String> results = ConsoleCommand.runCommand("arp -a");
        List<String> IPList = separateIPs(results);
        return IPList;
    }
    
    public static List separateIPs(List<String> results) throws IOException{
//        List<String> fileLines = FileReader(results);
        List<String> IPList = new ArrayList<>();
        
        for (String line : results) {
            if (line.length() > 1 && isNumeric(line.trim().substring(0, 1))){
                IP = line.trim().split(" ");
//                System.out.println(IP[0]);
                IPList.add(IP[0]);
            }
            
        }
        return IPList;
    }
    
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
    
}

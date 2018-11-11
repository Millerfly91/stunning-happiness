/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

/**
 *
 * @author James
 */
public class ScanIP {
       protected String executeCommandLine(String command) {

        try {
            // Just one line and you are done !  
            // We have given a command to start cmd 
            // /K : Carries out command specified by string 
            Runtime.getRuntime().exec("cmd  /K \"powershell -Command "
                    + "\"Start-Process 'cmd.exe' -Verb runAs -ArgumentList \"\"/k " + command+"\"\"\""
                    + "\"");
//            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + command + "\"");

        } catch (Exception e) {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }

        return "";
    }
    
}

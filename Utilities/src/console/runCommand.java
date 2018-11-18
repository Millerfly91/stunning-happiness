/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author James
 */
public class runCommand {

    public static void main(String[] argv) throws IOException, InterruptedException {
        
//        another option
//        results.forEach(System.out::println);
    }

    public static List runCommand(String command) throws IOException {
        File outputFile = new File("C:\\Users\\James\\Documents\\NetBeansProjects\\testical.txt");
        outputFile.createNewFile();
        executeCommandLine(command + " > " + outputFile.getAbsolutePath() + " && exit");

        List<String> fileLines = FileReader(outputFile);

        executeCommandLine("del " + outputFile + " && exit");
//        List<String> results = runCommand("ipconfig");

        for (String line : fileLines) {
            System.out.println(line);
        }
        return fileLines;
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    static List<String> FileReader(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    protected static String executeCommandLine(String command) {

        try {
            // Just one line and you are done !  
            // We have given a command to start cmd 
            // /K : Carries out command specified by string 
            Runtime.getRuntime().exec("cmd  /K \"powershell -Command "
                    + "\"Start-Process 'cmd.exe' -Verb runAs -ArgumentList \"\"/k " + command + "\"\"\""
                    + "\"");
//            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + command + "\"");

        } catch (Exception e) {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }
        return "";
    }

}

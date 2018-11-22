/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class ConsoleCommand {

    public static void main(String[] argv) throws IOException, InterruptedException {

//        another option
//        results.forEach(System.out::println);
    }

    public static List runCommand(String command) throws IOException {
        File outputFile = new File("C:\\jakeTempFIle.txt");
        if (outputFile.exists() != true) {
            outputFile.createNewFile();
        }
        executeCommandLine("" + command + " > " + outputFile.getAbsolutePath() + " && exit && exit");

        try {
            Thread.sleep(10 * 1000);
        } catch (Throwable ex) {
            Logger.getLogger(ConsoleCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> fileLines = FileReader(outputFile);

        outputFile.delete();
        executeCommandLine("del " + outputFile + " && exit");

//        ---------   to print in this  -------------
//        for (String line : fileLines) {
//            System.out.println(line);
//        }
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
            Process proc
                    = Runtime.getRuntime().exec("powershell -Command "
                            + "\""
                            /* Starts a command prompt: */
                            + "Start-Process 'cmd.exe' "
                            /* Start as administrator: */
                            + "-Verb runAs "
                            /* Line executed in command prompt: */
                            + "-ArgumentList '/c  " + command + "'"
                            + "\"");
            BufferedReader reader
                    = new BufferedReader(
                            new InputStreamReader(
                                    proc.getInputStream()));

            
            reader.lines().forEach(line -> System.out.println(line));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import misc.time.Stopwatch;

/**
 *
 * @author James
 */
public class ConsoleCommand {

    public static void main(String[] argv) {
        try {
            System.out.println(runCommand("ipconfig"));
        } catch (IOException ex) {
            Logger.getLogger(ConsoleCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List runCommand(String command) throws IOException {
        return runCommand(command, 500);
    }

    public static List runCommand(String command, long timeOut) throws IOException {
        File cmdOutput = new File("C:\\jakeTempFile.txt");

        if (cmdOutput.exists() != true) {
            cmdOutput.createNewFile();
        }
        File cmdOutput_done = new File("C:\\completedcmdout.txt");
//        File cmdOutput_done = new File(cmdOutput.getAbsolutePath()+ "_done");

        executeCommandLine("" + command + " > " + cmdOutput.getAbsolutePath() +" && exit");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsoleCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        executeCommandLine("rename " + cmdOutput.getAbsolutePath() + " " + cmdOutput_done.getName() + " && exit");

        while (!cmdOutput_done.exists()) {
        }
        List<String> cmdOutputLines = readFile(cmdOutput_done);
        cmdOutput_done.delete();
        return cmdOutputLines;
    }
    
     public static String quickExecute(String command) throws IOException {
        Stopwatch runTime = new Stopwatch();
        runTime.start();
        Runtime rt = Runtime.getRuntime();
        String[] commands = {command};
        Process proc = rt.exec(command);

        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(
                        proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(
                new InputStreamReader(
                        proc.getErrorStream()));

        StringBuilder output = new StringBuilder();
        
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            output.append(s).append("\n");
        }
        while ((s = stdError.readLine()) != null) {
            output.append(s).append("\n");
        }
        
        runTime.stop();
        System.out.println("Command, \"" + command + "\" "
                + "took " + runTime.elapsed(TimeUnit.SECONDS) + " seconds.");
        proc.destroy();
        return output.toString();
    }

    static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    protected static List<String> executeCommandLine(String command) {
        try {
            Process proc
                    = Runtime.getRuntime().exec("powershell -Command "
                            + "\""
                            /* Starts a command prompt: */
                            + "Start-Process 'cmd.exe' "
                            /* Start as administrator: */
                            + "-Verb runAs -WindowStyle hidden "
                            /* Line executed in command prompt: */
                            + "-ArgumentList '/c  " + command + "'"
                            + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
}

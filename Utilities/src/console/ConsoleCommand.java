/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class ConsoleCommand {

    public static void main(String[] argv) {
    }

    public static List runCommand(String command) throws IOException {
        File cmdOutput = new File("C:\\jakeTempFile.txt");

        if (cmdOutput.exists() != true) {
            cmdOutput.createNewFile();
        }

        executeCommandLine("" + command + " > " + cmdOutput.getAbsolutePath() + " && exit");

        try {
            Thread.sleep(500);
        } catch (Throwable ex) {
            Logger.getLogger(ConsoleCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> cmdOutputLines = readFile(cmdOutput);
        cmdOutput.delete();
        return cmdOutputLines;
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
                            + "-Verb runAs "
                            /* Line executed in command prompt: */
                            + "-ArgumentList '/c  " + command + "'"
                            + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
}

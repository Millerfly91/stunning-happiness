
import console.ConsoleCommand;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author James
 */
public class IPConfig {

    public static void main(String[] argv) throws IOException, InterruptedException {
        File outputFile = new File("C:\\testical.txt");
        outputFile.createNewFile();
        ConsoleCommand.runCommand("ipconfig");

//        executeCommandLine("ipconfig > C:\\testical.txt && exit");
//   
//        FileReader(outputFile).forEach((line) -> {
//            line = line.trim();
//
//            if (line.length() > 3 && isNumeric(line.substring(0, 3))) {
////                System.out.println(line);
//                System.out.println(line.split(" ")[0].trim());
//            }
//
//        });
//        outputFile.delete();
//    }
//
//    public static boolean isNumeric(String s) {
//        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
//    }
//
//    static List<String> FileReader(File file) throws IOException {
//        return Files.readAllLines(file.toPath());
//    }
//
//    protected static String executeCommandLine(String command) {
//        try {
//            // Just one line and you are done !  
//            // We have given a command to start cmd  
//            // /K : Carries out command specified by string 
//            Runtime.getRuntime().exec("cmd  /K \"powershell -Command "
//                    + "\"Start-Process 'cmd.exe' -Verb runAs -ArgumentList \"\"/k " + command + "\"\"\""
//                    + "\"");
////            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + command + "\"");
//
//        } catch (Exception e) {
//            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
//            e.printStackTrace();
//        }
//
//        return "";
//    }
    }
}

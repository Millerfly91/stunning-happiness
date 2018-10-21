
import file.csv.CSVReader;
import file.utils.FileLocator;
import file.utils.FileMover;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author James
 */
public class TersterClass {
    
    public static void main(String args[]) throws IOException {
        Scanner getinput = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the location you want to search : ");
        String searchlocation = getinput.next();
        System.out.println("Enter the location of the csv containing the files you are searching for : ");
        String CSVtosearch = getinput.next();
        System.out.println("Enter the destination for the files : ");
        String filedest = getinput.next();
        File dest = new File(filedest);
        
        List<String> filesneeded = CSVReader.CSVRead(CSVtosearch);
        List<File> filestomove = FileLocator.locateFilesByFileName(searchlocation, filesneeded);
        
        for (File temp : filestomove) {
            if (temp != null) {
                FileMover.copyFiles(temp, dest);
            }
	}
    }
        
    
}

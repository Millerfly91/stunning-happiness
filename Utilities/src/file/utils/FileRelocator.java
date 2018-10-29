/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

import file.csv.CSVReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author James
 */
public class FileRelocator {
    public static void main(String args[]) throws IOException {
        //Scanner getinput = new Scanner(System.in);  // Reading from System.in
        String searchlocation = getStringFromUser("Enter the location you want to search : ");
        String CSVtosearch = getStringFromUser("Enter the location of the csv containing the files you are searching for : ");
        String filedest = getStringFromUser("Enter the destination for the files : ");
        File dest = new File(filedest);
        
        List<String> filesneeded = CSVReader.CSVRead(CSVtosearch);
        List<File> filestomove = FileLocator.locateFilesByFileName(searchlocation, filesneeded);
        
        for (File temp : filestomove) {
            if (temp != null) {
                FileMover.copyFiles(temp, dest);
            }
	}
        
    }     
    public static String getStringFromUser(String question){
        Scanner scanIn = new Scanner(System.in);
        System.out.println(question);
        return scanIn.next();
    }
}

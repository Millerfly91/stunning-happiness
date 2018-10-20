
import file.csv.CSVReader;
import file.utils.FileLocator;
import java.io.File;
import java.io.IOException;
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
        Scanner getCSV = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the location of the csv :");
        String CSVtosearch = getCSV.next();
        String CSVlocation = new String(CSVtosearch);   
        
        CSVReader.CSVRead(CSVlocation);
        List<String> filesneeded = CSVReader.main(args);
        System.out.println(filesneeded);
        FileLocator.locateFilesByFileName("C:\\James44", filesneeded);
    }
        
    
}

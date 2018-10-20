
import file.csv.CSVReader;
import java.io.File;
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
    
    public static void main(String args[]) {
        Scanner getCSV = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the location of the csv :");
        String CSVtosearch = getCSV.next();
        File CSVlocation = new File(CSVtosearch);   
        
        CSVReader.CSVRead(CSVtosearch);
            
    }
        
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

/**
 *
 * @author James
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    
    public static void main(String[] args) throws IOException{
        testRead(); 
    }
             
    public static void testRead() throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader("C:\\James44\\mynuts.csv");
        BufferedReader br = new BufferedReader(fileReader);
        
        String line = br.readLine(); 
        
        System.out.println(line);
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class CSVReader {
     public static void main(String[] args) throws IOException {
        testRead();
    }

    public static void testRead() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader("C:\\James44\\mynuts.csv");
        BufferedReader br = new BufferedReader(fileReader);

        String line = br.readLine();
        List<String> fileslist = new ArrayList<String>();
        
        while ((line = br.readLine()) != null && !line.isEmpty()) {
//            String[] filenames = line.split(",");
            fileslist.add(line);
        }

        System.out.println(fileslist);

    }
}

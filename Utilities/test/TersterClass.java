
import file.csv.CSVReader;
import file.utils.FileLocator;
import file.utils.FileMover;
import file.utils.FileZip;
import file.utils.fileZipOld;
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
        String searchlocation = getStringFromUser("Enter the location you want to search : ");
        String CSVtosearch = getStringFromUser("Enter the location of the csv containing the files you are searching for : ");
//        String filedest = getStringFromUser("Enter the destination for the files : ");
        File dest = new File(searchlocation+"new");
        File searchdest = new File(searchlocation);

        List<String> filesneeded = CSVReader.CSVRead(CSVtosearch);
        List<File> filestomove = FileLocator.locateFilesByFileName(searchlocation, filesneeded);

        copyFileList(filestomove, dest);

        
//        packaging
        File zipdest = new File(dest + ".zip");
        FileZip.pack(dest.toPath(), zipdest.toPath());
        fileZipOld.oldFilesToZip(dest, searchdest);
        
//        cleanup
        
    }

    private static String getStringFromUser(String question1) {
        Scanner getstring = new Scanner(System.in);  // Reading from System.in
        System.out.println(question1);
        return getstring.next();
    }

    private static void copyFileList(List<File> filestomove, File dest) throws IOException {
        for (File temp : filestomove) {
            if (temp != null) {
                FileMover.copyFiles(temp, dest);
            }
        }
    }

}

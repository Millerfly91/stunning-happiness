/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jacob
 */
public class FileLocator {

    public static void main(String args[]) {
        List<String> testFileList = new ArrayList<>();
        testFileList.add("testData1.txt");
        testFileList.add("test3.txt");
        testFileList.add("ttt.txt");

        FileLocator.findAllFiles("C:\\Users\\Jacob\\", testFileList);
    }

    private FileLocator() {
    }

    public static List<File> findAllFiles(String rootDir, List<String> filesNames) {

        List<File> fileList = new ArrayList<>();
        Map<String, File> tempMap = new HashMap<>();
        Collection<File> fileColl = FileUtils.listFiles(new File(rootDir), null, true);

        fileColl.stream().forEach((file) -> {
            tempMap.put(file.getName().toUpperCase(), file);
        });

        File tmpFile;
        for (String fileName : filesNames) {
            tmpFile = tempMap.get(fileName.toUpperCase());
            if (tmpFile == null) {
                System.out.println("Unabale to find " + fileName + " in " + rootDir);

            } else {
                System.out.println("Found file: " + tmpFile.getPath());

                fileList.add(tmpFile);
            }
        }

        return fileList;
    }

    public static List<File> locateFilesByFileName(String rootDir, List<String> filesNames) {
        List<File> fileList = new ArrayList<>(filesNames.size());
        FileUtils.listFiles(new File(rootDir), null, true).
                stream().
                forEach((file) -> {
                    String fileName = file.getName();
                    if (filesNames.contains(fileName)) {
                        fileList.add(filesNames.indexOf(fileName), file);
                    }
                });
        return fileList;
    }

    public void testCsvWrite() {
//        File csvFile = new FIle("C:\\")
    }

    public void testCsvRead() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader("C:\\TestCsv.csv");
        BufferedReader bufRead = new BufferedReader(fileReader);

        System.out.println("ReadLIne:   " + bufRead.readLine());

    }

}

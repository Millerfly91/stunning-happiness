/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main(String args[]) throws Exception {
        testMEthod();
    }

    public static void testMEthod() throws Exception {
        List<String> testFileList = new ArrayList<>();
        testFileList.add("testData1.txt");
        testFileList.add("test3.txt");
        testFileList.add("ttt.txt");

        String root = "C:\\TestDirectory\\";
        List<File> testFiles = null;
        try {
            testFiles = locateFilesByFileName(root, testFileList);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("TEST");
        }
        System.out.println(testFiles);
    }

    private FileLocator() {
    }

    @Deprecated
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

    /**
     * Will return a list of File objects in the order of file names in
     * fileNames parameter.
     *
     * @param rootDir {@link String} of the directory path to begin the
     * recursive search for files.
     * @param filesNames {@link List<String>} of file names to be located.
     * @return {@link List<File>} fileList of the file specified by filesNames.
     */
    public static List<File> locateFilesByFileName(
            String rootDir,
            List<String> filesNames) throws IOException {

        File[] files = new File[filesNames.size()];
        java.nio.file.Files.
                walk(Paths.get(rootDir)).
                filter(Files::isRegularFile).
                forEach((path) -> {
                    int index = filesNames.indexOf(path.getFileName().toString());
                    if (index > 0) {
                        files[index] = path.toFile();
                    }
                });
        return Arrays.asList(files);
    }

}

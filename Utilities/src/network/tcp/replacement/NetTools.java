/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import console.ConsoleCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author Jacob
 */
public enum NetTools {
    INSTANCE;

    public static void main(String arg[]) throws Exception {
        System.out.println(INSTANCE.getNetworkNodes());
    }

    public  List<Node> getNetworkNodes() {
        List<Node> responsiveNodes = Collections.EMPTY_LIST;
        
        try {
            String scanResults = runBasicScan();

            responsiveNodes = parseBasicMapData(scanResults);

        } catch (IOException ex) {
            Logger.getLogger(NetTools.class.getName()).log(
                    Level.SEVERE, "Error occurred during scan", ex);

        }
        return responsiveNodes;
    }

    private  String runBasicScan() throws IOException {
        return ConsoleCommand.quickExecute("nmap -sP 192.168.1.0/24");
    }

     List<Node> parseBasicMapData(String rawMapData) {
        String[] splitMapData = rawMapData.split("\\r?\\n");
        List<String> splitMapDataList = Arrays.asList(splitMapData);
        Iterator<String> iter = splitMapDataList.iterator();
        List<Node> nodeList = new ArrayList<Node>();
        while (iter.hasNext()) {
            String line = iter.next();
            if (!line.contains("Nmap scan report")) {
                continue;
            }
            Node newNode = new Node();
            newNode.setIpAddress(line.substring(line.indexOf("for ") + 4).trim());
            line = iter.next();
            line = iter.next();
            if (line.contains("MAC Address")) {
                newNode.setMAC(
                        selectValueFromString(line, "MAC Address: ", " ", 0));
                newNode.setInfo(
                        selectValueFromString(line, "MAC Address: ", "(", 1));
            }

            newNode.setTimestamp(new Date());
            nodeList.add(newNode);
        }
        return nodeList;
    }

    private  String selectValueFromString(
            final String rawData,
            final String keyWord,
            final String delimeter,
            final int position) {

        if (!rawData.contains(keyWord)) {
            return "";
        }

        String values = rawData.substring(
                rawData.indexOf(keyWord) + keyWord.length());

        String targetVal = values.split("\\" + delimeter)[position];

        return fullTrim(targetVal);
    }

    private  String fullTrim(
            final String strTrim,
            final String... additionalTrim) {

        List<String> strCharsToTrim = Arrays.asList("(", ")");

        strCharsToTrim.addAll(Arrays.asList(additionalTrim));

        String strOut = strTrim;
        for (String t : strCharsToTrim) {
            strOut = strOut.replaceAll("\\" + t, "");
        }
        return strOut.trim();
    }

}

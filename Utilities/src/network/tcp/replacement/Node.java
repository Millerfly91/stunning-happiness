/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import java.util.Date;

/**
 *
 * @author Jacob
 */
public class Node {

    private String MAC;
    private String ipAddress;
    private String info;
    private Date timestamp;

    /**
     * @return the MAC
     */
    public String getMAC() {
        return MAC;
    }

    /**
     * @param MAC the MAC to set
     */
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /*
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\nNode IP: ").append(ipAddress);
        str.append("\n   MAC: ").append(MAC);
        str.append("\n   Info: ").append(info);
        return str.toString();
    }
}

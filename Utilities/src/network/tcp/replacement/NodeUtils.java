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
enum NodeUtils {
    INSTANCE;

    public Node buildNewNode(
            String ipAddress,
            String MAC,
            String info,
            Date timestamp) {   
        Node newNode = new Node();
        newNode.setIpAddress(ipAddress);
        newNode.setMAC(MAC);
        newNode.setInfo(info);
        newNode.setTimestamp(timestamp);
        return newNode;
    }
}

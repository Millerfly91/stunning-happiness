/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author James
 */
public class ScanIP {
    
//    public static void main(String args[]) throws IOException{
//        String ip = "192.168.1.1";
//        checkHosts("192.168.0");
//    }
//    
//    public void checkHosts(String subnet) throws UnknownHostException, IOException{
//   int timeout=1000;
//   for (int i=1;i<255;i++){
//       String host=subnet + "." + i;
//       if (InetAddress.getByName(host).isReachable(timeout)){
//           System.out.println(host + " is reachable");
//       }
//   }
//}
    
//    public class Main {

//        /**
//         *
//         * @param args
//         * @throws Exception
//         */
//        public static void main(String[] args) throws Exception {
//            InetAddress[] addr = InetAddress.getAllByName(args[0]);
//                for (int i = 0; i < addr.length; i++)
//                    System.out.println(addr[i]);
//  } 
//    public class IpRange {
//    private String ipMain = "192.168.1.";
//    private int startRange = 2; // 2 = 10.0.0.2
//    private int endRange = 255;   // 5 = 10.0.0.255
//    private int ipRangeLength = endRange - startRange;
//    private String urlParam = ":8098/?cmd=nothing";
//
//    public static void main(String[] args) {
//
//        ScanIP ipRange = new ScanIP();
//
//        // true -> ip range url with parameters / false -> only ip range
//        String[] results = ipRange.ipRangeGenerator(true);
//
//        for (String output : results)
//            System.out.println(output);
//
//    }
//
//    public String[] ipRangeGenerator(boolean link) {
//        String[] ipAddresses = new String[ipRangeLength];
//        String urlRange;
//
//        for (int i = 0; i < ipRangeLength; i++) {
//            String ipRange = ipMain + (startRange + i);
//            urlRange = ipRange;
//            if (link)
//                urlRange = "http://" + ipRange + urlParam;
//            ipAddresses[i] = urlRange;
//        }
//        return ipAddresses;
//    }
    public static void main(String[] args) throws UnknownHostException {
        String ipAddress = "192.168.1.10";
            InetAddress inet = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
		
            try {
//              String ipAddress = "192.168.1.1";
//              InetAddress inet = InetAddress.getByName(ipAddress);
//		System.out.println("Sending Ping Request to " + ipAddress);
		if (inet.isReachable(5000)) {
                    System.out.println(ipAddress + " is reachable.");
		} else {
                    System.out.println(ipAddress + " NOT reachable.");
		}
		} catch (Exception e) {
                    System.out.println("Exception:" + e.getMessage());
		}
	}
}
//}

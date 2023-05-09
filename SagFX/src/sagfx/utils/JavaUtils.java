/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sagfx.utils;

import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Frost
 * 
 * Tiene metodos para realizar cosas en particular
 */
public class JavaUtils {

    public static String hashString(String base) {
        String secureString = "";
        try {
            String salt = "EXAMPLE_3X4mpl3_S3CVREPa55word_*";
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(base.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            secureString = sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secureString;
    }

    public static String getMAC() {
        String mac = "";
        NetworkInterface a;
        try {
            a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] dirMac = a.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dirMac.length; i++) {
                sb.append(String.format("%02X%s", dirMac[i], (i < dirMac.length - 1) ? "-" : ""));
            }
            mac = sb.toString();
            System.out.println(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

}

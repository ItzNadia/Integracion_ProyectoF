package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JavaUtils {
    public static String hashString(String base){
        String secureString ="";
        try{
            String salt = "EXAMPLE_3X4MP13_S3CVREPa55word_*";
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(base.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            secureString = sb.toString().toUpperCase();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return secureString;
    }
}

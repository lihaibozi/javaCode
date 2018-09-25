package common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Encryption {
	private static Logger log = Logger.getLogger(Encryption.class);
    public String sendJson(String sendjsons){
        String encode64 = setBase64(sendjsons);
        byte[] jsondata = encode2(encode64);
        String bb = new String(jsondata);
        return bb;
    }

    public String receiveJson(String data) {
        String context = getBase64(encode2(data));
        return context;
    }

    private byte[] encode2(String str) {
        int code = 325;
        byte[] charArray = null;
        try {
            charArray = str.getBytes("UTF-8");
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] = (byte) (charArray[i] ^ code);
            }
            return charArray;
        } catch (Exception e) {
            log.error("Encryption encode2" + e.getMessage());
        }
        return null;
    }

    public String setBase64(String str) {

        try {
            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
            return new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Encryption setBase64" + e.getMessage());
        }
        return null;
    }

    public String getBase64(byte[] s) {
        byte[] encodeBase64 = Base64.decodeBase64(s);
        try {
            return new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public String makeMD5(String plainText) {
        if (plainText == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = plainText.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
        	  log.error("Encryption makeMD5" + e.getMessage());
            return null;
        }
    }


}

package avviait;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Secure Hashing of strings, for example for passwords
 *
 * for reference check:
 *   - http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 *   - http://docs.oracle.com/javase/8/docs/api/java/security/SecureRandom.html
 *   - https://crackstation.net/hashing-security.htm
 */

public class StringHashing {
    private static final String hashingAlgorithm = "SHA-1";
    private static final String randomNumberGeneratorAlgorithm = "SHA1PRNG";

    // Get secured string with hashingAlgorithm and salt
    public static String getSecuredString(String string2Secure, String salt) {
        String securedString = null;
        try {
            MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);

            md.update(salt.getBytes());
            byte[] bytes = md.digest(string2Secure.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte bytesItem : bytes) {
                sb.append(Integer.toString((bytesItem & 0xff) + 0x100, 16).substring(1));
            }

            securedString = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return securedString;
    }

    // Generate random string
    public static String getSalt() {
        String salt2String = "";

        try {
            SecureRandom sr = SecureRandom.getInstance(randomNumberGeneratorAlgorithm);
            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            salt2String = new String(salt, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Encode to Base64
        Base64.Encoder encoder = Base64.getEncoder();
        try {
        	salt2String = encoder.encodeToString(salt2String.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
        }

        // return string instead of array
        return salt2String;
    }
}

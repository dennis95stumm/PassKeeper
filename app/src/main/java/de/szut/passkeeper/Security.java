package de.szut.passkeeper;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Dennis Stumm on 17.02.15.
 */
public class Security {
    private static Security INSTANCE;

    /**
     * @return
     */
    public static Security getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Security();
        }
        return INSTANCE;
    }

    /**
     * @param password
     * @return
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer result = new StringBuffer();
        for (byte b : digest) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    /**
     * @param password
     * @param hash
     * @return
     */
    public boolean checkPassword(String password, String hash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String passwordHash = encryptPassword(password);
        return hash.equals(passwordHash);
    }

    /**
     * @param password
     * @param value
     * @return
     */
    public String encryptValue(String password, String value) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES");
        //cipher.init(Cipher.ENCRYPT_MODE, k);
        Log.d(Security.class.getSimpleName(), cipher.getAlgorithm());
        //Log.d(Security.class.getSimpleName(), cipher.getParameters());

        return "";
    }

    /**
     * @param password
     * @param value
     * @return
     */
    public String decryptValue(String password, String value) {
        return "";
    }
}

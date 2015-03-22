package de.szut.passkeeper.Model;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

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
    public String encryptPassword(String password) {
        StringBuffer result = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * @param password
     * @param hash
     * @return
     */
    public boolean checkPassword(String password, String hash) {
        String passwordHash = encryptPassword(password);
        return hash.equals(passwordHash);
    }

    /**
     * @param password
     * @param value
     * @return
     */
    public String encryptValue(String password, String value, byte[] salt) {
        String encryptedValueBase64 = "";
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            byte[] encryptedValue = cipher.doFinal(value.getBytes("UTF-8"));
            byte[] encryptedValueBase64Bytes = Base64.encode(encryptedValue, Base64.DEFAULT);
            encryptedValueBase64 = new String(encryptedValueBase64Bytes);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return encryptedValueBase64;
    }

    /**
     * @param password
     * @param value
     * @return
     */
    public String decryptValue(String password, String value, byte[] salt) {
        String decryptedValue = "";
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] decodedValue = Base64.decode(value.getBytes("UTF-8"), Base64.DEFAULT);
            byte[] decryptedValueBytes = cipher.doFinal(decodedValue);
            decryptedValue = new String(decryptedValueBytes);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return decryptedValue;
    }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    public byte[] generateSalt() {
        byte[] salt = new byte[8];
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
package de.szut.passkeeper.model;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A singleton class, which supply the security functionality of the app.
 */
public class Security {
    private static Security INSTANCE;

    /**
     * A private constructor so that this class couldn't be instantiated from another class.
     */
    private Security() {
    }

    /**
     * Method for getting the instance of the security class.
     * It generates a new instance if the class wasn't instantiated before.
     * @return The instance of this class
     */
    public static Security getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Security();
        }
        return INSTANCE;
    }

    /**
     * Creates an hash for a password with the hash algorithm SHA-512.
     * @param password The password that should be hashed
     * @return Hash of the password
     */
    public String encryptPassword(String password) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Check if the password equals the hashed password.
     * @param password The password that should be checked
     * @param hash The hash on which the password should be checked
     * @return Boolean value for the equality of the passwords.
     */
    public boolean checkPassword(String password, String hash) {
        String passwordHash = encryptPassword(password);
        return hash.equals(passwordHash);
    }

    /**
     * Creates a secret key on the basis of a password and a salt which is needed
     * by the AES algorithm to encrypt and decrypt values.
     * @param password Password from which the secret key is created
     * @param salt Salt which is inserted randomly into the hash so that its more safety
     * @return The created secret key
     */
    public SecretKey getSecret(String password, byte[] salt) {
        SecretKey secret = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return secret;
    }

    /**
     * Encrypts a value with the AES-256 algorithm by the given secret key and initialization vector.
     * @param value Value that should be encrypted
     * @param secret Secret key with which the value should be encrypted
     * @param iv Initialization vector with which the value should be encrypted
     * @return The AES-256 encrypted value
     */
    public String encryptValue(String value, SecretKey secret, byte[] iv) {
        String encryptedValueBase64 = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
            byte[] encryptedValue = cipher.doFinal(value.getBytes("UTF-8"));
            encryptedValueBase64 = Base64.encodeToString(encryptedValue, Base64.NO_WRAP);
        } catch (NoSuchPaddingException
                | UnsupportedEncodingException
                | NoSuchAlgorithmException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return encryptedValueBase64;
    }

    /**
     * Decrypts a value with the AES-256 algorithm by the given secret key and initialization vector.
     * @param value The previously encrypted value
     * @param secret Secret key with which the value should be decrypted
     * @param iv Initialization vector with which the value should be decrypted
     * @return The decrypted value
     */
    public String decryptValue(String value, SecretKey secret, byte[] iv) {
        String decryptedValue = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
            byte[] decodedValue = Base64.decode(value, Base64.NO_WRAP);
            byte[] decryptedValueBytes = cipher.doFinal(decodedValue);
            decryptedValue = new String(decryptedValueBytes);
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return decryptedValue;
    }

    /**
     * Generates a random salt required for proper en-/decryption.
     * @return The generated salt
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

    /**
     * Generates a initialization vector required for the AES algorithm
     * based on the block size of the algorithm.
     * @return The generated initialization vector
     */
    public byte[] generateIV() {
        byte[] iv = null;
        try {
            iv = new byte[Cipher.getInstance("AES/CBC/PKCS5Padding").getBlockSize()];
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(iv);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return iv;
    }

    /**
     * Generates a secure password.
     * @return The generated password
     */
    public String generatePassword() {
        String password = "";
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            password += (char) (random.nextInt(93) + 33);
        }
        return password;
    }
}
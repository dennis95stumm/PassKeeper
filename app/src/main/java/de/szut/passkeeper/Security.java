package de.szut.passkeeper;

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
        return "";
    }

    /**
     * @param password
     * @param value
     * @return
     */
    public String encryptValue(String password, String value) {
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

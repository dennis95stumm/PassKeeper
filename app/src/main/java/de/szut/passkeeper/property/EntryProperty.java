package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * This class is used to store data for user entries out of the database
 */
public class EntryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private int entryId;
    private String entryTitle;
    private String entryUsername;
    private String entryPwd;
    private String entryHash;
    private String entryNote;
    private int entryIconId;
    private String entryModifyDate;
    private String entryIV;

    /**
     * @param entryId the id of an entry
     * @param categoryId the category id of an entry
     * @param databaseId the database id of an entry
     * @param entryTitle the title of an entry
     * @param entryUsername the username of an entry
     * @param entryPwd the password of an entry
     * @param entryHash the hashed salt generated for this entry
     * @param entryNote the note of an entry
     * @param entryIconId the icon of an entry
     * @param entryModifyDate the modification date of an entry
     * @param iv string value used for encryption and decryption of an entry
     */
    public EntryProperty(int entryId, int categoryId, int databaseId, String entryTitle, String entryUsername, String entryPwd, String entryHash, String entryNote, int entryIconId, String entryModifyDate, String iv) {
        this.entryId = entryId;
        this.categoryId = categoryId;
        this.databaseId = databaseId;
        this.entryTitle = entryTitle;
        this.entryUsername = entryUsername;
        this.entryPwd = entryPwd;
        this.entryHash = entryHash;
        this.entryNote = entryNote;
        this.entryIconId = entryIconId;
        this.entryModifyDate = entryModifyDate;
        this.entryIV = iv;
    }

    /**
     * @param databaseId the database id of an entry
     * @param categoryId the category id of an entry
     * @param entryTitle the title of an entry
     * @param entryUsername the username of an entry
     * @param entryPwd the password of an entry
     * @param entryHash the hashed salt generated for this entry
     * @param entryNote the note of an entry
     * @param entryIconId the icon of an entry
     * @param iv string value used for encryption and decryption of an entry
     */
    public EntryProperty(int databaseId, int categoryId, String entryTitle, String entryUsername, String entryPwd, String entryHash, String entryNote, int entryIconId, String iv) {
        this.databaseId = databaseId;
        this.categoryId = categoryId;
        this.entryTitle = entryTitle;
        this.entryUsername = entryUsername;
        this.entryPwd = entryPwd;
        this.entryHash = entryHash;
        this.entryNote = entryNote;
        this.entryIconId = entryIconId;
        this.entryIV = iv;
    }

    /**
     * @param entryId the id of an entry
     * @param entryTitle the title of an entry
     * @param entryUsername the username of an entry
     * @param entryPwd the password of an entry
     * @param entryHash the hashed salt generated for this entry
     * @param entryNote the note of an entry
     * @param iv string value used for encryption and decryption of an entry
     */
    public EntryProperty(int entryId, String entryTitle, String entryUsername, String entryPwd, String entryHash, String entryNote, String iv) {
        this.entryId = entryId;
        this.entryTitle = entryTitle;
        this.entryUsername = entryUsername;
        this.entryPwd = entryPwd;
        this.entryHash = entryHash;
        this.entryNote = entryNote;
        this.entryIV = iv;
    }

    @Override
    public String getItemHeader() {
        return getEntryTitle();
    }

    @Override
    public String getItemSubHeader() {
        return getEntryModifyDate();
    }

    @Override
    public int getItemImage() {
        return getEntryIconId();
    }

    /**
     *
     * @return the database id of an entry
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     *
     * @return the category id of an entry
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @return the entry id of an entry
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     *
     * @return the title of an entry
     */
    public String getEntryTitle() {
        return entryTitle;
    }

    /**
     *
     * @return the username of an entry
     */
    public String getEntryUsername() {
        return entryUsername;
    }

    /**
     *
     * @return the password of an entry
     */
    public String getEntryPwd() {
        return entryPwd;
    }

    /**
     *
     * @return the hash of an entry (for username and password)
     */
    public String getEntryHash() {
        return entryHash;
    }

    /**
     *
     * @return the notes of entry
     */
    public String getEntryNote() {
        return entryNote;
    }

    /**
     *
     * @return the icon id of entry
     */
    public int getEntryIconId() {
        return entryIconId;
    }

    /**
     *
     * @return the modification date of an entry
     */
    public String getEntryModifyDate() {
        return entryModifyDate;
    }

    /**
     *
     * @return the string for encryption / decryption of an entry
     */
    public String getEntryIV() {
        return entryIV;
    }
}

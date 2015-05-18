package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 *
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
     * @param entryId
     * @param categoryId
     * @param databaseId
     * @param entryTitle
     * @param entryUsername
     * @param entryPwd
     * @param entryHash
     * @param entryNote
     * @param entryIconId
     * @param entryModifyDate
     * @param iv
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
     *
     * @param databaseId
     * @param categoryId
     * @param entryTitle
     * @param entryUsername
     * @param entryPwd
     * @param entryHash
     * @param entryNote
     * @param entryIconId
     * @param iv
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
     *
     * @param entryId
     * @param entryTitle
     * @param entryUsername
     * @param entryPwd
     * @param entryHash
     * @param entryNote
     * @param iv
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
     * @return
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     *
     * @return
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @return
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     *
     * @return
     */
    public String getEntryTitle() {
        return entryTitle;
    }

    /**
     *
     * @return
     */
    public String getEntryUsername() {
        return entryUsername;
    }

    /**
     *
     * @return
     */
    public String getEntryPwd() {
        return entryPwd;
    }

    /**
     *
     * @return
     */
    public String getEntryHash() {
        return entryHash;
    }

    /**
     *
     * @return
     */
    public String getEntryNote() {
        return entryNote;
    }

    /**
     *
     * @return
     */
    public int getEntryIconId() {
        return entryIconId;
    }

    /**
     *
     * @return
     */
    public String getEntryModifyDate() {
        return entryModifyDate;
    }

    /**
     *
     * @return
     */
    public String getEntryIV() {
        return entryIV;
    }
}

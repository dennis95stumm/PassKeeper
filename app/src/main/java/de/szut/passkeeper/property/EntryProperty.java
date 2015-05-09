package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * Created by Sami.Al-Khatib on 21.03.2015.
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

    public int getDatabaseId() {
        return databaseId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getEntryId() {
        return entryId;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public String getEntryUsername() {
        return entryUsername;
    }

    public String getEntryPwd() {
        return entryPwd;
    }

    public String getEntryHash() {
        return entryHash;
    }

    public String getEntryNote() {
        return entryNote;
    }

    public int getEntryIconId() {
        return entryIconId;
    }

    public String getEntryModifyDate() {
        return entryModifyDate;
    }

    public String getEntryIV() {
        return entryIV;
    }
}

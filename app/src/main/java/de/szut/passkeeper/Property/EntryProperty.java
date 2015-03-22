package de.szut.passkeeper.Property;

import de.szut.passkeeper.Interface.IUserProperty;

/**
 * Created by Sami.Al-Khatib on 21.03.2015.
 */
public class EntryProperty implements IUserProperty {

    private int databaseId;
    private int categoryId;
    private int entryId;
    private String entryTitle;
    private String entryUserName;
    private String entryPwd;
    private String entryHash;
    private String entryNote;
    private String entryCDate;
    private String entryMDate;

    public EntryProperty(int databaseId, int categoryId, int entryId, String entryTitle, String entryUserName, String entryPwd, String entryHash, String entryNote, String entryCDate, String entryMDate) {
        this.entryId = entryId;
        this.databaseId = databaseId;
        this.categoryId = categoryId;
        this.entryTitle = entryTitle;
        this.entryUserName = entryUserName;
        this.entryPwd = entryPwd;
        this.entryHash = entryHash;
        this.entryNote = entryNote;
        this.entryCDate = entryCDate;
        this.entryMDate = entryMDate;
    }

    @Override
    public String getItemHeader() {
        return getEntryTitle();
    }

    @Override
    public String getItemSubHeader() {
        return getEntryMDate();
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

    public String getEntryUserName() {
        return entryUserName;
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

    public String getEntryCDate() {
        return entryCDate;
    }

    public String getEntryMDate() {
        return entryMDate;
    }
}

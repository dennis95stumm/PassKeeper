package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class CategoryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private int categoryIconId;
    private String categoryModifyDate;

    public CategoryProperty(int categoryId, int databaseId, String categoryName, int categoryIconId, String categoryModifyDate) {
        this.categoryId = categoryId;
        this.databaseId = databaseId;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
        this.categoryModifyDate = categoryModifyDate;
    }

    public CategoryProperty(int databaseId, String categoryName, int categoryIconId) {
        this.databaseId = databaseId;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
    }

    @Override
    public String getItemHeader() {
        return getCategoryName();
    }

    @Override
    public String getItemSubHeader() {
        return getCategoryModifyDate();
    }

    @Override
    public int getItemImage() {
        return getCategoryIconId();
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryIconId() {
        return categoryIconId;
    }

    public String getCategoryModifyDate() {
        return categoryModifyDate;
    }
}

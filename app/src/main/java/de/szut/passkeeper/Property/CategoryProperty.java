package de.szut.passkeeper.Property;

import de.szut.passkeeper.Interface.IUserProperty;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class CategoryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private int categoryIconId;
    private String categoryCdate;
    private String categoryMdate;

    public CategoryProperty(int categoryId, int databaseId, String categoryName, int categoryIconId, String categoryCdate, String categoryMdate) {
        this.categoryId = categoryId;
        this.databaseId = databaseId;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
        this.categoryCdate = categoryCdate;
        this.categoryMdate = categoryMdate;
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
        return getCategoryMdate();
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

    public String getCategoryCdate() {
        return categoryCdate;
    }

    public String getCategoryMdate() {
        return categoryMdate;
    }
}

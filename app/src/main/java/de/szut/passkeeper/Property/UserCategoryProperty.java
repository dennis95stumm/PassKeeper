package de.szut.passkeeper.Property;

import de.szut.passkeeper.Interface.IListViewType;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class UserCategoryProperty implements IListViewType {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private String categoryCdate;
    private String categoryMdate;

    public UserCategoryProperty(int databaseId, int categoryId, String categoryName, String categoryCdate, String categoryMdate) {
        this.databaseId = databaseId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCdate = categoryCdate;
        this.categoryMdate = categoryMdate;
    }

    @Override
    public String getItemHeader() {
        return getCategoryName();
    }

    @Override
    public String getItemSubHeader() {
        return getCategoryMdate();
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

    public String getCategoryCdate() {
        return categoryCdate;
    }

    public String getCategoryMdate() {
        return categoryMdate;
    }
}

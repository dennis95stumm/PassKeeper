package de.szut.passkeeper;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class UserCategoryProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private String categoryCdate;
    private String categoryMdate;

    public UserCategoryProperty(int databaseId, int categoryId, String categoryName, String categoryCdate, String categoryMdate){
        this.databaseId = databaseId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCdate = categoryCdate;
        this.categoryMdate = categoryMdate;
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

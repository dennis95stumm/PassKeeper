package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * this class holds the properties of a category
 */
public class CategoryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private int categoryIconId;
    private String categoryModifyDate;

    /**
     * Constructor.
     * @param categoryId the id of a category
     * @param databaseId the database id of a category
     * @param categoryName the name of a category
     * @param categoryIconId the icon id of a cateogry
     * @param categoryModifyDate the modification date of a category
     */
    public CategoryProperty(int categoryId, int databaseId, String categoryName, int categoryIconId, String categoryModifyDate) {
        this.categoryId = categoryId;
        this.databaseId = databaseId;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
        this.categoryModifyDate = categoryModifyDate;
    }

    /**
     * Constructor.
     * @param databaseId the database id of a category
     * @param categoryName the name of a category
     * @param categoryIconId the icon id of a cateogry
     */
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

    /**
     * @return the databaseId of a category
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     * @return the category id of a category
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @return the name of a category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return the icon id of a category
     */
    public int getCategoryIconId() {
        return categoryIconId;
    }

    /**
     * @return the modification date of a category
     */
    public String getCategoryModifyDate() {
        return categoryModifyDate;
    }
}

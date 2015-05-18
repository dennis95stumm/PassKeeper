package de.szut.passkeeper.property;

import de.szut.passkeeper.interfaces.IUserProperty;

/**
 *
 */
public class CategoryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private int categoryIconId;
    private String categoryModifyDate;

    /**
     * @param categoryId
     * @param databaseId
     * @param categoryName
     * @param categoryIconId
     * @param categoryModifyDate
     */
    public CategoryProperty(int categoryId, int databaseId, String categoryName, int categoryIconId, String categoryModifyDate) {
        this.categoryId = categoryId;
        this.databaseId = databaseId;
        this.categoryName = categoryName;
        this.categoryIconId = categoryIconId;
        this.categoryModifyDate = categoryModifyDate;
    }

    /**
     *
     * @param databaseId
     * @param categoryName
     * @param categoryIconId
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
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @return
     */
    public int getCategoryIconId() {
        return categoryIconId;
    }

    /**
     *
     * @return
     */
    public String getCategoryModifyDate() {
        return categoryModifyDate;
    }
}

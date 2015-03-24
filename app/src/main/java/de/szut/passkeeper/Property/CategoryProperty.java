package de.szut.passkeeper.Property;

import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.R;

/**
 * Created by Sami.Al-Khatib on 18.03.2015.
 */
public class CategoryProperty implements IUserProperty {
    private int databaseId;
    private int categoryId;
    private String categoryName;
    private String categoryCdate;
    private String categoryMdate;

    public CategoryProperty( int categoryId, int databaseId, String categoryName, String categoryCdate, String categoryMdate) {
        this.categoryId = categoryId;
        this.databaseId = databaseId;
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

    @Override
    public int getItemImage() {
        return R.drawable.ic_folder;
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

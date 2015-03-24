package de.szut.passkeeper.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Vector;

import de.szut.passkeeper.Fragment.EntryFragment;
import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;

public class EntryFragmentActivity extends FragmentActivity implements IActivity {

    public static ViewPager viewPager;
    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserEntryProperty;
    private int databaseId;
    private int categoryId;
    private int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_fragment_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(this);
        databaseId = getIntent().getExtras().getInt("databaseId");
        categoryId = getIntent().getExtras().getInt("categoryId");
        entryId = getIntent().getExtras().getInt("entryId");
    }

    @Override
    public void populateView() {

    }
}

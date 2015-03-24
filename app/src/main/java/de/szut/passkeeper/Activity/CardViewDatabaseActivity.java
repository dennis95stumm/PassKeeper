package de.szut.passkeeper.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.Model.DatabaseModel;
import de.szut.passkeeper.R;
import de.szut.passkeeper.Utility.CardViewAdapter;

/**
 * Created by Sami.Al-Khatib on 24.03.2015.
 */
public class CardViewDatabaseActivity extends Activity implements IActivity {
    private RecyclerView recyclerView;
    private DatabaseModel databaseModel;
    private Vector<IUserProperty> vectorUserProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_listview_layout);
        setDefaults();
        populateView();
    }

    @Override
    public void setDefaults() {
        databaseModel = new DatabaseModel(this);
        vectorUserProperty = databaseModel.getUserDatabasePropertyVector();
    }

    @Override
    public void populateView() {
        recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CardViewAdapter(vectorUserProperty));
    }
}

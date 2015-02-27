package de.szut.passkeeper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by redtiger on 27.02.15.
 */
public class DatabaseCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    public DatabaseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.choose_database_list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(DatabaseCursorAdapter.class.getSimpleName(), String.valueOf(cursor.getInt(1)));
        Log.d(DatabaseCursorAdapter.class.getSimpleName(), cursor.getString(2));
        Log.d(DatabaseCursorAdapter.class.getSimpleName(), cursor.getString(3));
        Log.d(DatabaseCursorAdapter.class.getSimpleName(), cursor.getString(4));
        Log.d(DatabaseCursorAdapter.class.getSimpleName(), cursor.getString(5));
    }
}

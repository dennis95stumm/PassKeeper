package de.szut.passkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.szut.passkeeper.R;
import de.szut.passkeeper.UserDatabaseProperties;


public class CustomListViewAdapter extends BaseAdapter{

    private ArrayList<UserDatabaseProperties> _data;
    Context _c;

    public CustomListViewAdapter(ArrayList<UserDatabaseProperties> data, Context c) {
        this._data = data;
        this._c = c;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @SuppressWarnings("all")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_list_items, null);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        TextView optionHeader = (TextView) v.findViewById(R.id.databaseName);
        TextView optionDescription = (TextView) v.findViewById(R.id.databaseEditationDate);

        UserDatabaseProperties userDatabaseProperties = _data.get(position);
        imageView.setImageResource(R.drawable.ic_launcher);
        optionHeader.setText(userDatabaseProperties.getDatabaseName());
        optionDescription.setText(userDatabaseProperties.getDatabaseMdate());

        return v;
    }
}

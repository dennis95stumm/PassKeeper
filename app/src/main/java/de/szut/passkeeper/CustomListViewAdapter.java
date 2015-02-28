package de.szut.passkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CustomListViewAdapter extends BaseAdapter {

    Context _c;
    private List<UserDatabaseProperties> _data;

    public CustomListViewAdapter(List<UserDatabaseProperties> data, Context c) {
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
            v = vi.inflate(R.layout.list_item_layout, null);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        //ImageButton imageButton = (ImageButton) v.findViewById(R.id.icon2);
        TextView optionHeader = (TextView) v.findViewById(R.id.databaseName);
        TextView optionDescription = (TextView) v.findViewById(R.id.databaseEditationDate);

        UserDatabaseProperties userDatabaseProperties = _data.get(position);
        imageView.setImageResource(R.drawable.ic_launcher);
        //imageButton.setImageResource(R.drawable.ic_launcher);
        optionHeader.setText(userDatabaseProperties.getDatabaseName());
        optionDescription.setText(userDatabaseProperties.getDatabaseMdate());

        return v;
    }
}

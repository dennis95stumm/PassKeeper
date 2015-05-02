package de.szut.passkeeper.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IUserProperty;


public class ListViewAdapter extends BaseAdapter {

    Context context;
    private Vector<IUserProperty> vector;

    public ListViewAdapter(Context context, Vector<IUserProperty> vector) {
        this.context = context;
        this.vector = vector;
    }

    @Override
    public int getCount() {
        return vector.size();
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
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_layout, null);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        TextView textViewDatabaseName = (TextView) v.findViewById(R.id.textViewHeader);
        TextView textViewEditationDate = (TextView) v.findViewById(R.id.textViewSubHeader);

        IUserProperty iUserProperty = vector.get(position);
        imageView.setImageResource(iUserProperty.getItemImage());
        textViewDatabaseName.setText(iUserProperty.getItemHeader());
        textViewEditationDate.setText(context.getString(R.string.textview_modified) + " " + iUserProperty.getItemSubHeader());

        return v;
    }

    public void refresh(Vector<IUserProperty> vector) {
        this.vector = vector;
        notifyDataSetChanged();
    }
}

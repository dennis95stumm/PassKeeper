package de.szut.passkeeper.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.R;


public class ListViewAdapter extends BaseAdapter {

    Context context;
    private Vector<IUserProperty> vector;

    public ListViewAdapter(Vector<IUserProperty> vector, Context context) {
        this.vector = vector;
        this.context = context;
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

        IUserProperty IUserProperty = vector.get(position);
        imageView.setImageResource(R.drawable.ic_launcher);
        textViewDatabaseName.setText(IUserProperty.getItemHeader());
        textViewEditationDate.setText(IUserProperty.getItemSubHeader());

        return v;
    }
}
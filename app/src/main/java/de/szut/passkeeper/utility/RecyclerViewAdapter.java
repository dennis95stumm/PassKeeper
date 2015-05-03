package de.szut.passkeeper.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IRecyclerItemClickListener;
import de.szut.passkeeper.interfaces.IUserProperty;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    private Vector<IUserProperty> vector;
    private IRecyclerItemClickListener onRecyclerItemClickListener;

    public RecyclerViewAdapter(Context context, Vector<IUserProperty> vector) {
        this.context = context;
        this.vector = vector;
    }

    /*@Override
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
    }*/


    public void refresh(Vector<IUserProperty> vector) {
        this.vector = vector;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final GestureDetector gestruesDetector = new GestureDetector(context, new RecyclerGestrueListener(context, viewHolder, onRecyclerItemClickListener, i));
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestruesDetector.onTouchEvent(event)) {
                    return false;
                }
                return true;
            }
        });
        viewHolder.header.setText(vector.get(i).getItemHeader());
        viewHolder.subheader.setText(vector.get(i).getItemSubHeader());
        viewHolder.icon.setImageResource(vector.get(i).getItemImage());
    }

    @Override
    public int getItemCount() {
        return vector.size();
    }

    public void setOnItemClickListener(IRecyclerItemClickListener onItemClickListener) {
        this.onRecyclerItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView subheader;
        public ImageView icon;
        public View itemView;
        public View deleteAnimViewLeft;
        public View deleteAnimViewRight;
        public View mainView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            header = (TextView) itemView.findViewById(R.id.textViewHeader);
            subheader = (TextView) itemView.findViewById(R.id.textViewSubHeader);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            deleteAnimViewLeft = itemView.findViewById(R.id.delete_recycler_item_left);
            deleteAnimViewRight = itemView.findViewById(R.id.delete_recycler_item_right);
            mainView = itemView.findViewById(R.id.mainview);
        }
    }
}

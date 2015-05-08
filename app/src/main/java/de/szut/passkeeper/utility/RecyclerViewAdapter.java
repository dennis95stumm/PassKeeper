package de.szut.passkeeper.utility;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IRecyclerActivity;
import de.szut.passkeeper.interfaces.IUserProperty;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    private Vector<IUserProperty> vector;
    private IRecyclerActivity iRecyclerActivity;
    private int deleteConfirmationViewId;

    public RecyclerViewAdapter(Context context, Vector<IUserProperty> vector, RecyclerView recyclerView, IRecyclerActivity iRecyclerActivity) {
        this.context = context;
        this.vector = vector;
        this.iRecyclerActivity = iRecyclerActivity;
        final GestureDetector gestruesDetector = new GestureDetector(null, new RecyclerGestrueListener(context, iRecyclerActivity, recyclerView));
        ((Activity) context).findViewById(R.id.recyclerViewDefault).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestruesDetector.onTouchEvent(event);
            }
        });
        this.deleteConfirmationViewId = R.id.delitition_password;
    }

    public RecyclerViewAdapter(Context context, Vector<IUserProperty> vector, RecyclerView recyclerView, IRecyclerActivity iRecyclerActivity, int confirmViewId) {
        this.context = context;
        this.vector = vector;
        this.iRecyclerActivity = iRecyclerActivity;
        final GestureDetector gestruesDetector = new GestureDetector(null, new RecyclerGestrueListener(context, iRecyclerActivity, recyclerView));
        ((Activity) context).findViewById(R.id.recyclerViewDefault).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestruesDetector.onTouchEvent(event);
            }
        });
        this.deleteConfirmationViewId = confirmViewId;
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
        viewHolder.header.setText(vector.get(i).getItemHeader());
        viewHolder.subheader.setText(vector.get(i).getItemSubHeader());
        viewHolder.icon.setImageResource(vector.get(i).getItemImage());
    }

    @Override
    public int getItemCount() {
        return vector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView subheader;
        public ImageView icon;
        public View itemView;
        public View deleteAnimView;
        public View mainView;
        public View delteConfirmation;
        public View delteConfirmationView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            header = (TextView) itemView.findViewById(R.id.textViewHeader);
            subheader = (TextView) itemView.findViewById(R.id.textViewSubHeader);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            deleteAnimView = itemView.findViewById(R.id.delete_recycler_item);
            mainView = itemView.findViewById(R.id.mainview);
            delteConfirmation = itemView.findViewById(R.id.delete_recycler_item_confirmation);
            delteConfirmationView = itemView.findViewById(deleteConfirmationViewId);
            delteConfirmationView.setVisibility(View.VISIBLE);
        }
    }
}

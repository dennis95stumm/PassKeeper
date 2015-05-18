package de.szut.passkeeper.utility;

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
import de.szut.passkeeper.interfaces.IRecyclerActivity;
import de.szut.passkeeper.interfaces.IUserProperty;

/**
 * Adapter for the recycler view. Layouts the recycler items an manges the data.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Vector<IUserProperty> vector;
    private int deleteConfirmationViewId;

    /**
     * Constructor for the recycler view. Initializes the values and creates a gestures detector to the recycler view.
     * @param vector Vector with data for the recycler view
     * @param recyclerView Recycler view to which the adapter is attached
     * @param iRecyclerActivity Activity that uses the recycler view
     */
    public RecyclerViewAdapter(Vector<IUserProperty> vector, RecyclerView recyclerView, IRecyclerActivity iRecyclerActivity) {
        this.vector = vector;
        final GestureDetector gestruesDetector = new GestureDetector(null, new RecyclerGestrueListener(iRecyclerActivity, recyclerView));
        iRecyclerActivity.findViewById(R.id.recyclerViewDefault).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestruesDetector.onTouchEvent(event);
            }
        });
        this.deleteConfirmationViewId = R.id.delitition_text;
    }

    /**
     * Constructor for the recycler view. Initializes the values and creates a gestures detector to the recycler view.
     * A custom view of the recycler item layout can be specified here for the delete confirmation.
     * @param vector Vector with data for the recycler view
     * @param recyclerView Recycler view to which the adapter is attached
     * @param iRecyclerActivity Activity that uses the recycler view
     * @param confirmViewId ID of the delete confirmation view
     */
    public RecyclerViewAdapter(Vector<IUserProperty> vector, RecyclerView recyclerView, IRecyclerActivity iRecyclerActivity, int confirmViewId) {
        this.vector = vector;
        final GestureDetector gestruesDetector = new GestureDetector(null, new RecyclerGestrueListener(iRecyclerActivity, recyclerView));
        iRecyclerActivity.findViewById(R.id.recyclerViewDefault).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestruesDetector.onTouchEvent(event);
            }
        });
        this.deleteConfirmationViewId = confirmViewId;
    }

    /**
     * Update the recycler adapter with new data.
     * @param vector Vector with new data for the recycler view
     */
    public void refresh(Vector<IUserProperty> vector) {
        this.vector = vector;
        notifyDataSetChanged();
    }

    /**
     * Update the recycler adapter with new data specially if a recycler item is removed.
     * @param vector Vector with new data for the recycler view
     * @param position Position of deleted recycler item in the recycler view
     */
    public void refresh(Vector<IUserProperty> vector, int position) {
        this.vector = vector;
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_layout, viewGroup, false);
        return new ViewHolder(view);
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

    /**
     * Inner class for the view holder of a recycler item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView subheader;
        public ImageView icon;
        public View itemView;
        public View deleteAnimView;
        public View mainView;
        public View delteConfirmation;
        public View delteConfirmationView;

        /**
         * Constructor. Initializes the attributes of the view holder.
         * @param itemView View for the recycler item
         */
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

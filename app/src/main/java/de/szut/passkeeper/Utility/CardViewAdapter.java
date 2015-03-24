package de.szut.passkeeper.Utility;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import de.szut.passkeeper.Interface.IUserProperty;
import de.szut.passkeeper.R;

/**
 * Created by Sami.Al-Khatib on 24.03.2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.UserPropertyViewHolder> {
    private Vector<IUserProperty> vectorData;

    public CardViewAdapter(Vector<IUserProperty> vectorData) {
        this.vectorData = vectorData;
    }

    @Override
    public UserPropertyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item_layout, viewGroup, false);
        return new UserPropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserPropertyViewHolder userPropertyViewHolder, int position) {
        userPropertyViewHolder.imageView.setImageResource(R.drawable.ic_launcher);
        userPropertyViewHolder.textViewHeader.setText(vectorData.get(position).getItemHeader());
        userPropertyViewHolder.textViewSubHeader.setText(vectorData.get(position).getItemSubHeader());
    }

    @Override
    public int getItemCount() {
        return vectorData.size();
    }

    public class UserPropertyViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewHeader;
        protected TextView textViewSubHeader;
        protected ImageView imageView;

        public UserPropertyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageViewCard);
            textViewHeader = (TextView) view.findViewById(R.id.textViewHeaderCard);
            textViewSubHeader = (TextView) view.findViewById(R.id.textViewSubHeaderCard);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(getClass().getSimpleName() + " PRESSED: ", String.valueOf(v.getId()));
                }
            });
        }
    }
}

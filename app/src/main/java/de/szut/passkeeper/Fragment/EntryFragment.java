package de.szut.passkeeper.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.szut.passkeeper.Interface.IActivity;
import de.szut.passkeeper.R;

/**
 * Created by Sami on 22.03.2015.
 */
public class EntryFragment extends android.support.v4.app.Fragment implements IActivity {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_layout, container, false);
        return view;
    }

    @Override
    public void setDefaults() {

    }

    @Override
    public void populateView() {

    }
}

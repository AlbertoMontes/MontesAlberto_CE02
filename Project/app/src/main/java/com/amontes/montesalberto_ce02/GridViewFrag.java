// Alberto Montes
// MDF3 1604
// CE02

package com.amontes.montesalberto_ce02;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;

public class GridViewFrag extends Fragment {

    public static final String TAG = "GridViewFrag.TAG";
    private ClickInterface mListener;
    // How do I get rid of this static member variable???? Broadcast receiver in a fragment?
    static GridView imageGrid;

    public interface ClickInterface{

        void onRefresh();
        void imageClick(File file);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mListener = (ClickInterface)getActivity();
        return inflater.inflate(R.layout.gridview_frag, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.refresh_item, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.refresh_action:
                mListener.onRefresh();
                break;

            default:
                break;

        }

        return true;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageGrid = (GridView) getView().findViewById(R.id.serviceGrid);

        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                File file = (File) parent.getItemAtPosition(position);
                mListener.imageClick(file);

            }

        });

    }

    public static GridViewFrag newInstance() {

        return new GridViewFrag();

    }

}
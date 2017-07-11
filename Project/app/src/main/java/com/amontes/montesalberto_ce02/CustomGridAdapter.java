// Alberto Montes
// MDF3 1604
// CE02

package com.amontes.montesalberto_ce02;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;

public class CustomGridAdapter extends ArrayAdapter<File>{

    // Picasso dependency used throughout adapter.

    private Context mContext;
    private int resID;
    private ArrayList<File> mGridFile = new ArrayList<>();

    static class ViewHolder {

        ImageView imgView;

    }

    // Constructor.
    public CustomGridAdapter(Context _mContext, int _resID, ArrayList<File> _mGridFile) {

        super(_mContext, _resID, _mGridFile);
        this.resID = _resID;
        this.mContext = _mContext;
        this.mGridFile = _mGridFile;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // View holders.
        View row = convertView;
        ViewHolder imgHolder;

        if (row == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(resID, parent, false);
            imgHolder = new ViewHolder();
            imgHolder.imgView = (ImageView) row.findViewById(R.id.custom_image_view);
            row.setTag(imgHolder);

        } else {

            imgHolder = (ViewHolder) row.getTag();

        }

        File item = mGridFile.get(position);
        Picasso.with(mContext).load(item).noFade().fit().into(imgHolder.imgView);
        return row;

    }

}
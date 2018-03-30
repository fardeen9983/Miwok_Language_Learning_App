package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int backgroundColorId;

    public WordAdapter(Activity context, ArrayList<Word> list, int backgroundColorId) {
        super(context, 0, list);
        this.backgroundColorId = ContextCompat.getColor(getContext(), backgroundColorId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ListItemView.setBackgroundColor(backgroundColorId);
        Word word = getItem(position);
        TextView miwokTranslation = ListItemView.findViewById(R.id.miwok);
        TextView defaultTranslation = ListItemView.findViewById(R.id.eng);
        ImageView imageView = ListItemView.findViewById(R.id.image);

        if (word.hasImage()) imageView.setImageResource(word.getImageId());
        else imageView.setVisibility(View.GONE);

        View frameLayout = ListItemView.findViewById(R.id.frameLayout);
        frameLayout.setBackgroundColor(backgroundColorId);

        miwokTranslation.setText(word.getMiwokTranslation());
        defaultTranslation.setText(word.getDeaultTranslation());
        return ListItemView;
    }
}


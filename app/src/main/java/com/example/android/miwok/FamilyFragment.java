package com.example.android.miwok;



import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;
import com.example.android.miwok.WordAdapter;

import java.util.ArrayList;

/**
 * Created by LUCIFER on 11-01-2018.
 */

public class FamilyFragment extends Fragment {
    private ArrayList<Word> family = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseAudio();
        }
    };
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseAudio();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mediaPlayer.start();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        initFamily(family);
        final WordAdapter wordAdapter = new WordAdapter(getActivity(), family, R.color.category_family);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseAudio();
                Word word = wordAdapter.getItem(position);
                if (audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmMediaId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer!=null)
            mediaPlayer.stop();
    }
    public void releaseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
    public void initFamily(ArrayList<Word> family) {
        family.add(new Word("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("Younger Brother ", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("Younger Sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("Grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("Grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));
    }
}


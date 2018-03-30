package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by LUCIFER on 11-01-2018.
 */

public class ColorsFragment extends Fragment{
    private MediaPlayer mediaPlayer;
    private ArrayList<Word> colors = new ArrayList<>();
    private AudioManager audioManager;
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
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseAudio();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        initList(colors);
        final WordAdapter wordAdapter = new WordAdapter(getActivity(), colors, R.color.category_colors);
        ListView list = rootView.findViewById(R.id.list);
        list.setAdapter(wordAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        if (mediaPlayer != null)
            mediaPlayer.stop();
        releaseAudio();
    }

    public void releaseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
    public void initList(ArrayList<Word> colors) {

        colors.add(new Word("Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("Green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("Brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("Gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("Black", "kululli", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("White", "kelelli", R.drawable.color_white, R.raw.color_white));
        colors.add(new Word("Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Word("Mustard Yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
    }
}

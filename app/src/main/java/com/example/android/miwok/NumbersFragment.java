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


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class NumbersFragment extends Fragment {

    private ArrayList<Word> numbers = new ArrayList<>();
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

    public NumbersFragment() {
        // Required empty public constructor
    }

    public void releaseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        releaseAudio();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);
        init(numbers);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final WordAdapter wordAdapter = new WordAdapter(getActivity(), numbers, R.color.category_numbers);
        ListView list = rootView.findViewById(R.id.list);
        list.setAdapter(wordAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = wordAdapter.getItem(position);
                releaseAudio();
                if (audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmMediaId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return rootView;
    }

    public void init(ArrayList<Word> numbers) {
        numbers.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("Two", "ottiko", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));
    }
}



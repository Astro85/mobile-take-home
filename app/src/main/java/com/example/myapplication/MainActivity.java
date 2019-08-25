package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.episode.EpisodeFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EpisodeFragment fragment = new EpisodeFragment();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

}

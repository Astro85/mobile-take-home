package com.example.myapplication.character;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Network;
import com.example.myapplication.R;
import com.example.myapplication.details.DetailFragment;
import com.example.myapplication.episode.Episode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterFragment extends Fragment implements CharacterAdapter.OnItemClickedListener {
    private static final String TAG = CharacterFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private CharacterAdapter characterAdapter;
    private Episode episode;


    public CharacterFragment(Episode episode) {
        this.episode = episode;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.character_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.character_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        characterAdapter = new CharacterAdapter(this, new ArrayList<Character>());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(characterAdapter);
        //TODO: add headers, loading spinner, error messages and retry
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Network.getInstance(getContext()).getAllCharacters(episode, new CharacterListener() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                Collections.sort(characters);
                Collections.reverse(characters);
                characterAdapter = new CharacterAdapter(CharacterFragment.this, characters);
                recyclerView.setAdapter(characterAdapter);
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG, "Error", ex);
            }
        });

    }

    @Override
    public void onItemClicked(Character character) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new DetailFragment(character));
        fragmentTransaction.addToBackStack("detail");
        fragmentTransaction.commit();
    }

    public interface CharacterListener {
        void onCharactersLoaded(List<Character> characters);
        void onError(Exception ex);
    }
}

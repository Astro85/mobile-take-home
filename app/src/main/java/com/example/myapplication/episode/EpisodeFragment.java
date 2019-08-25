package com.example.myapplication.episode;

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
import com.example.myapplication.character.CharacterFragment;

import java.util.ArrayList;
import java.util.List;

public class EpisodeFragment extends Fragment implements EpisodeAdapter.OnItemClickedListener {
    private static final String TAG = EpisodeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private EpisodeAdapter episodeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.episode_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        episodeAdapter = new EpisodeAdapter(this, new ArrayList<Episode>());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(episodeAdapter);
        //TODO: add headers, loading spinner, error messages and retry
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Network.getInstance(getContext()).getEpisodes(new EpisodeListener() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                try {
                    episodeAdapter = new EpisodeAdapter(EpisodeFragment.this, episodes);
                    recyclerView.setAdapter(episodeAdapter);
                } catch (Exception e) {
                    Log.e(TAG, "onEpisodesLoaded: ", e);
                }
            }

            @Override
            public void onMoreEpisodesLoaded(List<Episode> episodes) {
                episodeAdapter.addEpisodes(episodes);
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG, "Error", ex);
            }
        });
    }

    @Override
    public void onItemClicked(Episode episode) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new CharacterFragment(episode));
        fragmentTransaction.addToBackStack("character");
        fragmentTransaction.commit();
    }

    public interface EpisodeListener {
        void onEpisodesLoaded(List<Episode> episodes);
        void onMoreEpisodesLoaded(List<Episode> episodes);
        void onError(Exception e);
    }
}

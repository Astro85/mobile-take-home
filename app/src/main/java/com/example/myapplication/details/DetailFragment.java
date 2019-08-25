package com.example.myapplication.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Network;
import com.example.myapplication.R;
import com.example.myapplication.character.Character;

public class DetailFragment extends Fragment {
    private Character character;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView statusTextView;
    private TextView speciesGenderTextView;

    public DetailFragment(Character character) {
        this.character = character;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        nameTextView = rootView.findViewById(R.id.detail_name);
        imageView = rootView.findViewById(R.id.detail_image);
        speciesGenderTextView = rootView.findViewById(R.id.detail_species_gender);
        statusTextView  = rootView.findViewById(R.id.detail_status);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        nameTextView.setText(character.name);
        speciesGenderTextView.setText(String.format("%s %s", character.species, character.gender));
        statusTextView.setText(getString(R.string.status,  character.status));
        Network.getInstance(getContext()).getImage(character.image_url, imageView);
    }
}
